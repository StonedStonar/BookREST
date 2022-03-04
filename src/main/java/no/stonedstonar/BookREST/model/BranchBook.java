package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.*;

/**
 * Represents a book in a branch. This book holds the ISBN to the original copy of the book.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Entity
public class BranchBook {

    @Id
    @GeneratedValue
    private long branchBookID;

    @ManyToOne(targetEntity = Book.class)
    @JoinColumn(name = "isbn")
    private Book book;

    @ManyToOne(targetEntity = Branch.class)
    @JoinColumn(name = "branchID")
    private Branch branch;

    /**
     * Empty constructor for JPA.
     */
    public BranchBook() {
    }

    /**
     * Makes an instance of the BranchBook class.
     * @param branchBookID the branch book id.
     * @param book the book this represents.
     * @param branch the branch this book belongs to.
     */
    @JsonCreator
    public BranchBook(long branchBookID, Book book, Branch branch){
        checkIfLongIsAboveZero(branchBookID, "branch book id");
        checkIfObjectIsNull(book,"book");
        checkIfObjectIsNull(branch, "branch");

        this.book = book;
        this.branchBookID = branchBookID;
        this.branch = branch;
    }

    /**
     * Gets the branch book ID.
     * @return the branch book ID.
     */
    public long getBranchBookID() {
        return branchBookID;
    }

    /**
     * Gets the book.
     * @return the book this branch book represents.
     */
    public Book getBook(){
        return book;
    }

    /**
     * Gets the branch this book belongs too.
     * @return the branch this book belongs to.
     */
    public Branch getBranch(){
        return branch;
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
