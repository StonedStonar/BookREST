package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Represents a branch of the library.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Entity
public class Branch {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private long branchID;

    @Column(nullable = false)
    private String branchName;

    /**
     * Empty constructor for JPA.
     */
    public Branch() {
    }

    /**
     * Makes an instance of the Branch class.
     * @param branchID the branch id this branch should have.
     * @param branchName the name of the branch.
     */
    @JsonCreator
    public Branch(@JsonProperty("branchID") long branchID, @JsonProperty("branchName") String branchName){
        checkIfBranchIDIsValid(branchID);
        checkIfBranchNameIsValid(branchName);
        this.branchID = branchID;
        this.branchName = branchName;
    }

    /**
     * Gets the branchID of this branch.
     * @return the branch ID.
     */
    public long getBranchID() {
        return branchID;
    }

    /**
     * Gets the branch's name.
     * @return the branchID.
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Sets the branch name to a new value.
     * @param branchName the new branch name.
     */
    public void setBranchName(String branchName) {
        checkIfBranchNameIsValid(branchName);
        this.branchName = branchName;
    }

    /**
     * Checks if a branch name is not null.
     * @param branchName the branch name to check.
     */
    private void checkIfBranchNameIsValid(String branchName){
        checkString(branchName, "branch name");
    }

    /**
     * Checks if the branch ID is above zero.
     * @param branchID the branchID to check.
     */
    private void checkIfBranchIDIsValid(long branchID){
        checkIfLongIsAboveZero(branchID, "branchID");
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