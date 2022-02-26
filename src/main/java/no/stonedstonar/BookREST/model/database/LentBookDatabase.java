package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.LentBooksRegister;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an interface for lent books to a database.
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

            ResultSet resultSet = statement.executeQuery("SELECT * FROM lentBook WHERE branchBookID = " + lentBook.getBranchBookID() + ";");

            if (!resultSet.next()) {
                statement.executeUpdate("INSERT INTO lentBook(branchBookID, personID, lentDate, dueDate) VALUES(" + lentBook.getBranchBookID() + " , " + lentBook.getUserID() + "," + makeSQLString(lentBook.getLentDate().toString()) + "," + makeSQLString(lentBook.getDueDate().toString()) + ")");
            }else {
                throw new DuplicateObjectException("The lent book with branch book id " + lentBook.getBranchBookID() + " is already lent out to someone");
            }
        } catch (SQLException exception) {
            throw new DuplicateObjectException("The lent book with book and userID " + lentBook.getBranchBookID() + " " + lentBook.getUserID());
        }
    }

    @Override
    public void removeLentBook(LentBook lentBook) throws RemoveObjectException {
        checkLentBook(lentBook);
        try {
            statement.executeUpdate("DELETE FROM lentBook WHERE branchBookID = " + lentBook.getBranchBookID());
        } catch (SQLException exception) {
            throw new RemoveObjectException("The lent book with branchbookID and userID " + lentBook.getBranchBookID() + " "  + lentBook.getUserID() + " could not be found.");
        }
        try {
            statement.execute("INSERT INTO lentBooksLog(branchBookID, personID, lentDate, dueDate, returnedDate) VALUES(" + lentBook.getBranchBookID() + " , " + lentBook.getUserID() + "," + makeSQLString(lentBook.getLentDate().toString()) + "," + makeSQLString(lentBook.getDueDate().toString()) + "," + makeSQLString(LocalDate.now().toString()) + ")");
        }catch (SQLException exception){
            throw new RemoveObjectException("The lent book could not be added to the log.");
        }
    }

    @Override
    public LentBook getLentBook(long branchBookID) throws GetObjectException {
        checkIfLongIsAboveZero(branchBookID, "branch book id");
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lentBooks WHERE branchBookID = " + branchBookID);
            return makeLentBookFromSQLStatement(resultSet);
        }catch (SQLException sqlException){
            throw new GetObjectException("Could not get lent book with book branch id " + branchBookID + ".");
        }
    }

    @Override
    public void removeLentBookByBranchBookID(long branchBookID) throws RemoveObjectException {
        checkIfLongIsAboveZero(branchBookID, "branch book ID");
        try {
            removeLentBook(getLentBook(branchBookID));
        }catch (GetObjectException getObjectException){
            throw new RemoveObjectException(getObjectException.getMessage());
        }
    }

    @Override
    public List<LentBook> getAllDueBooks() {
        List<LentBook> lentBooks;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lentBook WHERE dueDate <= curdate();");
            lentBooks = makeLentBooksFormResultSet(resultSet);
        } catch (SQLException exception) {
            lentBooks = new ArrayList<>();
        }
        return lentBooks;
    }

    @Override
    public List<LentBook> getAllDueBooksForBranch(long branchID, int amountOfDays) {
        return null;
    }

    @Override
    public List<LentBook> getAllBooksWithBranchID(long branchID) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT *\n" +
                "FROM lentBook\n" +
                "JOIN branchBook USING(branchBookID)\n" +
                "WHERE branchID = " + branchID + ";");
        return makeLentBooksFormResultSet(resultSet);
    }


    @Override
    public List<LentBook> getAllDueBooksForUser(long userID) {
        checkIfLongIsAboveZero(userID, "user id");
        List<LentBook> lentBooks;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lentbook WHERE personID = " + userID + ";");
            lentBooks = makeLentBooksFormResultSet(resultSet);
        } catch (SQLException exception) {
            lentBooks = new ArrayList<>();
        }
        return lentBooks;
    }

    /**
     * Makes lent books form result set.
     * @param resultSet the result set with all the lent books.
     * @return a list with all the lent books in them.
     * @throws SQLException if the set is empty.
     */
    private List<LentBook> makeLentBooksFormResultSet(ResultSet resultSet) throws SQLException {
        List<LentBook> lentBooks = new ArrayList<>();
        while (resultSet.next()){
            lentBooks.add(makeLentBookFromSQLStatement(resultSet));
        }
        return lentBooks;
    }

    /**
     * Makes a lent book from the input sql statement.
     * @param resultSet the result set containing the lent book.
     * @return the lent book from the result set.
     * @throws SQLException gets thrown if the result set is empty.
     */
    private LentBook makeLentBookFromSQLStatement(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()){
            resultSet.next();
        }
        return new LentBook(resultSet.getLong("branchBookID"), resultSet.getLong("personID"), resultSet.getDate("lentDate").toLocalDate(), resultSet.getDate("dueDate").toLocalDate());
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
