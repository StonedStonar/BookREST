package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;

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
     * Gets an author by its ID.
     * @param authorID the ID of the author.
     * @return a author that has that ID.
     * @throws CouldNotGetAuthorException gets thrown if the author could not be found.
     */
    Author getAuthorById(long authorID) throws CouldNotGetAuthorException;
}
