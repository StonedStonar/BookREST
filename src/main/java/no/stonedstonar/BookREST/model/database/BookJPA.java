package no.stonedstonar.BookREST.model.database;


import no.stonedstonar.BookREST.model.Book;
import no.stonedstonar.BookREST.model.registers.BookRegister;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveBookException;
import no.stonedstonar.BookREST.model.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class BookJPA implements BookRegister {

    private BookRepository bookRepository;

    /**
     * Makes an instance of the BookJPA class.
     * @param bookRepository the book repository.
     */
    public BookJPA(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    @Override
    public void addBook(Book book) throws CouldNotAddBookException {
        checkIfBookIsValid(book);
        if (!bookRepository.existsById(book.getISBN())){
            bookRepository.save(book);
        }else {
            throw new CouldNotAddBookException("The book with ISBN " + book.getISBN() + " is already in the system.");
        }

    }

    @Override
    public void updateBook(Book book) throws CouldNotGetBookException {
        checkIfBookIsValid(book);
        if (bookRepository.existsById(book.getISBN())){
            bookRepository.save(book);
        }else {
            throw new CouldNotGetBookException("The book with ISBN " + book.getISBN() + " is not in the system.");
        }
    }

    @Override
    public void removeBook(Book book) throws CouldNotRemoveBookException {
        checkIfBookIsValid(book);
        if (bookRepository.existsById(book.getISBN())){
            bookRepository.delete(book);
        }else {
            throw new CouldNotRemoveBookException("The book with the ISBN " + book.getISBN() + " could not be removed.");
        }
    }

    @Override
    public void removeBookByID(long isbn) throws CouldNotRemoveBookException {
        checkIfBookIDIsAboveZero(isbn);
        if (bookRepository.existsById(isbn)){
            bookRepository.deleteById(isbn);
        }else {
            throw new CouldNotRemoveBookException("The book with ISBN " + isbn + " could not be removed.");
        }
    }

    @Override
    public List<Book> getAllBooksOfAuthorID(long authorID) {
        List<Book> books = new ArrayList<>();
        bookRepository.findAllBookWithAuthorID(authorID).forEach(books::add);
        return books;
    }

    @Override
    public Book getBook(long isbn) throws CouldNotGetBookException {
        Optional<Book> opBook = bookRepository.findById(isbn);
        if (opBook.isPresent()){
            return opBook.get();
        }else {
            throw new CouldNotGetBookException("Could not locate the book with ISBN " + isbn);
        }
    }

    @Override
    public List<Book> getBookList() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().iterator().forEachRemaining(books::add);
        return books;
    }

    /**
     * Checks if the bookID is above zero.
     * @param bookID the book id to check.
     */
    private void checkIfBookIDIsAboveZero(long bookID){
        checkIfLongIsAboveZero(bookID, "book ID");
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
     * Checks if the book is not null.
     * @param book the book to check.
     */
    private void checkIfBookIsValid(Book book){
        checkIfObjectIsNull(book, "book");
    }


    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}