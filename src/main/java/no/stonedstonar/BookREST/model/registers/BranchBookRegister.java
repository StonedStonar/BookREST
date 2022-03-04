package no.stonedstonar.BookREST.model.registers;

import no.stonedstonar.BookREST.model.Branch;
import no.stonedstonar.BookREST.model.BranchBook;
import no.stonedstonar.BookREST.model.exceptions.*;

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
     * @throws CouldNotAddBranchBookException gets thrown if the branch book with the same ID is already in the register.
     */
    void addBranchBook(BranchBook branchBook) throws CouldNotAddBranchBookException;

    /**
     * Removes a branch book from the system.
     * @param branchBook the branch book to remove.
     * @throws CouldNotRemoveBranchBookException gets thrown if the branch book could not be removed.
     */
    void removeBranchBook(BranchBook branchBook) throws CouldNotRemoveBranchBookException;

    /**
     * Removes a branch book with the id.
     * @param branchBookId the id of the branch book to remove.
     * @throws CouldNotRemoveBranchBookException gets thrown if the branch book could not be removed.
     */
    void removeBranchBookWithId(long branchBookId) throws CouldNotRemoveBranchBookException;

    /**
     * Updates the branch book that is input.
     * @param branchBook the branch book to update.
     * @throws CouldNotGetBranchBookException gets thrown if there is no branch book in the register with that id.
     */
    void updateBranchBook(BranchBook branchBook) throws CouldNotGetBranchBookException;

    /**
     * Gets a branch book.
     * @param branchBookID the branch book ID.
     * @return the branch book that matches the ID.
     * @throws CouldNotGetBranchBookException gets thrown if there is no book with that branch book id in the system.
     */
    BranchBook getBranchBook(long branchBookID) throws CouldNotGetBranchBookException;

    /**
     * Gets all the branch books for the branch with the input ID.
     * @param branchID the branch ID.
     * @return a list with all the books for the branch.
     */
    List<BranchBook> getAllBranchBooksForBranchWithID(long branchID);

    /**
     * Gets all the branch books in the system.
     * @return a list with all the branch books.
     */
    List<BranchBook> getAllBranchBooks();


    /**
     * Checks if the branch books is empty or not.
     * @return <code>true</code> if the register has branch books.
     *         <code>false</code> if the register does not have branch books.
     */
    boolean checkIfBranchBooksRegisterHasBooks();
}
