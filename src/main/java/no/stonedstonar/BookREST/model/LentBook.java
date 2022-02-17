package no.stonedstonar.BookREST.model;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a book that is lent by a user.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class LentBook {

    private long branchID;

    private long bookID;

    private long userID;

    private LocalDate lentDate;

    private LocalDate dueDate;

    private LocalDate returnedDate;

    /**
     * Makes an instance of the LentBook class.
     * @param branchID the ID of the branch this book is lent from.
     * @param bookID the bookID of the lent book.
     * @param userID the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be delivered.
     */
    public LentBook(long branchID, long bookID, long userID, LocalDate lentDate, LocalDate dueDate){
        setDetails(branchID, bookID, userID, lentDate, dueDate);
    }

    /**
     * Makes an instance of the LentBook class. This book has set lent date to today.
     * @param branchID the ID of the branch this book is lent from.
     * @param bookID the bookID of the lent book.
     * @param userID the users ID.
     * @param dueDate the date this book is supposed to be delivered.
     */
    public LentBook(long branchID, long bookID, long userID, LocalDate dueDate){
        setDetails(branchID, bookID, userID, LocalDate.now(), dueDate);
    }

    /**
     * Setst the details of the book.
     * @param branchID the ID of the branch this book is lent from.
     * @param bookID the bookID of the lent book.
     * @param userID the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be returned.
     */
    private void setDetails(long branchID, long bookID, long userID, LocalDate lentDate, LocalDate dueDate){
        checkIfLongIsAboveZero(branchID, "branchID");
        checkIfLongIsAboveZero(bookID, "bookID");
        checkIfLongIsAboveZero(userID, "userID");
        checkIfObjectIsNull(dueDate, "due date");
        checkIfObjectIsNull(lentDate, "lent date");
        if (dueDate.isBefore(lentDate)){
            throw new DateTimeException("The due date cannot be after the lent date.");
        }
        this.dueDate = dueDate;
        this.lentDate = lentDate;
        this.branchID = branchID;
        this.bookID = bookID;
        this.userID = userID;
    }

    /**
     * Gets the ID of the branch this book is lent out from.
     * @return the branch id this book belongs to.
     */
    public long getBranchID() {
        return branchID;
    }

    /**
     * Gets the id of the book.
     * @return the ID of the book.
     */
    public long getBookID() {
        return bookID;
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
     * @return the dudate of this book.
     */
    public LocalDate getDueDate(){
        return dueDate;
    }

    /**
     * Gets the date this book was returned. Returns null if the book is not returned.
     * @return the date this book was returned.
     */
    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    /**
     * Sets the date this book was returned.
     * @param returnedDate the date this book was returned.
     */
    public void setReturnedDate(LocalDate returnedDate){
        checkIfObjectIsNull(returnedDate, "returned date");
        if (returnedDate.isBefore(lentDate)){
            throw new DateTimeException("The book cannot be returned ");
        }
        this.returnedDate = returnedDate;
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
