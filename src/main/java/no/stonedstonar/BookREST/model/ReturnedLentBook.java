package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a returned book.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Entity
public class ReturnedLentBook {

    @Id
    @Column(nullable = false)
    private long returnedBookID;

    @ManyToOne(targetEntity = BranchBook.class)
    @JoinColumn(name = "branchBookId")
    private BranchBook branchBook;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="userID")
    private User user;

    @Column(nullable = false)
    private LocalDate lentDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalDate returnedDate;

    public long getReturnedBookID() {
        return returnedBookID;
    }


    /**
     * Constructor for JPA.
     */
    public ReturnedLentBook() {

    }

    /**
     * Makes an instance of the ReturnedLentBook class.
     * @param branchBook the branchbook that is lent.
     * @param user the user lending the book.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be delivered.
     * @param returnedDate the date this book was returned.
     */
    @JsonCreator
    public ReturnedLentBook(@JsonProperty("branchBook") BranchBook branchBook, @JsonProperty("user") User user, @JsonProperty("lentDate") LocalDate lentDate,
                            @JsonProperty("dueDate") LocalDate dueDate, @JsonProperty("returnedDate") LocalDate returnedDate,
                            @JsonProperty("lentBookId") long lentBookID){
        checkIfObjectIsNull(branchBook, "branch book");
        checkIfObjectIsNull(dueDate, "due date");
        checkIfObjectIsNull(user, "user");
        checkIfObjectIsNull(lentDate, "lent date");
        checkReturnedDate(returnedDate, lentDate);
        this.lentDate = lentDate;
        this.returnedDate = returnedDate;
        this.branchBook = branchBook;
        this.dueDate = dueDate;
        this.user = user;
        this.returnedBookID = lentBookID;
    }

    /**
     * Makes an instance of the ReturnedLentBook class.
     * @param lentBook the book that was lent.
     * @param returnedDate the date this book was returned.
     */
    public ReturnedLentBook(LentBook lentBook, LocalDate returnedDate){
        checkReturnedDate(returnedDate, lentBook.getLentDate());
        checkIfObjectIsNull(lentBook, "lent book");
        this.lentDate = lentBook.getLentDate();
        this.branchBook = lentBook.getBranchBook();
        this.returnedDate = returnedDate;
        this.dueDate = lentBook.getDueDate();
        this.user = lentBook.getUser();
        this.returnedBookID = lentBook.getLentBookId();
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
        checkReturnedDate(returnedDate, lentDate);

        this.returnedDate = returnedDate;
    }

    /**
     * Gets the branch book.
     * @return gets the branch book this lent book represents.
     */
    public BranchBook getBranchBook(){
        return branchBook;
    }

    /**
     * Gets the user from the lent object.
     * @return the user.
     */
    public User getUser(){
        return user;
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
     * Checks if the returned date is not null and after the set lent date.
     * @param returnedDate the return date.
     * @param lentDate the date this book was lent.
     */
    private void checkReturnedDate(LocalDate returnedDate, LocalDate lentDate){
        checkIfObjectIsNull(returnedDate, "returned date");
        if (returnedDate.isBefore(lentDate)){
            throw new DateTimeException("The book cannot be returned since the return date is before the lent out date.");
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
