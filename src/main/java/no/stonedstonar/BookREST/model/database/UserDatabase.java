package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Address;
import no.stonedstonar.BookREST.model.User;
import no.stonedstonar.BookREST.model.UserRegister;
import no.stonedstonar.BookREST.model.exceptions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a user interface to a database.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class UserDatabase implements UserRegister {

    private Statement statement;

    /**
      * Makes an instance of the UserDatabase class.
      * @param connection the connection to the database.
      * @throws SQLException gets thrown if the connection to the DB could not be made.
      */
    public UserDatabase(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }


    @Override
    public void addUser(User user) throws CouldNotAddUserException, SQLException {
        checkUser(user);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE personID = " + user.getUserID());
        if (!resultSet.next()){
            statement.executeUpdate("INSERT INTO person(personID, firstName, lastName, email, pass) VALUES(" + user.getUserID() + "," + makeSQLString(user.getFirstName()) + "," + makeSQLString(user.getLastName()) + "," + makeSQLString(user.getEMail()) + "," +makeSQLString(user.getPassword()) + ");");
            List<Address> addressList = user.getAllAddresses();
            for (Address address : addressList){
                statement.executeUpdate("INSERT INTO personAddress(personID, streetName, houseNumber, houseLetter, houseFloor, apartmentNumber, postalCode) VALUES( " + user.getUserID() + "," + makeSQLString(address.getStreetName()) + ","
                        + address.getHouseNumber() + "," + makeSQLString(address.getHouseLetter() + "") + "," + address.getFloor() + "," + address.getApartmentNumber() + "," + address.getPostalCode() +" );");
            }
        }else {
            throw new CouldNotAddUserException("The user with the ID " + user.getUserID() + " is already in the system.");
        }
    }


    @Override
    public void removeUser(User user) throws CouldNotRemoveUserException, SQLException {
        checkUser(user);
        int amount = statement.executeUpdate("DELETE FROM person WHERE personID = " + user.getUserID() + ";");
        if (amount == 0){
            throw new CouldNotRemoveUserException("The user with the userID " + user.getUserID() + " could not be removed from the system.");
        }
        statement.executeUpdate("DELETE FROM personaddress WHERE personID = " + user.getUserID() + ";");

    }


    @Override
    public User getUserById(long userID) throws CouldNotGetUserException, SQLException {
        checkUserID(userID);
        ResultSet userSet = statement.executeQuery("SELECT * FROM person WHERE personID = " + userID + ";");
        if (!userSet.next()){
            throw new CouldNotGetUserException("The user with the id " + userID + " could not be found in the database.");
        }
        return makeSQLIntoUser(userSet);

    }

    @Override
    public User loginToUser(String email, String password) throws CouldNotLoginToUser, CouldNotGetUserException, SQLException {
        checkString(email, "email");
        checkString(password, "password");
        User user;
        ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE email = " + makeSQLString(email) + ";");
        if (resultSet.next()){
            user = makeSQLIntoUser(resultSet);
            if (!user.checkIfPasswordIsCorrect(password)){
                throw new CouldNotLoginToUser("The passwords does not match on this user.");
            }
        }else {
            throw new CouldNotGetUserException("The user with the email " + email + " could not be found in the database.");
        }
        return user;
    }

    @Override
    public boolean checkIfRegisterHasUsers() throws SQLException {
        return statement.executeQuery("SELECT * FROM person").next();
    }

    /**
     * Makes a new statement to execute querys on.
     * @return a new statement to execute querys on.
     * @throws SQLException gets thrown if the connection to the database is closed.
     */
    private Statement makeStatement() throws SQLException {
        return statement.getConnection().createStatement();
    }

    /**
     * Makes result set into a user object.
     * @param resultSet the user result set.
     * @return the user.
     * @throws SQLException gets thrown if one of the result set is empty.
     */
    private User makeSQLIntoUser(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()){
            resultSet.next();
        }

        Statement statement2 = makeStatement();
        ResultSet addressSet = statement2.executeQuery("SELECT * FROM personAddress WHERE personID = " + resultSet.getInt("personID") + ";");

        List<Address> addresses = makeSQLIntoAddress(addressSet);
        User user = new User(resultSet.getLong("personID"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getString("pass"));
        addresses.forEach(address -> {
            try {
                user.addAddress(address);
            } catch (CouldNotAddAddressException e) {
                System.err.println(e.getMessage());
            }
        });
        return user;
    }

    /**
     * Makes the SQL response into addresses.
     * @param addressSet the address set.
     * @return a list with all the addresses.
     * @throws SQLException gets thrown if the resultset is empty.
     */
    private List<Address> makeSQLIntoAddress(ResultSet addressSet) throws SQLException {
        List<Address> addresses = new LinkedList<>();
        while (addressSet.next()){
            addresses.add(new Address(addressSet.getString("streetName"), addressSet.getInt("houseNumber"), addressSet.getString("houseLetter").charAt(0), addressSet.getInt("houseFloor"), addressSet.getInt("apartmentNumber"), addressSet.getInt("postalCode")));
        }
        return addresses;
    }



    /**
     * Checks if the user is not null.
     * @param user the user to check.
     */
    private void checkUser(User user){
        checkIfObjectIsNull(user, "user");
    }

    /**
     * Checks if the userID is invalid.
     * @param userID the userID to check.
     */
    private void checkUserID(long userID){
        checkIfLongIsAboveZero(userID, "userID");
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
