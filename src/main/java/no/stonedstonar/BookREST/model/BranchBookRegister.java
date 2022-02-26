package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;

import java.sql.SQLException;
import java.util.List;

/**
 * Represents a register for a branch and its books. All branches share the same bookID route for now.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface BranchBookRegister {

    /**
     * Adds a branch book to the system.
     * @param branchBook the branch book to add.
     * @throws DuplicateObjectException gets thrown if the branch book with the same ID is already in the register.
     */
    void addBranchBook(BranchBook branchBook) throws DuplicateObjectException, SQLException;

    /**
     * Removes a branch book from the system.
     * @param branchBook the branch book to remove.
     * @throws RemoveObjectException gets thrown if the branch book could not be removed.
     */
    void removeBranchBook(BranchBook branchBook) throws RemoveObjectException, SQLException;

    /**
     * Gets a branch book.
     * @param branchBookID the branch book ID.
     * @return the branch book that matches the ID.
     * @throws GetObjectException gets thrown if there is no book with that branch book id in the system.
     */
    BranchBook getBranchBook(long branchBookID) throws GetObjectException, SQLException;

    /**
     * Gets all the branch books for the branch with the input ID.
     * @param branchID the branch ID.
     * @return a list with all the books for the branch.
     */
    List<BranchBook> getAllBranchBooksForBranchWithID(long branchID) throws SQLException;
}
