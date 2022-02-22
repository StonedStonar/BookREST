package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.CouldNotAddUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotLoginToUser;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveUserException;

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
     * @throws CouldNotRemoveUserException gets thrown if the user could not be found.
     */
    void removeUser(User user) throws CouldNotRemoveUserException;

    /**
     * Gets a user by its userID.
     * @param userID the userID to search for.
     * @throws CouldNotGetUserException gets thrown if the user could not be found.
     * @return
     */
    User getUserById(long userID) throws CouldNotGetUserException;

    /**
     * Gets the user if the input password matches the set password.
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the user that matches that email and password.
     * @throws CouldNotGetUserException gets thrown if the user could not be found.
     * @throws CouldNotLoginToUser gets thrown if the passwords does not match.
     */
    User loginToUser(String email, String password) throws CouldNotGetUserException, CouldNotLoginToUser;

}
