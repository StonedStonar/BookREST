package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.CouldNotAddUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotLoginToUser;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveUserException;

import java.sql.SQLException;
import java.util.List;

/**
 * Represents a basic user register and its methods.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface UserRegister {

    /**
     * Adds a user to the register.
     * @param user the user you want to add.
     * @throws CouldNotAddUserException gets thrown if the user is already in the register.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void addUser(User user) throws CouldNotAddUserException, SQLException;

    /**
     * Removes a user from the register.
     * @param user the user to remove.
     * @throws CouldNotRemoveUserException gets thrown if the user could not be found.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void removeUser(User user) throws CouldNotRemoveUserException, SQLException;

    /**
     * Gets a user by its userID.
     * @param userID the userID to search for.
     * @throws CouldNotGetUserException gets thrown if the user could not be found.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     * @return the user matching that ID.
     */
    User getUserById(long userID) throws CouldNotGetUserException, SQLException;

    /**
     * Gets the user if the input password matches the set password.
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the user that matches that email and password.
     * @throws CouldNotGetUserException gets thrown if the user could not be found.
     * @throws CouldNotLoginToUser gets thrown if the passwords does not match.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    User loginToUser(String email, String password) throws CouldNotGetUserException, CouldNotLoginToUser, SQLException;

    /**
     * Checks if the user register has users.
     * @return <code>true</code> if the register has users.
     *         <code>false</code> if the register has no users.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    boolean checkIfRegisterHasUsers() throws SQLException;

}
