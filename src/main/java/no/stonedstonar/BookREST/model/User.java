package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAddressException;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents a basic user object that can hold all the details needed for a user.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long userID;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String eMail;

    @OneToMany(orphanRemoval = true)
    @JoinTable(name = "userAddress",
                joinColumns = @JoinColumn(columnDefinition = "userID", referencedColumnName = "userID"),
                inverseJoinColumns = @JoinColumn(columnDefinition = "addressID", referencedColumnName = "addressID"))
    private List<Address> addresses = new java.util.ArrayList<>();

    @Column(nullable = false)
    private String password;

    /**
     * Gets all addresses as a list.
     * @return the addresses.
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * Empty constructor for JPA.
     */
    public User() {
    }

    /**
     * Makes an instance of the User class.
     * @param firstName the first name of the borrower.
     * @param lastName the last name of the borrower.
     * @param eMail the email of the user.
     * @param password the password of the user.
     * @param userID the id of the user.
     * @param addresses the list with addresses.
     */
    @JsonCreator
    public User(@JsonProperty("userID") long userID, @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName, @JsonProperty("eMail") String eMail, @JsonProperty("password") String password,
                @JsonProperty("addresses") List<Address> addresses){
        checkFirstName(firstName);
        checkLastName(lastName);
        checkUserID(userID);
        checkPassword(password);
        checkIfObjectIsNull(this.addresses, "addresses");
        this.addresses = new LinkedList<>();
        this.addresses.addAll(addresses);
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
    public User(@JsonProperty("userID") long userID, @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName, @JsonProperty("eMail") String eMail, @JsonProperty("password") String password){
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

    public void addAddress(Address address) throws CouldNotAddAddressException {
        checkIfObjectIsNull(address, "address");
        if (!this.addresses.contains(address)){
            addresses.add(address);
        }else {
            throw new CouldNotAddAddressException("The address " + address.toString() +  " is already added.");
        }
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword(){
        return password;
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
