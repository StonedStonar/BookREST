package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * Finds all the books with author id.
     * @param authorID the author id to look for.
     * @return books that are written by this author.
     */
    @Query(value = "SELECT * FROM book b JOIN authorsofbook a USING(isbn) WHERE a.authorId = :authorID", nativeQuery = true)
    List<Book> findAllBookWithAuthorID(@Param(value = "authorID") long authorID);

}
