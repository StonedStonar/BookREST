package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.LentBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface LentBookRepository extends CrudRepository<LentBook, Long> {

    /**
     * Finds a book based on the branch books id.
     * @param branchBookID the branch books id.
     * @return the book that has this branch book id.
     */
    @Query(value = "SELECT * FROM lentbook WHERE branchbookid = :branchBookID", nativeQuery = true)
    LentBook findByBranchBookID(@Param("branchBookID") long branchBookID);

    @Query(value = "SELECT * FROM lentbook WHERE dueDate < curdate()", nativeQuery = true)
    List<LentBook> findLentBooksByDueDate();

    @Query(value = "SELECT * FROM lentbook JOIN branchbook b USING(branchBookID) WHERE dueDate < date_add(curdate(), INTERVAL :amountOfDays DAY) AND branchID  = :branchID", nativeQuery = true)
    List<LentBook> findLentBooksByBranchIDAndDueDate(@Param(value = "branchID") long branchID, @Param(value = "amountOfDays") int amountOfDays);

    @Query(value = "SELECT * FROM lentbook JOIN branchbook USING(branchBookID) WHERE branchID = :branchID", nativeQuery = true)
    List<LentBook> findLentBooksByBranchID(@Param(value = "branchID") long branchID);

    @Query(value = "SELECT * FROM lentbook WHERE userID = :userID AND dueDate < curdate()", nativeQuery = true)
    List<LentBook> findLentBooksThatHasUserIDAndAfterDueDate(@Param(value = "userID")long userID);
}
