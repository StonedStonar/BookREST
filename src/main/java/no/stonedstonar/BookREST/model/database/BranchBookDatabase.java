package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.BranchBook;
import no.stonedstonar.BookREST.model.BranchBookRegister;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class BranchBookDatabase implements BranchBookRegister {

    private Statement statement;
    /**
      * Makes an instance of the BranchBookDatabase class.
      * @param connection the connection to the database.
      * @throws SQLException gets thrown if the connection to the DB could not be made.
      */
    public BranchBookDatabase(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public void addBranchBook(BranchBook branchBook) throws DuplicateObjectException, SQLException {
        checkBranchBook(branchBook);

        ResultSet resultSet = statement.executeQuery("SELECT * FROM branchBook");
        if (!resultSet.next()){
            statement.executeUpdate("INSERT INTO branchBook(branchBookID, isbn, branchID) VALUES("+ branchBook.getBranchBookID() + " ," + branchBook.getIsbn() + " , " + branchBook.getBranchID() + ");");
        }else {
            throw new DuplicateObjectException("The branch book with id " + branchBook.getBranchBookID() + " is already in the system.");
        }
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public void removeBranchBook(BranchBook branchBook) throws RemoveObjectException, SQLException {
        checkBranchBook(branchBook);
        int amount = statement.executeUpdate("DELETE FROM branchBook WHERE branchbookID = " + branchBook.getBranchBookID() + ";");
        if (amount == 0){
            throw new RemoveObjectException("Could not remove branch book with id " + branchBook.getBranchBookID() + ".");
        }
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public BranchBook getBranchBook(long branchBookID) throws GetObjectException, SQLException {
        checkIfLongIsAboveZero(branchBookID, "branch book id");

        ResultSet resultSet = statement.executeQuery("SELECT * FROM branchbook WHERE branchBookID = " + branchBookID + ";");
        if (!resultSet.next()){
            throw new GetObjectException("Could not get branch book with id " + branchBookID + ".");
        }
        return makeSQLIntoBranchBook(resultSet);

    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public List<BranchBook> getAllBranchBooksForBranchWithID(long branchID) throws SQLException {
        checkIfLongIsAboveZero(branchID, "branch ID");
        List<BranchBook> branchBooks = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM branchBook WHERE branchID = " + branchID + ";");
        while (resultSet.next()){
            branchBooks.add(makeSQLIntoBranchBook(resultSet));
        }
        return branchBooks;
    }

    /**
     * Makes SQL statement into branch book.
     * @param resultSet the result set.
     * @return the branch book that the result set holds.
     * @throws SQLException gets thrown if the branch book could not be made.
     */
    private BranchBook makeSQLIntoBranchBook(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()){
            resultSet.next();
        }
        return new BranchBook(resultSet.getLong("branchbookID"), resultSet.getLong("isbn"), resultSet.getLong("branchID"));
    }

    /**
     * Checks if the branch book is not null.
     * @param branchBook the branch book to check.
     */
    private void checkBranchBook(BranchBook branchBook){
        checkIfObjectIsNull(branchBook, "branch book");
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
