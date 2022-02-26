package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Branch;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a class that is the "library"
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class LibraryDatabase {

    private Statement statement;

    /**
      * Makes an instance of the LibraryDatabase class.
      * @param connection the connection for this database
      * @throws SQLException gets thrown if the connection to the DB could not be made.
      */
    public LibraryDatabase(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * Adds a new branch to the library.
     * @param branch the new branch.
     * @throws DuplicateObjectException gets thrown if the object could not be added.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    public void addNewBranch(Branch branch) throws DuplicateObjectException, SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM branch WHERE branchID = " + branch.getBranchID());
        if (!resultSet.next()){
            statement.executeUpdate("INSERT INTO branch(branchID, branchName) VALUES(" + branch.getBranchID() + " ," + makeSQLString(branch.getBranchName()) + ");");
        }else {
            throw new DuplicateObjectException("There is a branch with " + branch.getBranchID() + "already in the system.");
        }
    }

    /**
     * Removes a branch from this library.
     * @param branch the branch you want to remove.
     * @throws RemoveObjectException gets thrown if the branch could not be removed.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    public void removeBranch(Branch branch) throws RemoveObjectException, SQLException {
        removeBranchWithID(branch.getBranchID());
    }

    /**
     * Removes a branch with its id.
     * @param branchID the branch id.
     * @throws RemoveObjectException gets thrown if the branch could not be located.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    public void removeBranchWithID(long branchID) throws RemoveObjectException, SQLException {
        int amount = statement.executeUpdate("DELETE FROM branch WHERE branchID = " + branchID + ";");
        if (amount == 0){
            throw new RemoveObjectException("Could not remove branch with branchID " + branchID + ".");
        }
    }

    /**
     * Gets all the branches of this library.
     * @return a list with all the branches.
     * @throws SQLException gets thrown if the connection to the database gets closed.
     */
    public List<Branch> getAllBranches() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM branch");
        return makeSqlRequestIntoListWithBranches(resultSet);
    }

    /**
     * Makes a sql result set into a list with branches.
     * @param resultSet the sql result set with the branches.
     * @return a list with branches.
     * @throws SQLException gets thrown if the connection to the database gets closed.
     */
    private List<Branch> makeSqlRequestIntoListWithBranches(ResultSet resultSet) throws SQLException {
        List<Branch> branches = new LinkedList<>();
        while (resultSet.next()){
            branches.add(getBranchFromSql(resultSet));
        }
        return branches;
    }

    /**
     * Gets the branch that has the matching ID.
     * @param branchID the ID of the branch.
     * @return a branch that matches that ID.
     * @throws GetObjectException gets thrown if no branch with that ID can be located.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    public Branch getBranchWithID(long branchID) throws GetObjectException, SQLException {
        checkIfLongIsAboveZero(branchID, "branch id");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM branch WHERE branchID = " + branchID + ";");
        if (resultSet.next()){
            return getBranchFromSql(resultSet);
        }else {
            throw new GetObjectException("Could not get branch with branchID " + branchID + ".");
        }


    }

    /**
     * Makes a branch from an SQL result set.
     * @param resultSet the result set of the SQL query.
     * @return a branch that has the result set as details.
     * @throws SQLException gets thrown if the result set is empty.
     */
    private Branch getBranchFromSql(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()){
            resultSet.next();
        }
        return new Branch(resultSet.getLong("branchID"), resultSet.getString("branchName"));
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
