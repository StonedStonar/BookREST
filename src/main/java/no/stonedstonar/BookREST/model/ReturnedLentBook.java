package no.stonedstonar.BookREST.model;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a returned book.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class ReturnedLentBook extends LentBook {

    private LocalDate returnedDate;

    /**
     * Makes an instance of the ReturnedLentBook class.
     * @param branchID the ID of the branch this book is lent from.
     * @param bookID the bookID of the lent book.
     * @param userID the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be delivered.
     * @param returnedDate the date this book was returned.
     */
    public ReturnedLentBook(long branchID, long bookID, long userID, LocalDate lentDate, LocalDate dueDate, LocalDate returnedDate){
        super(branchID, bookID, userID, lentDate, dueDate);
        checkReturnedDate(returnedDate);
        this.returnedDate = returnedDate;
    }

    /**
     * Makes an instance of the ReturnedLentBook class.
     * @param lentBook the book that was lent.
     * @param returnedDate the date this book was returned.
     */
    public ReturnedLentBook(LentBook lentBook, LocalDate returnedDate){
        super(lentBook.getBranchID(), lentBook.getBranchBookID(), lentBook.getUserID(), lentBook.getLentDate(), lentBook.getDueDate());
        checkReturnedDate(returnedDate);

        this.returnedDate = returnedDate;
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
        checkReturnedDate(returnedDate);

        this.returnedDate = returnedDate;
    }

    /**
     * Checks if the returned date is not null and after the set lent date.
     * @param returnedDate the return date.
     */
    private void checkReturnedDate(LocalDate returnedDate){
        checkIfObjectIsNull(returnedDate, "returned date");
        if (returnedDate.isBefore(getLentDate())){
            throw new DateTimeException("The book cannot be returned since the return date is before the lent out date.");
        }
    }
}
