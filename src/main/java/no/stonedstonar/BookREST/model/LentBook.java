package no.stonedstonar.BookREST.model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.lang.annotation.Inherited;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Represents a book that is lent by a user.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Entity
public class LentBook {

    @Id
    @GeneratedValue
    private long lentBookId;

    @ManyToOne
    @JoinColumn(name = "branchBookID", unique = true)
    @JsonIgnore
    private BranchBook branchBook;

    @Column(name = "branchBookID", updatable = false, insertable = false)
    private long branchBookID;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="userID")
    private User user;

    @Column(nullable = false)
    private LocalDate lentDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    /**
     * Gets the lent book id.
     * @return the new lent book id.
     */
    public Long getLentBookId() {
        return lentBookId;
    }

    public LentBook() {

    }

    /**
     * Makes an instance of the LentBook class.
     * @param branchBook the bookID of the lent book.
     * @param user the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be delivered.
     */
    @JsonCreator
    public LentBook(BranchBook branchBook, User user, LocalDate lentDate, LocalDate dueDate){
        setDetails(branchBook, user, lentDate, dueDate);
    }

    /**
     * Makes an instance of the LentBook class. This book has set lent date to today.
     * @param branchBook the lent branch book.
     * @param user the users ID.
     * @param dueDate the date this book is supposed to be delivered.
     */
    public LentBook(BranchBook branchBook, User user, LocalDate dueDate){
        setDetails(branchBook, user, LocalDate.now(), dueDate);
    }

    /**
     * Sets the details of the book.
     * @param branchBook the branch book.
     * @param user the users ID.
     * @param lentDate the date this book was lent.
     * @param dueDate the date this book is supposed to be returned.
     */
    private void setDetails(BranchBook branchBook, User user, LocalDate lentDate, LocalDate dueDate){
        checkIfObjectIsNull(branchBook, "branchbook");
        checkIfObjectIsNull(user, "user");
        checkIfObjectIsNull(dueDate, "due date");
        checkIfObjectIsNull(lentDate, "lent date");
        if (dueDate.isBefore(lentDate)){
            throw new DateTimeException("The due date cannot be after the lent date.");
        }
        branchBook.setTaken(true);
        this.dueDate = dueDate;
        this.lentDate = lentDate;
        this.branchBook = branchBook;
        this.user = user;
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
