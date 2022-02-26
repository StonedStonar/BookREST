package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import java.sql.SQLException;
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
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void addReturnedLentBook(ReturnedLentBook returnedLentBook) throws DuplicateObjectException, SQLException;

    /**
     * Removes a returned book from the system.
     * @param returnedLentBook the book you want to remove.
     * @throws RemoveObjectException gets thrown if the returned lent book could not be removed.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void removeReturnedLentBook(ReturnedLentBook returnedLentBook) throws RemoveObjectException;

    /**
     * Gets all the times the book with this ID has been lent.
     * @param bookID the ID of the book that was lent.
     * @param branchID the branch ID this book belongs to.
     * @return all the different times this book has been lent.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    List<ReturnedLentBook> getAllTheTimesBookHasBeenLent(long bookID, long branchID) throws SQLException;

    /**
     * Gets all the lent books that are in the log.
     * @return a list with all the lent books
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    List<ReturnedLentBook> getAllReturnedBooks() throws SQLException;
}
