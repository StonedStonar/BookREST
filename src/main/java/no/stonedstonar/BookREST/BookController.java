package no.stonedstonar.BookREST;

import no.stonedstonar.BookREST.exceptions.CouldNotAddBookException;
import no.stonedstonar.BookREST.exceptions.CouldNotGetBookException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/books")
    public List<Book> getBooks(){
        return bookRegister.getBookList();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable long id) throws CouldNotGetBookException {
        return bookRegister.getBook(id);
    }

    /**
     * Handles the CouldNotGetBookException.
     * @return a response according to the exception.
     */
    @ExceptionHandler(CouldNotGetBookException.class)
    public ResponseEntity<String> handleCouldNotGetBookException(CouldNotGetBookException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
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
