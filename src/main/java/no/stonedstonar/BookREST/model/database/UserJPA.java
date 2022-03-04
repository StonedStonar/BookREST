package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Address;
import no.stonedstonar.BookREST.model.User;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotLoginToUser;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveUserException;
import no.stonedstonar.BookREST.model.registers.UserRegister;
import no.stonedstonar.BookREST.model.repositories.AddressRepository;
import no.stonedstonar.BookREST.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class UserJPA implements UserRegister {

    private UserRepository userRepository;

    private AddressRepository addressRepository;

    /**
     * Makes an instance of the UserJPA class
     * @param userRepository the user repository.
     */
    public UserJPA(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public void addUser(User user) throws CouldNotAddUserException {
        checkIfUserIsNotNull(user);
        if (!userRepository.existsById(user.getUserID())){
            addressRepository.saveAll(user.getAddresses());
            userRepository.save(user);
        }else {
            throw new CouldNotAddUserException("The user with id " + user.getUserID() + " is already in the system.");
        }

    }

    @Override
    public void removeUser(User user) throws CouldNotRemoveUserException {
        checkIfUserIsNotNull(user);
        if (userRepository.existsById(user.getUserID())){
            userRepository.delete(user);
        }else {
            throw new CouldNotRemoveUserException("The user with id " + user.getUserID() + " could not be located in the system");
        }
    }

    @Override
    public User getUserById(long userID) throws CouldNotGetUserException {
        checkIfUserIdIsValid(userID);
        Optional<User> opUser = userRepository.findById(userID);
        if (opUser.isEmpty()){
            throw new CouldNotGetUserException("The user with id " + userID + " is not in the system.");
        }
        return opUser.get();
    }

    @Override
    public User loginToUser(String email, String password) throws CouldNotGetUserException, CouldNotLoginToUser {

        return null;
    }

    @Override
    public boolean checkIfRegisterHasUsers() {
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Checks if the user is not null.
     * @param user the user to check.
     */
    private void checkIfUserIsNotNull(User user){
        checkIfObjectIsNull(user, "user");
    }

    /**
     * Checks if the user id is above zero.
     * @param userId the userid to check.
     */
    private void checkIfUserIdIsValid(long userId){
        checkIfLongIsAboveZero(userId, "user id");
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
     *
     * @param stringToCheck the string you want to check.
     * @param errorPrefix   the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix) {
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()) {
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
