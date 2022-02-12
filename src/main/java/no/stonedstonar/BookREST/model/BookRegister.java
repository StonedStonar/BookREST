package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.CouldNotAddBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveBookException;

import java.util.Collection;
import java.util.List;

/**
 * Represents what a basic book register should have of methods.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface BookRegister {

    /**
     * Adds a new book to the register.
     * @param book the book you want to add.
     * @throws  CouldNotAddBookException gets thrown if the book could not be added.
     */
    void addBook(Book book) throws CouldNotAddBookException;

    /**
     * Removes a book from the register.
     * @param book the book you want to remove.
     * @throws CouldNotRemoveBookException gets thrown if the book is not in the register.
     */
    void removeBook(Book book) throws CouldNotRemoveBookException;

    /**
     * Removes a book from the register with the bookID.
     * @param ID the ID of the book.
     * @throws CouldNotRemoveBookException gets thrown if the book is not in the register.
     */
    void removeBookByID(long ID) throws CouldNotRemoveBookException;

    /**
     * Gets all the books this author is a part of.
     * @param authorID the author's id.
     * @return the book that has this author as a part of it.
     */
    List<Book> getAllBooksOfAuthorID(long authorID);

    /**
     * Gets a book based on its bookID.
     * @param bookID the bookID of the wanted book.
     * @return a book that matches the bookID
     * @throws CouldNotGetBookException gets thrown if the book could not be found.
     */
    Book getBook(long bookID) throws CouldNotGetBookException;

    /**
     * Gets a list of all the books in the register.
     * @return list with all the books in the register.
     */
    List<Book> getBookList();
}
