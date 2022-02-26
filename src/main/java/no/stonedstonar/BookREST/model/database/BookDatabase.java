package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.RegisterTestData;
import no.stonedstonar.BookREST.model.*;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveBookException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;

/**
 * Represents an interface to the database for books.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class BookDatabase implements BookRegister {

    private Statement statement;

    //"jdbc:mysql://localhost:3306/bookREST", "root", "SzzSacbkbachw"
    /**
      * Makes an instance of the BookDatabase class.
      * @param connection the connection to the database.
      */
    public BookDatabase(Connection connection){
        try {
            statement = connection.createStatement();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }


    @Override
    public void addBook(Book book) throws CouldNotAddBookException {
        checkBook(book);
        try {
            statement.executeUpdate("INSERT INTO book(isbn, title, yearPublished, numberOfPages, publisherID) VALUES(" + book.getISBN() + "," +  makeSQLString(book.getTitle()) + "," + book.getYear() + "," + book.getNumberOfPages() + "," + book.getPublisherID() + ");");
            Iterator<Long> it = book.getAuthors().iterator();
            while (it.hasNext()){
                long authorID = it.next();
                statement.executeUpdate("INSERT INTO authorsOfBook(authorID, isbn) VALUES(" + authorID + "," + book.getISBN() + ");");
            }
        }catch (SQLException exception) {
            throw new CouldNotAddBookException("The book could not be added.");
        }
    }

    @Override
    public void removeBook(Book book) throws CouldNotRemoveBookException {
        checkBook(book);
        removeBookByID(book.getISBN());
    }

    @Override
    public void removeBookByID(long ID) throws CouldNotRemoveBookException {
        checkIfBookID(ID);
        try {
            statement.executeUpdate("DELETE FROM book WHERE isbn = " +  ID + ";");
        }catch (SQLException exception) {
            throw new CouldNotRemoveBookException("Could not remove the book with id " + ID + ".");
        }
    }

    @Override
    public List<Book> getAllBooksOfAuthorID(long authorID) {
        List<Book> bookList = new LinkedList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT isbn FROM authorsofbook WHERE authorID = " + authorID + ";");
            while (resultSet.next()){
                Book book = getBook(resultSet.getLong("isbn"));
                bookList.add(book);
            }
        } catch (SQLException | CouldNotGetBookException exception) {
            System.err.println("Something has gone wrong in getting books of author.");
        }
        return bookList;
    }

    @Override
    public Book getBook(long bookID) throws CouldNotGetBookException {
        checkIfBookID(bookID);
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book WHERE isbn = " + bookID + ";");
            resultSet.next();
            return makeSqlStatementIntoBook(resultSet, makeStatement());
        } catch (SQLException exception) {
            throw new CouldNotGetBookException("Could not get book with isbn " + bookID + ".");
        }
    }

    /**
     *
     * @param bookResultSet
     * @param statementAuthor
     * @return
     * @throws SQLException gets thrown if the result set is empty or the connection to the database is closed.
     */
    private Book makeSqlStatementIntoBook(ResultSet bookResultSet, Statement statementAuthor) throws SQLException {
        if(bookResultSet.isBeforeFirst()){
            bookResultSet.next();
        }
        String title = bookResultSet.getString("title");
        long isbn = bookResultSet.getLong("isbn");
        int yearPublihsed = bookResultSet.getInt("yearPublished");
        int numberOfPages = bookResultSet.getInt("numberOfPages");
        long publisherID = bookResultSet.getLong("publisherID");
        List<Long> autuhorIDs = getAuthorsIDs(statementAuthor.executeQuery("SELECT * FROM authorsOfBook WHERE isbn = " + isbn + ";"));
        return new Book(isbn, title, autuhorIDs, yearPublihsed, numberOfPages,publisherID);
    }

    /**
     * Makes a new statement to execute querys on.
     * @return a new statement to execute querys on.
     * @throws SQLException gets thrown if the connection to the database is closed.
     */
    private Statement makeStatement() throws SQLException {
        return statement.getConnection().createStatement();
    }

    /**
     * Gets the authorID's from a resultset.
     * @param resultSet the resultset with the authors id's.
     * @return a list with all the authorID's
     * @throws SQLException gets thrown if the column "authorID" could not be located.
     */
    private List<Long> getAuthorsIDs(ResultSet resultSet) throws SQLException {
        List<Long> list = new LinkedList<>();
        while (resultSet.next()){
            long authorID = resultSet.getLong("authorID");
            list.add(authorID);
        }
        return list;
    }

    /**
     * Checks if the bookID is above zero.
     * @param bookID the book id to check.
     */
    private void checkIfBookID(long bookID){
        checkIfLongIsAboveZero(bookID, "book ID");
    }

    /**
     * Checks if the input long is above zero.
     * @param number the number to check.
     * @param prefix the prefix the error should have.
     */
    private void checkIfLongIsAboveZero(long number, String prefix){
        if (number <= 0){
            throw new IllegalArgumentException("The " + prefix + " must be above zero.");
        }
    }

    /**
     * Checks if the book is not null.
     * @param bookToCheck the book you want to check.
     */
    private void checkBook(Book bookToCheck){
        checkIfObjectIsNull(bookToCheck, "book");
    }

    @Override
    public List<Book> getBookList() {
        List<Book> booksList = new LinkedList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM book");
            while (resultSet.next()){
                if (!resultSet.isAfterLast()){
                    booksList.add(makeSqlStatementIntoBook(resultSet, makeStatement()));
                }
            }
        } catch (SQLException exception) {
            exception.getMessage();
            booksList.clear();
        }
        return booksList;
    }

    /**
     * Makes a string into the format SQL needs for a string. The quotes is added. So hi turns into "hi".
     * @param statement the statement you want to make into a string.
     * @return a string with " around it.
     */
    private String makeSQLString(String statement){
        return "\"" + statement + "\"";
    }
    
    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix){
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()){
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }
    
    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }
}
