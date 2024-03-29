package no.stonedstonar.BookREST.model.registers;

import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;

import java.sql.SQLException;
import java.util.List;

/**
 * Represents a basic overview on what a author register should have of methods.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface AuthorRegister {

    /**
     * Adds a new author to the register.
     * @param author the author to add.
     * @throws CouldNotAddAuthorException gets thrown if the author is already in the register.
     */
    void addAuthor(Author author) throws CouldNotAddAuthorException;

    /**
     * Removes an author from the register.
     * @param author the author to remove.
     * @throws CouldNotRemoveAuthorException gets thrown if the author could not be removed form the register.
     */
    void removeAuthor(Author author) throws CouldNotRemoveAuthorException;

    /**
     * Removes an author based on their id.
     * @param authorID the author id to remove.
     * @throws CouldNotRemoveAuthorException gets thrown if the author is not in the system.
     */
    void removeAuthorWithId(long authorID) throws CouldNotRemoveAuthorException;

    /**
     * Updates the details of an author in the system.
     * @param author the author to update.
     * @throws CouldNotGetAuthorException gets thrown if the author could not be located.
     */
    void updateAuthor(Author author) throws CouldNotGetAuthorException;

    /**
     * Gets an author by its ID.
     * @param authorID the ID of the author.
     * @return an author that has that ID.
     * @throws CouldNotGetAuthorException gets thrown if the author could not be found.
     */
    Author getAuthorById(long authorID) throws CouldNotGetAuthorException;

    /**
     * Gets the whole list of authors.
     * @return a list with all the authors.
     */
    List<Author> getAuthorList();
}
