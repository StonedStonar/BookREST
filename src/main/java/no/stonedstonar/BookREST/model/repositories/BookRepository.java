package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.Book;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface BookRepository extends CrudRepository<Book, Long> {

    /**
     * Finds all the books with authorid.
     * @param authorID the author id to look for.
     * @return books that are written by this author.
     */
    //@Query("SELECT b FROM book b JOIN authorofbook USING(isbn) JOIN author USING(id) WHERE auhtorID =  ?1")
    //Iterable<Book> findAllBookWithAuthorID(long authorID);

}
