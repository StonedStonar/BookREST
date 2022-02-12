package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Address;
import no.stonedstonar.BookREST.model.User;
import no.stonedstonar.BookREST.model.UserRegister;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAddressException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveUserException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class UserDatabase implements UserRegister {

    private Statement statement;
    /**
      * Makes an instance of the UserDatabase class.
      * @param connection the connection to the database.
      */
    public UserDatabase(Connection connection){
        try {
            statement = connection.createStatement();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void addUser(User user) throws CouldNotAddUserException {
        checkUser(user);
        try {
            statement.executeUpdate("INSERT INTO person(personID, firstName, lastName, email) VALUES(" + user.getUserID() + "," + makeSQLString(user.getFirstName()) + "," + makeSQLString(user.getLastName()) + "," + makeSQLString(user.getEMail()) + ");");
            List<Address> addressList = user.getAllAddresses();
            for (Address address : addressList){
                try {
                    statement.executeUpdate("INSERT INTO personAddress(personID, streetName, houseNumber, houseLetter, houseFloor, apartmentNumber, postalCode) VALUES( " + user.getUserID() + "," + makeSQLString(address.getStreetName()) + ","
                            + address.getHouseNumber() + "," + makeSQLString(address.getHouseLetter() + "") + "," + address.getFloor() + "," + address.getApartmentNumber() + "," + address.getPostalCode() +" );");
                }catch (SQLException exception){
                    System.err.println("Could not add address " + address.toString());
                }
            }
        } catch (SQLException exception) {
            throw new CouldNotAddUserException("The user with the ID " + user.getUserID() + " is already in the system.");
        }
    }

    @Override
    public void removeUser(User user) throws CouldNotRemoveUserException {
        checkUser(user);
        try {
            statement.executeUpdate("DELETE FROM person WHERE personID = " + user.getUserID() + ";");
        } catch (SQLException exception) {
            throw new CouldNotRemoveUserException("The user with the userID " + user.getUserID() + " could not be removed from the system.");
        }
    }

    @Override
    public User getUserById(long userID) throws CouldNotGetUserException {
        checkUserID(userID);
        try {
            ResultSet userSet = statement.executeQuery("SELECT * FROM person WHERE personID = " + userID + ";");
            ResultSet addressSet = statement.executeQuery("SELECT * FROM personAddress WHERE personID = " + userID + ";");
            return makeSQLIntoUser(userSet, addressSet);
        } catch (SQLException exception) {
            throw new CouldNotGetUserException("The user with the id " + userID + " could not be found in the database.");
        }
    }

    /**
     * Makes result set into a user object.
     * @param resultSet the user result set.
     * @param addressSet the address result set.
     * @return the user.
     * @throws SQLException gets thrown if one of the result set is empty.
     */
    private User makeSQLIntoUser(ResultSet resultSet, ResultSet addressSet) throws SQLException {
        List<Address> addresses = makeSQLIntoAddress(addressSet);
        User user = new User(resultSet.getLong("userID"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"));
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
