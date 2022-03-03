package no.stonedstonar.BookREST.model.registers;

import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.ReturnedLentBook;
import no.stonedstonar.BookREST.model.exceptions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a register for lent books.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface LentBooksRegister {

    /**
     * Adds a lent book to the register.
     * @param lentBook the book to add to the system.
     * @throws CouldNotAddLentBookException gets thrown if the lent book is already in the system.
     */
    void addLentBook(LentBook lentBook) throws CouldNotAddLentBookException;

    /**
     * Removes a returned book from the system.
     * @param lentBook the book you want to remove.
     * @param returned <code>true</code> if the book is returned.
     *                 <code>false</code> if the book is not returned.
     * @param date the date this book was returned.
     * @throws CouldNotRemoveLentBookException gets thrown if the returned lent book could not be removed.
     */
    void removeLentBook(LentBook lentBook, boolean returned, LocalDate date) throws CouldNotRemoveLentBookException;

    /**
     * Removes a lent book with its id.
     * @param lentBookID the id of the lent book to remove.
     * @throws CouldNotRemoveLentBookException gets thrown if the lent book could not be removed.
     */
    void removeLentBookWithLentBookId(long lentBookID) throws CouldNotRemoveLentBookException;

    /**
     * Gets a lent book with branch id.
     * @param branchBookID the branch book id.
     * @return the lent book that matches the branch book id.
     * @throws CouldNotGetLentBookException gets thrown if the lent book could not be located.
     */
    LentBook getLentBook(long branchBookID) throws CouldNotGetLentBookException;



    /**
     * Gets all the due books for the whole library.
     * @return a list with all the due books.
     */
    List<LentBook> getAllDueBooks();

    /**
     * Gets all the lent books.
     * @return all the lent books.
     */
    List<LentBook> getAllLentBooks();

    /**
     * Gets all due books for a branch.
     * @param branchID the branchID for the wanted branch.
     * @param amountOfDays the amount of days forward in time.
     * @return a list with books that are due in this branch.
     */
    List<LentBook> getAllDueBooksForBranch(long branchID, int amountOfDays);

    /**
     * Gets all the lent books for a branchID.
     * @param branchID the branchID.
     * @return a list of all the lent books
     */
    List<LentBook> getAllBooksWithBranchID(long branchID);

    /**
     * Gets all the books that are due for the user.
     * @param userID the userID to search for.
     * @return a list with all the books for the user.
     */
    List<LentBook> getAllDueBooksForUser(long userID);
}
