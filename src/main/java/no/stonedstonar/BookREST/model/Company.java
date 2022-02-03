package no.stonedstonar.BookREST.model;
/**
 * Represents a basic company with a name and some other deatils.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class Company {

    private long companyID;

    private String companyName;

    /**
      * Makes an instance of the Company class.
      * @param companyID the ID of the company.
      * @param companyName the name of the company.
      */
    public Company(long companyID, String companyName){
        checkCompanyName(companyName);
        checkCompanyID(companyID);
        this.companyID = companyID;
        this.companyName = companyName;
    }

    /**
     * Gets the company ID.
     * @return the ID of the company.
     */
    public long getCompanyID() {
        return companyID;
    }

    /**
     * Sets the company ID to a new value.
     * @param companyID the company ID.
     */
    public void setCompanyID(long companyID) {
        checkCompanyID(companyID);
        this.companyID = companyID;
    }

    /**
     * Gets the company name.
     * @return the name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Changes the company name to the input name.
     * @param companyName the new company name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Checks if the ID is above zero.
     * @param ID the ID to check.
     */
    private void checkCompanyID(long ID){
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
     * Checks if the company name is not null.
     * @param companyName the name to check.
     */
    private void checkCompanyName(String companyName){
        checkString(companyName, "company name");
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
