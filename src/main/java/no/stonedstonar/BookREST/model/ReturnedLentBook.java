package no.stonedstonar.BookREST.model;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a returned book.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Entity
public class ReturnedLentBook extends LentBook {

    private LocalDate returnedDate;



    /**
     * Constructor for JPA.
     */
    public ReturnedLentBook() {
    }

    /**
     * Makes an instance of the ReturnedLentBook class.
     * @param branchBook the branchbook that is lent.
     * @param userID the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be delivered.
     * @param returnedDate the date this book was returned.
     */
    public ReturnedLentBook(BranchBook branchBook, User user, LocalDate lentDate, LocalDate dueDate, LocalDate returnedDate){
        super(branchBook, user, lentDate, dueDate);
        checkReturnedDate(returnedDate);
        this.returnedDate = returnedDate;
    }

    /**
     * Makes an instance of the ReturnedLentBook class.
     * @param lentBook the book that was lent.
     * @param returnedDate the date this book was returned.
     */
    public ReturnedLentBook(LentBook lentBook, LocalDate returnedDate){
        super(lentBook.getBranchBook(), lentBook.getUser(), lentBook.getLentDate(), lentBook.getDueDate());
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
