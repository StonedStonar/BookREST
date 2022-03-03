package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.LentBook;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface LentBookRepository extends CrudRepository<LentBook, Long> {
}
