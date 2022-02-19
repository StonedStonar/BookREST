package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.LentBooksRegister;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import org.apache.tomcat.jni.Local;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a interface for lent books to a database.
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

    @Override
    public void addLentBook(LentBook lentBook) throws DuplicateObjectException {
        checkLentBook(lentBook);
        try {
            statement.executeUpdate("INSERT INTO lentbook(branchBookID, personID, lentDate, dueDate) VALUES(" + lentBook.getBranchBookID() + " , " + lentBook.getUserID() + "," + makeSQLString(lentBook.getLentDate().toString())  + "," + makeSQLString(lentBook.getDueDate().toString()) +  ")");
        } catch (SQLException exception) {
            throw new DuplicateObjectException("The lent book with book and userID " + lentBook.getBranchBookID() + " " + lentBook.getUserID());
        }
    }

    @Override
    public void removeLentBook(LentBook lentBook) {
        checkLentBook(lentBook);
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
        //Todo: Usikker på hvordan denne kan gjøres bra.
        return null;
    }

    @Override
    public List<LentBook> getAllDueBooksForUser(long userID) {
        return null;
    }

    /**
     * Checks that the lent book is not null.
     * @param lentBook the lent book to check.
     */
    private void checkLentBook(LentBook lentBook){
        checkIfObjectIsNull(lentBook, "lent book");
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
