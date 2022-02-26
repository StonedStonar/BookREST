package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import java.sql.SQLException;
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
     * @throws DuplicateObjectException gets thrown if the lent book is already in the system.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void addLentBook(LentBook lentBook) throws DuplicateObjectException, SQLException;

    /**
     * Removes a lent book from the system.
     * @param lentBook the lent book to remove.
     * @throws RemoveObjectException gets thrown if the lent book could not be removed.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void removeLentBook(LentBook lentBook) throws RemoveObjectException, SQLException;

    /**
     * Gets a lent book with branch id.
     * @param branchBookID the branch book id.
     * @return the lent book that matches the branch book id.
     * @throws GetObjectException gets thrown if the lent book could not be located.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    LentBook getLentBook(long branchBookID) throws GetObjectException, SQLException;

    /**
     * Removes a lent book with branch book id.
     * @param branchBookID the branch book id to remove.
     * @throws RemoveObjectException gets thrown if the book could not be removed.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void removeLentBookByBranchBookID(long branchBookID) throws RemoveObjectException, SQLException, GetObjectException;

    /**
     * Gets all the due books for the whole library.
     * @return a list with all the due books.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    List<LentBook> getAllDueBooks() throws SQLException;

    /**
     * Gets all due books for a branch.
     * @param branchID the branchID for the wanted branch.
     * @param amountOfDays the amount of days forward in time.
     * @return a list with books that are due in this branch.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    List<LentBook> getAllDueBooksForBranch(long branchID, int amountOfDays);

    /**
     * Gets all the lent books for a branchID.
     * @param branchID the branchID.
     * @return a list of all the lent books
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    List<LentBook> getAllBooksWithBranchID(long branchID) throws SQLException;

    /**
     * Gets all the books that are due for the user.
     * @param userID the userID to search for.
     * @return a list with all the books for the user.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    List<LentBook> getAllDueBooksForUser(long userID) throws SQLException;
}
