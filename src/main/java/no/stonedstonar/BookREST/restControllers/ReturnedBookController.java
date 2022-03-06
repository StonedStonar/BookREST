package no.stonedstonar.BookREST.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.stonedstonar.BookREST.model.ReturnedLentBook;
import no.stonedstonar.BookREST.model.database.LentBooksLogJPA;
import no.stonedstonar.BookREST.model.exceptions.*;
import no.stonedstonar.BookREST.model.registers.LentBooksLog;
import no.stonedstonar.BookREST.model.repositories.LentBooksLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@RestController
@RequestMapping("/returnedBooks")
public class ReturnedBookController {

    private final LentBooksLog lentBooksLog;

    /**
     * Makes an instance of the ReturnedBookController class.
     * @param lentBooksLogRepository the lent books log repository.
     */
    public ReturnedBookController(LentBooksLogRepository lentBooksLogRepository) {
        this.lentBooksLog =  new LentBooksLogJPA(lentBooksLogRepository);
    }

    /**
     * Removes a lent book from the register.
     * @param lentBookID the lent book logs id.
     * @throws CouldNotRemoveLentBookException gets thrown if the lent book could not be removed.
     */
    @DeleteMapping("/{lentBookID}")
    public void removeLentBook(@PathVariable long lentBookID) throws CouldNotRemoveLentBookException {
        lentBooksLog.removeLentBookWithLentBookID(lentBookID);
    }

    /**
     * Removes all the returned books with this branch book id.
     * @param branchBookID the branch book id.
     * @throws CouldNotRemoveLentBookException gets thrown if the book could not be located.
     */
    @DeleteMapping
    public void removeReturnedBookWithBranchBookID(@RequestParam(value = "branchBookID") long branchBookID) throws CouldNotRemoveLentBookException {
        lentBooksLog.removeLentBookWithBranchBookID(branchBookID);
    }

    /**
     * Gets a returned book by the id.
     * @param lentBookID the id of the returned book.
     * @return the history of who lent this book.
     * @throws CouldNotGetLentBookException gets thrown if the lent book could not be found.
     */
    @GetMapping("/{lentBookID}")
    public ReturnedLentBook getReturnedBook(@PathVariable long lentBookID) throws CouldNotGetLentBookException {
        ReturnedLentBook returnedLentBook = lentBooksLog.getLentBook(lentBookID);
        return returnedLentBook;
    }

    /**
     * Gets all the lent books from the register.
     * @return a list with all the lent books.
     */
    @GetMapping
    public List<ReturnedLentBook> getLog(){
        return lentBooksLog.getAllReturnedBooks();
    }

    /**
     * Handles a invalid JSON format request.
     * @return the response according to exception.
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonFormatFault(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format on JSON file is invalid.");
    }

    /**
     * Handles the CouldNotGetBookException.
     * @param exception the exception that was thrown.
     * @return a response according to the exception.
     */
    @ExceptionHandler(CouldNotGetLentBookException.class)
    public ResponseEntity<String> handleCouldNotGetBookException(CouldNotGetBookException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


    /**
     * Handles a CouldNotRemoveBookException
     * @param exception the exception that was thrown.
     * @return a response according to the exception
     */
    @ExceptionHandler(CouldNotRemoveLentBookException.class)
    public ResponseEntity<String> handleCouldNotRemoveBookException(CouldNotGetBookException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles the CouldNotAddBookException.
     * @param exception the exception that was thrown.
     * @return a response according to the exception.
     */
    @ExceptionHandler(CouldNotAddLentBookException.class)
    public ResponseEntity<String> handleCouldNotAddBookException(CouldNotAddBookException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }



    /**
     * Checks if a string is of a valid format or not.
     *
     * @param stringToCheck the string you want to check.
     * @param errorPrefix   the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix) {
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()) {
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
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
