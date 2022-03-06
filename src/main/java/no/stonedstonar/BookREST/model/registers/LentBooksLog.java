package no.stonedstonar.BookREST.model.registers;

import no.stonedstonar.BookREST.model.ReturnedLentBook;
import no.stonedstonar.BookREST.model.exceptions.*;

import java.sql.SQLException;
import java.time.LocalDate;
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
     * @throws CouldNotAddLentBookException gets thrown if the returned book is already in the register.
     */
    void addReturnedLentBook(ReturnedLentBook returnedLentBook) throws CouldNotAddLentBookException;

    /**
     * Removes a lent book with the id.
     * @param lentBookId the id of the lent book to be removed.
     * @throws CouldNotRemoveLentBookException gets thrown if the lent book could not be removed.
     */
    void removeLentBookWithLentBookID(long lentBookId) throws CouldNotRemoveLentBookException;

    /**
     * Removes all the lent books with the branch book id.
     * @param branchBookID the branch books id.
     * @throws CouldNotRemoveLentBookException gets thrown if one of the removal processes fail.
     */
    void removeLentBookWithBranchBookID(long branchBookID) throws CouldNotRemoveLentBookException;

    /**
     * Gets a returned book by the lent book id.
     * @param lentBookID the lent book id to search for.
     * @throws CouldNotGetLentBookException gets thrown if the lent book could not be located.
     */
    ReturnedLentBook getLentBook(long lentBookID) throws CouldNotGetLentBookException;

    /**
     * Updates the returned lent book with new details.
     * @param returnedLentBook the new returned book.
     * @throws CouldNotGetLentBookException gets thrown if the returned lent book is not in the register.
     */
    void updateReturnedLentBook(ReturnedLentBook returnedLentBook) throws CouldNotGetLentBookException;

    /**
     * Gets all the times the book with this ID has been lent.
     * @param branchBookID the branch book ID of the book that was lent.
     * @return all the different times this book has been lent.
     */
    List<ReturnedLentBook> getAllTheTimesBookHasBeenLent(long branchBookID);

    /**
     * Gets all the lent books that are in the log.
     * @return a list with all the lent books
     */
    List<ReturnedLentBook> getAllReturnedBooks();
}
