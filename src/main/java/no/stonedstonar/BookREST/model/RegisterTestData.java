package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddBookException;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that holds methods to fill the registers with test data.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class RegisterTestData {

    /**
     * Adds predefined books to a register.
     * @param normalBookRegister the book register to fill.
     * @throws CouldNotAddBookException gets thrown if a book could not be added.
     */
    public static void addBooksToRegister(NormalBookRegister normalBookRegister) throws CouldNotAddBookException {
        checkIfObjectIsNull(normalBookRegister, "book register");
        List<Long> authors = new ArrayList<>();
        List<Long> authors2 = new ArrayList<>();
        List<Long> authors3 = new ArrayList<>();
        List<Long> authors4 = new ArrayList<>();

        authors.add(1L);
        authors2.add(2L);
        authors3.add(3L);
        authors3.add(4L);
        authors4.add(5L);

        normalBookRegister.addBook(new Book(1, "Snømannen", authors,2007, 438));
        normalBookRegister.addBook(new Book(2, "Kniv", authors,2019, 519));
        normalBookRegister.addBook(new Book(3, "Lars Monsen - mitt liv", authors2, 2020, 246));
        normalBookRegister.addBook(new Book(4, "Verden ifølge Vinni", authors3, 2021, 236));
        normalBookRegister.addBook(new Book(5, "Min skyld - en historie om frigjøring", authors4, 2021, 239));
    }

    /**
     * Adds all the predefined authors to an author register.
     * @param authorRegister the author register to add to.
     * @throws CouldNotAddAuthorException gets thrown if one author could not be added.
     */
    public static void addAuthorsToRegister(AuthorRegister authorRegister) throws CouldNotAddAuthorException {
        authorRegister.addAuthor(new Author(1, "Jo", "Nesbø", 1960));
        authorRegister.addAuthor(new Author(2, "Lars", "Monsen", 1963));
        authorRegister.addAuthor(new Author(3, "Øyvind", "Sauvik", 1976));
        authorRegister.addAuthor(new Author(4, "Nils", "Anker", 1961));
        authorRegister.addAuthor(new Author(5, "Abid", "Raja", 1975));
    }
    
    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private static void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }
}
