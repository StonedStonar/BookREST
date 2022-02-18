package no.stonedstonar.BookREST.model;
/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class BranchBook {

    private final long branchBookID;

    private final long isbn;

    private final long branchID;

    /**
     * Makes an instance of the BranchBook class.
     * @param branchBookID the branch book id.
     * @param isbn the books ISBN code.
     * @param branchID the branch ID.
     */
    public BranchBook(long branchBookID, long isbn, long branchID){
        checkIfLongIsAboveZero(branchBookID, "branch book id");
        checkIfLongIsAboveZero(isbn, "ISBN");
        checkIfLongIsAboveZero(branchID, "branch ID");

        this.isbn = isbn;
        this.branchBookID = branchBookID;
        this.branchID = branchID;
    }

    /**
     * Gets the branch book ID.
     * @return the branch book ID.
     */
    public long getBranchBookID() {
        return branchBookID;
    }

    /**
     * Gets the ISBN this branch book is underneath.
     * @return the ISBN of the book.
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * Gets the branchID that is book belongs to.
     * @return the branch ID.
     */
    public long getBranchID() {
        return branchID;
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
}
