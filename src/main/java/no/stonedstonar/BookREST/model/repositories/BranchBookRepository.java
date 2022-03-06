package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.BranchBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface BranchBookRepository extends CrudRepository<BranchBook, Long> {

    @Query(value = "SELECT * FROM branchbook WHERE isbn = :isbn AND branchBookID NOT IN (SELECT branchBookID FROM lentbook)", nativeQuery = true)
    List<BranchBook> getAllBooksWithISBNThatIsFree(@Param(value = "isbn") long isbn);

    @Query(value = "SELECT * FROM branchbook WHERE isbn = :isbn AND branchBookID IN (SELECT branchBookID FROM lentbook)", nativeQuery = true)
    List<BranchBook> getAllBooksWithISBNThatIsLent(@Param(value = "isbn") long isbn);

    @Query(value = "SELECT * FROM branchbook WHERE branchbookID NOT IN (SELECT branchBookID FROM lentbook)", nativeQuery = true)
    List<BranchBook> getAllBranchBooksThatIsNotLent();

    @Query(value = "SELECT * FROM branchbook WHERE branchbookID IN (SELECT branchBookID FROM lentbook)", nativeQuery = true)
    List<BranchBook> getAllBranchBooksThatIsLent();

    @Query(value = "SELECT count(branchBookId) FROM lentbook WHERE branchBookId  = :branchBookID", nativeQuery = true)
    int checkIfBranchBookIsLent(@Param(value = "branchBookID")long branchBookID);

    @Query(value = "SELECT * FROM branchbook WHERE branchID = :branchID AND branchBookID NOT IN (SELECT branchBookID FROM lentbook)", nativeQuery = true)
    List<BranchBook> getAllBranchBooksThatIsFree(@Param(value = "branchID") long branchID);

    @Query(value = "SELECT * FROM branchbook WHERE branchID = :branchID AND branchBookID IN (SELECT branchBookID FROM lentbook)", nativeQuery = true)
    List<BranchBook> getAllBranchBooksThatIsLent(@Param(value = "branchID") long branchID);
}
