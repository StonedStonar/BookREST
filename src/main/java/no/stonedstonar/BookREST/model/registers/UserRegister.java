package no.stonedstonar.BookREST.model.registers;

import no.stonedstonar.BookREST.model.User;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotLoginToUser;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveUserException;
import org.springframework.stereotype.Service;

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
     */
    void addUser(User user) throws CouldNotAddUserException;

    /**
     * Removes a user from the register.
     * @param user the user to remove.
     * @throws CouldNotRemoveUserException gets thrown if the user could not be found..
     */
    void removeUser(User user) throws CouldNotRemoveUserException;

    /**
     * Gets a user by its userID.
     * @param userID the userID to search for.
     * @throws CouldNotGetUserException gets thrown if the user could not be found.
     * @return the user matching that ID.
     */
    User getUserById(long userID) throws CouldNotGetUserException;

    /**
     * Gets the user if the input password matches the set password.
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the user that matches that email and password.
     * @throws CouldNotLoginToUser gets thrown if the passwords does not match.
     */
    User loginToUser(String email, String password) throws CouldNotLoginToUser;

    /**
     * Checks if the email is taken.
     * @param email the email to check for.
     * @return <code>true</code> if the email is taken.
     *         <code>false</code> if the email is not taken.
     */
    boolean checkIfEmailIsTaken(String email);

    /**
     * Checks if the user register has users.
     * @return <code>true</code> if the register has users.
     *         <code>false</code> if the register has no users.
     */
    boolean checkIfRegisterHasUsers();

    /**
     * Gets all the users in the system.
     * @return the list with all the users.
     */
    List<User> getAllUsers();
}
