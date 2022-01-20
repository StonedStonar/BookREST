package no.stonedstonar.BookREST;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import no.stonedstonar.BookREST.exceptions.CouldNotAddBookException;
import no.stonedstonar.BookREST.exceptions.CouldNotGetBookException;
import no.stonedstonar.BookREST.exceptions.CouldNotRemoveBookException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@RestController
public class BookController {

    private BookRegister bookRegister;
    /**
      * Makes an instance of the BookController class.
      */
    public BookController() throws CouldNotAddBookException {
        bookRegister = new BookRegister();
        RegisterTestData.addBooksToRegister(bookRegister);
    }

    /**
     * Gets all the books in the register.
     * @return all the books in the register.
     */
    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRegister.getBookList();
    }

    /**
     * Gets a book by its id.
     * @param id the ID of the book.
     * @return a book that matches that description.
     * @throws CouldNotGetBookException gets thrown if the book could not be found.
     */
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable long id) throws CouldNotGetBookException {
        return bookRegister.getBook(id);
    }

    /**
     * Adds a new book to the register.
     * @param body the body that contains the JSON file.
     * @throws JsonProcessingException gets thrown if the JSON could not be translated to the wanted object.
     * @throws CouldNotAddBookException gets thrown if the book could not be added.
     */
    @PostMapping("/books")
    public void postBook(@RequestBody String body) throws JsonProcessingException, CouldNotAddBookException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = objectMapper.readValue(body, Book.class);
        bookRegister.addBook(book);
    }

    /**
     * Deletes a book from the book register.
     * @param id the ID of the book.
     * @throws CouldNotGetBookException gets thrown if the book could not be found.
     * @throws CouldNotRemoveBookException gets thrown if the book could not be removed.
     */
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) throws CouldNotGetBookException, CouldNotRemoveBookException {
        Book book = bookRegister.getBook(id);
        bookRegister.removeBook(book);
    }

    /**
     * Handles the CouldNotGetBookException.
     * @param exception the exception that was thrown.
     * @return a response according to the exception.
     */
    @ExceptionHandler(CouldNotGetBookException.class)
    public ResponseEntity<String> handleCouldNotGetBookException(CouldNotGetBookException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


    /**
     * Handles a CouldNotRemoveBookException
     * @param exception the exception that was thrown.
     * @return a response according to the exception
     */
    @ExceptionHandler(CouldNotRemoveBookException.class)
    public ResponseEntity<String> handleCouldNotRemoveBookException(CouldNotGetBookException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles the CouldNotAddBookException.
     * @param exception the exception that was thrown.
     * @return a response according to the exception.
     */
    @ExceptionHandler(CouldNotAddBookException.class)
    public ResponseEntity<String> handleCouldNotAddBookException(CouldNotAddBookException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
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
