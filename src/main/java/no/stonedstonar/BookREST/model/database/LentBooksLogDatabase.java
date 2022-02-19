package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.ReturnedLentBook;
import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.LentBooksLog;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
            exception.printStackTrace();
        }
    }

    @Override
    public void addReturnedLentBook(ReturnedLentBook returnedLentBook) {
        checkReturnedBook(returnedLentBook);

    }

    @Override
    public void removeReturnedLentBook(ReturnedLentBook returnedLentBook) {
        checkReturnedBook(returnedLentBook);

    }

    @Override
    public List<LentBook> getAllTheTimesBookHasBeenLent(long bookID, long branchID) {
        return null;
    }

    private void checkReturnedBook(ReturnedLentBook returnedLentBook){
        checkIfObjectIsNull(returnedLentBook, "returned lent book");
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
