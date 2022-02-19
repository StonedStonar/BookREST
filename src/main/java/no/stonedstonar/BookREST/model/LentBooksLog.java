package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import java.util.List;

/**
 * Represents a log for lent books.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface LentBooksLog{

    /**
     * Adds a returned book to the system.
     * @param returnedLentBook the returned book.
     * @throws DuplicateObjectException gets thrown if the returned book is already in the register.
     */
    void addReturnedLentBook(ReturnedLentBook returnedLentBook) throws DuplicateObjectException;

    /**
     * Removes a returned book from the system.
     * @param returnedLentBook the book you want to remove.
     * @throws RemoveObjectException gets thrown if the returned lent book could not be removed.
     */
    void removeReturnedLentBook(ReturnedLentBook returnedLentBook) throws RemoveObjectException;

    /**
     * Gets all the times the book with this ID has been lent.
     * @param bookID the ID of the book that was lent.
     * @param branchID the branch ID this book belongs to.
     * @return all the different times this book has been lent.
     */
    List<LentBook> getAllTheTimesBookHasBeenLent(long bookID, long branchID);
}
