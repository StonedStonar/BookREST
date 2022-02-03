package no.stonedstonar.BookREST.model;


import no.stonedstonar.BookREST.model.exceptions.CouldNotAddBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveBookException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represets a book register.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class NormalBookRegister {

    private List<Book> bookList;

    /**
      * Makes an instance of the BookRegister class.
      */
    public NormalBookRegister(){
        bookList = new ArrayList<>();
    }

    /**
     * Adds a book to the register.
     * @param book the book you want to add.
     * @throws CouldNotAddBookException gets thrown if the book is already in the register.
     */
    public void addBook(Book book) throws CouldNotAddBookException {
        checkBook(book);
        if (bookList.stream().noneMatch(book2 -> book2.getISBN() == book.getISBN())){
            bookList.add(book);
        }else {
            throw new CouldNotAddBookException("The book is already in the register.");
        }
    }

    /**
     * Removes a book from the register.
     * @param book the book you want to remove.
     * @throws CouldNotRemoveBookException gets thrown if a book could not be removed.
     */
    public void removeBook(Book book) throws CouldNotRemoveBookException {
        checkBook(book);
        if (!bookList.remove(book)){
            throw new CouldNotRemoveBookException("The input book could not be removed.");
        }
    }

    /**
     * Gets all the books this authorID is a part of.
     * @param authorID the author ID to check.
     * @return a list with all the books this author is a part of.
     */
    public List<Book> getAllBooksOfAuthorID(long authorID){
        checkAuthorID(authorID);
        return bookList.stream().filter(book -> book.checkIfAuthorIsPartOfBook(authorID)).toList();
    }


    public Book getBook(long bookID) throws CouldNotGetBookException {
        checkID(bookID);
        Optional<Book> optionalBook = bookList.stream().filter(book -> book.getISBN() == bookID).findFirst();
        if (optionalBook.isPresent()){
            return optionalBook.get();
        }else {
            throw new CouldNotGetBookException("The book with the ID " + bookID + " could not be found in the register.");
        }
    }

    /**
     * Checks if the book is not null.
     * @param bookToCheck the book you want to check.
     */
    private void checkBook(Book bookToCheck){
        checkIfObjectIsNull(bookToCheck, "book");
    }

    /**
     * Checks if the author ID is above zero.
     * @param authorID the author ID.
     */
    private void checkAuthorID(long authorID){
        checkIfLongIsAboveZero(authorID, "authorID");
    }


    /**
     * Checks if the ID is above zero.
     * @param ID the ID to check.
     */
    private void checkID(long ID){
        checkIfLongIsAboveZero(ID, "ID");
    }

    /**
     * Checks if the input long is above zero.
     * @param number the number to check.
     * @param prefix the prefix the error should have.
     */
    private void checkIfLongIsAboveZero(long number, String prefix){
        if (number <= 0){
            throw new IllegalArgumentException("The " + prefix + " must be above zero.");
        }
    }
    
    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }

    /**
     * Gets all the books in the register.
     * @return the list with all the books.
     */
    public List<Book> getBookList(){
        return bookList;
    }
}
