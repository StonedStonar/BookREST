package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Branch;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Represents a class that is the "library"
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class LibraryDatabase {

    private Statement statement;

    /**
      * Makes an instance of the LibraryDatabase class.
      */
    public LibraryDatabase(Connection connection){
        try {
            statement = connection.createStatement();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Adds a new branch to the library.
     * @param branch the new branch.
     * @throws DuplicateObjectException gets thrown if the object could not be added.
     */
    public void addNewBranch(Branch branch) throws DuplicateObjectException {
        try {
            statement.executeUpdate("INSERT INTO branch(branchID, branchName) VALUES(" + branch.getBranchID() + " ," + makeSQLString(branch.getBranchName()) + ");");
        } catch (SQLException exception) {
            throw new DuplicateObjectException("There is a branch with " + branch.getBranchID() + "already in the system.");
        }
    }

    /**
     * Removes a branch from this library.
     * @param branch the branch you want to remove.
     * @throws RemoveObjectException gets thrown if the branch could not be removed.
     */
    public void removeBranch(Branch branch) throws RemoveObjectException {
        try {
            statement.executeUpdate("DELETE FROM branch WHERE branchID = " + branch.getBranchID() + ";");
        } catch (SQLException exception) {
            throw new RemoveObjectException("Could not remove branch with branchID " + branch.getBranchID() + ".");
        }
    }

    /**
     * Gets the branch that has the matching ID.
     * @param branchID the ID of the branch.
     * @return a branch that matches that ID.
     * @throws GetObjectException gets thrown if no branch with that ID can be located.
     */
    public Branch getBranchWithID(long branchID) throws GetObjectException {
        checkIfLongIsAboveZero(branchID, "branch id");
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM branch WHERE branchID = " + branchID + ";");
            Branch branch = getBranchFromSql(resultSet);
            return branch;
        } catch (SQLException exception) {
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
