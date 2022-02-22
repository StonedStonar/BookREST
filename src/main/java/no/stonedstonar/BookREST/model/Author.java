package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class Author {

    private long ID;

    private String firstName;

    private String lastName;

    private int birthYear;

    /**
      * Makes an instance of the Author class.
      * @param ID the ID of the author.
      * @param firstName the first name of the author.
      * @param lastName the last name of the author.
      * @param birthYear the birth year of the author.
      */
    @JsonCreator
    public Author(long ID, String firstName, String lastName, int birthYear){
        checkFirstName(firstName);
        checkLastName(lastName);
        checkID(ID);
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    /**
     * Gets the ID of the author.
     * @return the ID.
     */
    public long getID() {
        return ID;
    }

    /**
     * Sets the ID of the author.
     * @param ID the new ID of the author.
     */
    public void setID(long ID) {
        checkID(ID);
        this.ID = ID;
    }

    /**
     * Gets the first name of the author.
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the author.
     * @param firstName the new first name.
     */
    public void setFirstName(String firstName) {
        checkFirstName(firstName);
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the author.
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the author.
     * @param lastName the last name.
     */
    public void setLastName(String lastName) {
        checkLastName(lastName);
        this.lastName = lastName;
    }

    /**
     * Gets the birth year of the author.
     * @return the birth year.
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Sets the birth year to a new value.
     * @param birthYear the new birth year.
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * Checks the first name of the input is valid.
     * @param firstName the first name to check.
     */
    public void checkFirstName(String firstName){
        checkString(firstName, "firstname");
    }

    /**
     * Checks if the last name of the input is valid.
     * @param lastName the lastname to check.
     */
    public void checkLastName(String lastName){
        checkString(lastName, "lastname");
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
