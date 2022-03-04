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
    @Query(value = "SELECT l FROM lentbook l WHERE l.branchBookID  = :branchBookID", nativeQuery = true)
    Optional<LentBook> findByBranchBookID(@Param("branchBookID") long branchBookID);

    @Query(value = "SELECT * FROM lentbook l WHERE dueDate < curdate()", nativeQuery = true)
    List<LentBook> findLentBooksByDueDate();

    @Query(value = "SELECT * FROM lentbook l JOIN branchbook b USING(branchBookID) WHERE l.dueDate < date_add(curdate(), INTERVAL :amountOfDays DAY) AND b.branchID  = :branchID", nativeQuery = true)
    List<LentBook> findLentBooksByBranchIDAndDueDate(@Param(value = "branchID") long branchID, @Param(value = "amountOfDays") int amountOfDays);

    @Query(value = "SELECT * FROM lentbook l JOIN branchbook b USING(branchBookID) WHERE b.branchID = :branchID", nativeQuery = true)
    List<LentBook> findLentBooksByBranchID(@Param(value = "branchID") long branchID);

    @Query(value = "SELECT * FROM lentbook l WHERE userID = :userID AND dueDate < curdate()", nativeQuery = true)
    List<LentBook> findLentBooksThatHasUserIDAndAfterDueDate(@Param(value = "userID")long userID);
}
