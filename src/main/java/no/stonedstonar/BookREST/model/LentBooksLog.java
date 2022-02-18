package no.stonedstonar.BookREST.model;

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
     */
    void addReturnedLentBook(ReturnedLentBook returnedLentBook);

    /**
     * Removes a returned book from the system.
     * @param returnedLentBook the book you want to remove.
     */
    void removeReturnedLentBook(ReturnedLentBook returnedLentBook);

    /**
     * Gets all the times the book with this ID has been lent.
     * @param bookID the ID of the book that was lent.
     * @param branchID the branch ID this book belongs to.
     * @return all the different times this book has been lent.
     */
    List<LentBook> getAllTheTimesBookHasBeenLent(long bookID, long branchID);
}
