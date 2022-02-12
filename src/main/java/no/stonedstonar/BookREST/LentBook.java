package no.stonedstonar.BookREST;
/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class LentBook {

    private long bookID;

    private long userID;

    /**
      * Makes an instance of the LentBook class.
      * @param bookID
      * @param userID
      */
    public LentBook(long bookID, long userID){
    
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