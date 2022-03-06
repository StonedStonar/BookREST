package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.Book;
import no.stonedstonar.BookREST.model.ReturnedLentBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface LentBooksLogRepository extends CrudRepository<ReturnedLentBook, Long> {

    @Query(value = "SELECT *  FROM returnedlentbook  WHERE branchBookID = :branchBookID", nativeQuery = true)
    List<ReturnedLentBook> findAllTheTimesBookHasBeenLentOut(@Param(value = "branchBookID") long branchBookID);
}
