package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.AuthorRegister;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

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
      * @throws SQLException gets thrown if the connection to the DB could not be made.
      */
    public AuthorDatabase(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public void addAuthor(Author author) throws CouldNotAddAuthorException, SQLException {
        checkAuthor(author);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM author WERE authorID = " + author.getID());
        if (!resultSet.next()){
            statement.executeUpdate("INSERT INTO author(authorID, firstName, lastName, birthYear) VALUES (" + author.getID()  + "," +  makeSQLString(author.getFirstName()) + "," + makeSQLString(author.getLastName()) + "," + author.getBirthYear() + ")");
        }else {
            throw new CouldNotAddAuthorException("The author with the id " + author.getID() + " is already in the system.");
        }
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public void removeAuthor(Author author) throws CouldNotRemoveAuthorException, SQLException {
        checkAuthor(author);
        int amount = statement.executeUpdate("DELETE FROM author WHERE authorID = " + author.getID());
        if (amount == 0){
            throw new CouldNotRemoveAuthorException("The author with the id " + author.getID() + " is not in the system.");
        }
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public Author getAuthorById(long authorID) throws CouldNotGetAuthorException, SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM author WHERE authorID = " + authorID +  ";");
        if (!resultSet.next()){
            throw new CouldNotGetAuthorException("The author with the ID " + authorID + " is not in the system." );
        }
        return makeSQLIntoAuthor(resultSet);

    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public List<Author> getAuthorList() throws SQLException {
        List<Author> authors = new LinkedList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM author;");
        while (resultSet.next()){
            authors.add(makeSQLIntoAuthor(resultSet));
        }
        return authors;
    }

    /**
     * Makes an SQL response into an author object.
     * @param resultSet the result set the author is in.
     * @return an author.
     * @throws SQLException gets thrown if the author could not be made.
     */
    private Author makeSQLIntoAuthor(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()){
            resultSet.next();
        }
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
