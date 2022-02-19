package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.AuthorRegister;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

/**
 * Represents a author interface to a database.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class AuthorDatabase implements AuthorRegister {

    private Statement statement;

    /**
      * Makes an instance of the AuthorDatabase class.
      * @param connection the connection to the database.
      */
    public AuthorDatabase(Connection connection){
        try {
            statement = connection.createStatement();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void addAuthor(Author author) throws CouldNotAddAuthorException {
        checkAuthor(author);
        try {
            statement.executeUpdate("INSERT INTO author(authorID, firstName, lastName, birthYear) VALUES (" + author.getID()  + "," +  makeSQLString(author.getFirstName()) + "," + makeSQLString(author.getLastName()) + "," + author.getBirthYear() + ")");
        } catch (SQLException exception) {
            throw new CouldNotAddAuthorException("The author with the id " + author.getID() + " is already in the system.");
        }
    }

    @Override
    public void removeAuthor(Author author) throws CouldNotRemoveAuthorException {
        checkAuthor(author);
        try {
            statement.executeUpdate("DELETE FROM author WHERE authorID = " + author.getID());

        } catch (SQLException exception) {
            throw new CouldNotRemoveAuthorException("The author with the id " + author.getID() + " is not in the system.");
        }
    }

    @Override
    public Author getAuthorById(long authorID) throws CouldNotGetAuthorException {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM author WHERE authorID = " + authorID +  ";");
            return makeSQLIntoAuthor(resultSet);
        } catch (SQLException exception) {
            throw new CouldNotGetAuthorException("The author with the ID " + authorID + " is not in the system." );
        }
    }

    /**
     * Makes an SQL response into an author object.
     * @param resultSet the result set the author is in.
     * @return an author.
     * @throws SQLException gets thrown if the author could not be made.
     */
    private Author makeSQLIntoAuthor(ResultSet resultSet) throws SQLException {
        return new Author(resultSet.getLong("authorID"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getInt("birthYear"));
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
     * Checks if the author is null.
     * @param authorToCheck the author to check.
     */
    private void checkAuthor(Author authorToCheck){
        checkIfObjectIsNull(authorToCheck, "author");
    }

    /**
     * Checks if the ID is above zero.
     * @param ID the ID to check.
     */
    private void checkID(long ID){
        checkIfLongIsAboveZero(ID, "ID");
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
