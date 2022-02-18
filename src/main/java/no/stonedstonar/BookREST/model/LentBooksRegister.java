package no.stonedstonar.BookREST.model;

import java.util.List;

/**
 * Represents a lent books register.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface LentBooksRegister {

    /**
     * Adds a lent book to the register.
     * @param lentBook the book to add to the system.
     */
    void addLentBook(LentBook lentBook);

    /**
     * Removes a lent book from the system.
     * @param lentBook the lent book to remove.
     */
    void removeLentBook(LentBook lentBook);

    /**
     * Gets all the due books for the whole library.
     * @return a list with all the due books.
     */
    List<LentBook> getAllDueBooks();

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

    /**
     * Gets all the books that are due in a certain branch.
     * @param userId the id of the user.
     * @param branchID the id of the branch.
     * @return
     */
    LentBook getAllDueBooksForUserInBranch(long userId, long branchID);
}
