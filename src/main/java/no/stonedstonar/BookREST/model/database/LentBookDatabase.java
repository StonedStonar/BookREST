package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.LentBooksRegister;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class LentBookDatabase implements LentBooksRegister {

    private Statement statement;

    /**
      * Makes an instance of the LentBookDatabase class.
      */
    public LentBookDatabase(Connection connection){
        try {
            this.statement = connection.createStatement();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
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

    @Override
    public void addLentBook(LentBook lentBook) {

    }

    @Override
    public void removeLentBook(LentBook lentBook) {

    }

    @Override
    public List<LentBook> getAllDueBooks() {
        return null;
    }

    @Override
    public List<LentBook> getAllDueBooksForBranch(long branchID, int amountOfDays) {
        return null;
    }

    @Override
    public List<LentBook> getAllBooksWithBranchID(long branchID) {
        return null;
    }

    @Override
    public List<LentBook> getAllDueBooksForUser(long userID) {
        return null;
    }

    @Override
    public List<LentBook> getAllDueBooksForUserInBranch(long userId, long branchID) {
        return null;
    }
}
