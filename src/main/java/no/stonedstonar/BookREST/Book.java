package no.stonedstonar.BookREST;


import java.util.LinkedList;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class Book {

    private long ID;

    private String title;

    private LinkedList<Integer> authors;

    private int year;

    private int numberOfPages;

    public Book(){

    }
    /**
      * Makes an instance of the Books class.
      * @param ID the ID the book has.
      * @param authors a list with all the authors of this book with their ID's.
      * @param title the title of the book.
      * @param year the year of the book.
      * @param numberOfPages the amount of pages in the book.
      */
    public Book(long ID, String title, List<Integer> authors, int year, int numberOfPages){
        checkID(ID);
        checkTitle(title);
        checkNumberOfPages(numberOfPages);
        this.authors = new LinkedList<>();
        this.ID = ID;
        this.year = year;
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.authors.addAll(authors);
    }

    /**
     * Gets the ID of the book.
     * @return the ID
     */
    public long getID() {
        return ID;
    }

    /**
     * Sets the ID to a new value.
     * @param ID the new ID.
     */
    public void setID(long ID) {
        checkID(ID);
        this.ID = ID;
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
     * Checks if the title is not null or empty.
     * @param title the title to check.
     */
    private void checkTitle(String title){
        checkString(title, "title");
    }

    /**
     * Checks if the number of pages is above zero.
     * @param numberOfPages the numberofpages to check.
     */
    private void checkNumberOfPages(int numberOfPages){
        checkIfLongIsAboveZero((long) numberOfPages, "number of pages");
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
