package no.stonedstonar.BookREST.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents what a basic book should hold of information.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class Book {

    private long isbn;

    private String title;

    private final LinkedList<Long> authors;

    private int year;

    private int numberOfPages;

    private long publisherID;

    /**
     * Makes a basic book with all the fields set to a default invalid value.
     */
    public Book(){
        this.isbn = 0;
        this.title = "";
        this.authors = new LinkedList<>();
        this.numberOfPages = 0;
        this.year = Integer.MIN_VALUE;
    }

    /**
      * Makes an instance of the Books class.
      * @param isbn the ISBN the book has.
      * @param authors a list with all the authors of this book with their ID's.
      * @param title the title of the book.
      * @param year the year of the book.
      * @param numberOfPages the amount of pages in the book.
      *  @param publisherID the ID of the publisher.
      */
    @JsonCreator
    public Book(long isbn, String title, List<Long> authors, int year, int numberOfPages, long publisherID){
        checkISBN(isbn);
        checkTitle(title);
        checkNumberOfPages(numberOfPages);
        checkPublisherID(publisherID);
        this.authors = new LinkedList<>();
        this.isbn = isbn;
        this.year = year;
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.authors.addAll(authors);
        this.publisherID = publisherID;
    }

    /**
     * Adds an author to the book.
     * @param authorID the ID of the author to add.
     * @throws CouldNotAddAuthorException gets thrown if the author could not be added.
     */
    public void addAuthor(long authorID) throws CouldNotAddAuthorException {
        checkAuthorID(authorID);
        if (!authors.contains(authorID)){
            authors.add(authorID);
        }else {
            throw new CouldNotAddAuthorException("The author with the ID " + authorID + " is already a part of this book.");
        }
    }

    /**
     * Gets the publishers ID.
     * @return the ID the publisher has.
     */
    public long getPublisherID() {
        return publisherID;
    }

    /**
     * Sets the publisher ID to a new value.
     * @param publisherID the publisher ID.
     */
    public void setPublisherID(long publisherID) {
        this.publisherID = publisherID;
    }

    /**
     * Checks if the input author is a part of this book.
     * @param authorID the authors ID.
     * @return <code>true</code> if the author is already in this book.
     *         <code>false</code> if the author is not already in this book.
     */
    public boolean checkIfAuthorIsPartOfBook(long authorID){
        checkAuthorID(authorID);
        return authors.stream().anyMatch(number -> number == authorID);
    }

    /**
     * Removes an authors ID from this book.
     * @param authorID the ID of the author.
     * @throws CouldNotRemoveAuthorException gets thrown if the author could not be removed.
     */
    public void removeAuthor(long authorID) throws CouldNotRemoveAuthorException {
        checkAuthorID(authorID);
        if (!authors.remove(authorID)){
            throw new CouldNotRemoveAuthorException("The author with the ID " + authorID + "is not a part of this book.");
        }
    }

    /**
     * Gets the list with all the authors ID's.
     * @return a list with all the authors ID's.
     */
    public List<Long> getAuthors(){
        return authors;
    }

    /**
     * Gets the ID of the book.
     * @return the ID
     */
    public long getISBN() {
        return isbn;
    }

    /**
     * Sets the ID to a new value.
     * @param isbn the new ISBN.
     */
    public void setISBN(long isbn) {
        checkISBN(isbn);
        this.isbn = isbn;
    }

    /**
     * Sets the title to a new value.
     * @return the new title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title to a new value.
     * @param title the new title.
     */
    public void setTitle(String title) {
        checkTitle(title);
        this.title = title;
    }

    /**
     * Gets the year this book was published.
     * @return the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year this book was published.
     * @param year the year this book was published.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the amount of pages in the book.
     * @return the amount of pages.
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * Sets the number of pages of the book.
     * @param numberOfPages the number of pages in the book.
     */
    public void setNumberOfPages(int numberOfPages) {
        checkNumberOfPages(numberOfPages);
        this.numberOfPages = numberOfPages;
    }

    /**
     * Checks if the author ID is above zero.
     * @param authorID the author ID.
     */
    private void checkAuthorID(long authorID){
        checkIfLongIsAboveZero(authorID, "authorID");
    }

    /**
     * Checks if the title is not null or empty.
     * @param title the title to check.
     */
    private void checkTitle(String title){
        checkString(title, "title");
    }

    /**
     * Checks if the publisher ID is above zero.
     * @param publisherID the publisher ID to check.
     */
    private void checkPublisherID(long publisherID){
        checkIfLongIsAboveZero(publisherID, "publisherID");
    }

    /**
     * Checks if the number of pages is above zero.
     * @param numberOfPages the numberofpages to check.
     */
    private void checkNumberOfPages(int numberOfPages){
        checkIfLongIsAboveZero(numberOfPages, "number of pages");
    }

    /**
     * Checks if the ID is above zero.
     * @param ID the ID to check.
     */
    private void checkISBN(long ID){
        checkIfLongIsAboveZero(ID, "ISBN");
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
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix){
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()){
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
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
}
