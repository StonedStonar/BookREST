package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAddressException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAddressException;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a basic user object that can hold all the details needed for a user.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class User {

    private long userID;

    private String firstName;

    private String lastName;

    private String eMail;

    private final LinkedList<Address> addresses;

    private String password;


    /**
     * Makes an instance of the User class.
     * @param firstName the first name of the borrower.
     * @param lastName the last name of the borrower.
     * @param eMail the email of the user.
     * @param password the password of the user.
     * @param userID the id of the user.
     * @param addresses the list with all the addresses.
     */
    public User(long userID, String firstName, String lastName, String eMail, String password, List<Address> addresses){
        checkFirstName(firstName);
        checkLastName(lastName);
        checkUserID(userID);
        checkPassword(password);
        checkIfObjectIsNull(addresses, "addresses");
        this.addresses = new LinkedList<>();
        addresses.addAll(addresses);
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.userID = userID;
        this.password = password;
    }

    /**
     * Makes an instance of the User class.
     * @param firstName the first name of the borrower.
     * @param lastName the last name of the borrower.
     * @param eMail the email of the user.
     */
    @JsonCreator
    public User(long userID, String firstName, String lastName, String eMail, String password){
        checkFirstName(firstName);
        checkLastName(lastName);
        checkUserID(userID);
        checkPassword(password);
        addresses = new LinkedList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.userID = userID;
        this.password = password;
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword(){
        return password;
    }

    /**
     * Adds an address to the users list of addresses.
     * @param address the address to add.
     * @throws CouldNotAddAddressException gets thrown if the address is already in the register.
     */
    public void addAddress(Address address) throws CouldNotAddAddressException {
        checkAddress(address);
        if (addresses.contains(address)){
            throw new CouldNotAddAddressException("The address is already in the list.");
        }else {
            addresses.add(address);
        }
    }

    /**
     * Removes an address from the user.
     * @param address the address to remove.
     * @throws CouldNotRemoveAddressException gets thrown if the address is not a part of this user.
     */
    public void removeAddress(Address address) throws CouldNotRemoveAddressException {
        checkAddress(address);
        if (!addresses.remove(address)){
            throw new CouldNotRemoveAddressException("The input address does not belong to this user.");
        }
    }

    /**
     * Checks if the password is correct to the set password. This is case-sensitive.
     * @param password the password to try.
     * @return <code>true</code> if the passwords match.
     *         <code>false</code> if the passwords does not match.
     */
    public boolean checkIfPasswordIsCorrect(String password){
        checkPassword(password);
        return this.password.equals(password);
    }

    /**
     * Changes the password if the input is correct.
     * @param newPassword the new password of the user.
     * @param oldPassword the old password of the user.
     * @return <code>true</code> if the password was changed.
     *         <code>false</code> if the password was not changed.
     */
    public boolean changePassword(String newPassword, String oldPassword){
        checkPassword(newPassword);
        checkPassword(oldPassword);
        boolean valid = checkIfPasswordIsCorrect(oldPassword);
        if (valid){
            this.password = newPassword;
        }
        return valid;
    }

    /**
     * Gets all the addresses this user has.
     * @return all the addresses.
     */
    public List<Address> getAllAddresses(){
        return addresses;
    }

    /**
     * Gets the userID.
     * @return the userID.
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Changes the userID to the input value.
     * @param userID the new userID.
     */
    public void setUserID(long userID) {
        checkUserID(userID);
        this.userID = userID;
    }

    /**
     * Gets the first name of the user.
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name to a new value.
     * @param firstName the new first name.
     */
    public void setFirstName(String firstName) {
        checkFirstName(firstName);
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * @param lastName the new last name.
     */
    public void setLastName(String lastName) {
        checkLastName(lastName);
        this.lastName = lastName;
    }

    /**
     * Gets the email of the user.
     * @return the email.
     */
    public String getEMail() {
        return eMail;
    }

    //Todo: Fiks en sjekk på emailen så den er riktig format.
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * Checks if the userID is above zero.
     * @param ID the userID to check.
     */
    private void checkUserID(long ID){
        checkIfLongIsAboveZero(ID, "userID");
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
     * Checks if the input password is not null and empty.
     * @param password the password to check.
     */
    private void checkPassword(String password){
        checkString(password, "password");
    }


    /**
     * Checks if the address is null.
     * @param address the address to check.
     */
    private void checkAddress(Address address){
        checkIfObjectIsNull(address, "addres");
    }

    /**
     * Checks if the firstname is not null or empty.
     * @param firstName the firstname to check.
     */
    private void checkFirstName(String firstName){
        checkString(firstName, "firstname");
    }

    /**
     * Checks if the lastname is not null or empty.
     * @param lastName the lastname to check.
     */
    private void checkLastName(String lastName){
        checkString(lastName, "lastname");
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
