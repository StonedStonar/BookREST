package no.stonedstonar.BookREST.model;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a book that is lent by a user.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class LentBook {

    private long branchBookID;

    private long userID;

    private LocalDate lentDate;

    private LocalDate dueDate;

    /**
     * Makes an instance of the LentBook class.
     * @param branchBookID the bookID of the lent book.
     * @param userID the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be delivered.
     */
    public LentBook(long branchBookID, long userID, LocalDate lentDate, LocalDate dueDate){
        setDetails(branchBookID, userID, lentDate, dueDate);
    }

    /**
     * Makes an instance of the LentBook class. This book has set lent date to today.
     * @param branchBookID the bookID of the lent book.
     * @param userID the users ID.
     * @param dueDate the date this book is supposed to be delivered.
     */
    public LentBook(long branchBookID, long userID, LocalDate dueDate){
        setDetails(branchBookID, userID, LocalDate.now(), dueDate);
    }

    /**
     * Sets the details of the book.
     * @param bookID the bookID of the lent book.
     * @param userID the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be returned.
     */
    private void setDetails(long bookID, long userID, LocalDate lentDate, LocalDate dueDate){
        checkIfLongIsAboveZero(bookID, "bookID");
        checkIfLongIsAboveZero(userID, "userID");
        checkIfObjectIsNull(dueDate, "due date");
        checkIfObjectIsNull(lentDate, "lent date");
        if (dueDate.isBefore(lentDate)){
            throw new DateTimeException("The due date cannot be after the lent date.");
        }
        this.dueDate = dueDate;
        this.lentDate = lentDate;
        this.branchBookID = bookID;
        this.userID = userID;
    }

    /**
     * Gets the id of the book.
     * @return the ID of the book.
     */
    public long getBranchBookID() {
        return branchBookID;
    }

    /**
     * Gets the userID.
     * @return the ID of the user who lent this book.
     */
    public long getUserID() {
        return userID;
    }

    /**
     * Gets the date this book was lent.
     * @return the date this book was lent.
     */
    public LocalDate getLentDate() {
        return lentDate;
    }

    /**
     * Gets the due date of this book.
     * @return the due date of this book.
     */
    public LocalDate getDueDate(){
        return dueDate;
    }

    /**
     * Checks if the input long is above zero.
     * @param number the number to check.
     * @param prefix the prefix the error should have.
     */
    protected void checkIfLongIsAboveZero(long number, String prefix){
        if (number <= 0){
            throw new IllegalArgumentException("The " + prefix + " must be above zero.");
        }
    }

    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    protected void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }
}
