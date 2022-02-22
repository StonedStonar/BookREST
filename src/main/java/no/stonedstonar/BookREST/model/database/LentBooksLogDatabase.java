package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.ReturnedLentBook;
import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.LentBooksLog;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import org.apache.tomcat.jni.Local;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a lent books log interface to a database.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class LentBooksLogDatabase implements LentBooksLog {

    private Statement statement;

    /**
      * Makes an instance of the LentBooksLogDatabase class.
      */
    public LentBooksLogDatabase(Connection connection){
        try {
            this.statement = connection.createStatement();
        } catch (SQLException exception) {
            System.err.println("Could not connect to the database");
        }
    }

    /**
     * @throws SQLException gets thrown if the sql connection is terminated before results are done
     */
    @Override
    public void addReturnedLentBook(ReturnedLentBook returnedLentBook) throws SQLException {
        checkReturnedBook(returnedLentBook);
        statement.executeUpdate("INSERT INTO lentbookslog(branchBookID, personID, lentDate, dueDate, returnedDate) " +
                    "VALUES(" + returnedLentBook.getBranchBookID() + "," + returnedLentBook.getUserID() + ","+
                    makeSQLString(returnedLentBook.getLentDate().toString()) + "," + makeSQLString(returnedLentBook.getDueDate().toString()) + ","
                    + makeSQLString(returnedLentBook.getReturnedDate().toString())+ " );");
    }

    /**
     * @throws SQLException gets thrown if the sql connection is terminated before results are done
     */
    @Override
    public void removeReturnedLentBook(ReturnedLentBook returnedLentBook) {
        //Todo: Fiks dette så vi har en "ordreID" som kan trackes. Dermed kan vi slette loggen også.

    }

    /**
     * @throws SQLException gets thrown if the sql connection is terminated before results are done
     */
    @Override
    public List<ReturnedLentBook> getAllTheTimesBookHasBeenLent(long branchBookID, long branchID) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM lentbookslog WHERE branchBookID = " + branchBookID + " AND branchID = " + branchBookID + ";");
        return makeReturnedLentBooksList(resultSet);
    }

    /**
     * @throws SQLException gets thrown if the sql connection is terminated before results are done
     */
    @Override
    public List<ReturnedLentBook> getAllReturnedBooks() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM lentbookslog");
        return makeReturnedLentBooksList(resultSet);
    }

    /**
     * Makes a list with returned lent books.
     * @param resultSet the result set to take from.
     * @return a list with all the returned lent books.
     * @throws SQLException gets thrown if the sql connection is terminated before results are done.
     */
    private List<ReturnedLentBook> makeReturnedLentBooksList(ResultSet resultSet) throws SQLException {
        List<ReturnedLentBook> returnedLentBooks = new LinkedList<>();
        while (resultSet.next()){
            returnedLentBooks.add(makeReturnedLentBook(resultSet));
        }
        return returnedLentBooks;
    }

    /**
     * Makes a returned lent book.
     * @param resultSet the result set to make the book from.
     * @return a returned lent book.
     * @throws SQLException gets thrown if the sql connection is terminated before the results are done.
     */
    private ReturnedLentBook makeReturnedLentBook(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()){
            resultSet.next();
        }
        return new ReturnedLentBook(resultSet.getLong("branchBookID"), resultSet.getLong("personID"),
                LocalDate.parse(resultSet.getString("lentDate")), LocalDate.parse(resultSet.getString("dueDate")),
                LocalDate.parse(resultSet.getString("returnedDate")));
    }

    /**
     * Checks if a returned book is null.
     * @param returnedLentBook the returned book to check.
     */
    private void checkReturnedBook(ReturnedLentBook returnedLentBook){
        checkIfObjectIsNull(returnedLentBook, "returned lent book");
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
