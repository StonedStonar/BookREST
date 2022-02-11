package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveBookException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class BookDatabase implements BookRegister{

    private Connection connection;

    private Statement statement;

    public static void main(String[] args) {
        BookDatabase bookDatabase = new BookDatabase();
        List<Long> authors = new ArrayList<>();
        authors.add(1L);
        bookDatabase.addBookWithDetails("Flaggermusmannen", authors,2007, 438);
    }

    /**
      * Makes an instance of the BookDatabase class.
      */
    public BookDatabase(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookREST", "root", "SzzSacbkbachw");
            statement = connection.createStatement();
        }catch (Exception exception){

        }
    }


    @Override
    public void addBook(Book book) {

    }

    @Override
    public void addBookWithDetails(String title, Collection<Long> authors, int year, int numberOfPages){
       checkString(title, "title");
       checkIfObjectIsNull(authors, "authors");
       try {
           statement.executeUpdate("INSERT INTO book(title, yearPublished, numberOfPages) VALUES(" + makeSQLString(title) + "," + year + "," + numberOfPages + ");");
           Iterator<Long> it = authors.iterator();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM book WHERE title =" + makeSQLString(title) + ";");
           resultSet.next();
           int bookID = resultSet.getInt("bookID");
           System.out.print(bookID);
           while (it.hasNext()){
               long authorID = it.next();
               statement.executeUpdate("INSERT INTO authorsOfBook(authorID, bookID) VALUES(" + authorID + "," + bookID + ");");
           }

       }catch (SQLException exception) {
           exception.printStackTrace();
       }
    }

    @Override
    public void removeBook(Book book) throws CouldNotRemoveBookException {

    }

    @Override
    public void removeBookByID(long ID) throws CouldNotRemoveBookException {

    }

    @Override
    public List<Book> getAllBooksOfAuthorID(long authorID) {
        return null;
    }

    @Override
    public Book getBook(long bookID) throws CouldNotGetBookException {
        return null;
    }

    @Override
    public List<Book> getBookList() {
        return null;
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
