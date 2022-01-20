package no.stonedstonar.BookREST;

import no.stonedstonar.BookREST.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.exceptions.CouldNotAddBookException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class RegisterTestData {

    /**
     * Adds predefined books to a register.
     * @param bookRegister the book register to fill.
     * @throws CouldNotAddBookException gets thrown if a book could not be added.
     */
    public static void addBooksToRegister(BookRegister bookRegister) throws CouldNotAddBookException {
        checkIfObjectIsNull(bookRegister, "book register");
        List<Integer> authors = new ArrayList<>();
        List<Integer> authors2 = new ArrayList<>();
        List<Integer> authors3 = new ArrayList<>();
        List<Integer> authors4 = new ArrayList<>();

        authors.add(1);
        authors2.add(2);
        authors3.add(3);
        authors3.add(4);
        authors4.add(5);

        bookRegister.addBook(new Book(1, "Snømannen", authors,2007, 438));
        bookRegister.addBook(new Book(2, "Kniv", authors,2019, 519));
        bookRegister.addBook(new Book(3, "Lars Monsen - mitt liv", authors2, 2020, 246));
        bookRegister.addBook(new Book(4, "Verden ifølge Vinni", authors3, 2021, 236));
        bookRegister.addBook(new Book(5, "Min skyld - en historie om frigjøring", authors4, 2021, 239));
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
