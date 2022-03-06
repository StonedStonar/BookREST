package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.stonedstonar.BookREST.model.Book;
import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.database.LentBookJPA;
import no.stonedstonar.BookREST.model.exceptions.*;
import no.stonedstonar.BookREST.model.registers.LentBooksRegister;
import no.stonedstonar.BookREST.model.repositories.LentBookRepository;
import no.stonedstonar.BookREST.model.repositories.LentBooksLogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@RestController
@CrossOrigin
@RequestMapping("/lentbooks")
public class LentBooksController {

    private final LentBooksRegister lentBooksRegister;

    /**
     * Makes an instance of the LentBooksController class.
     * @param lentBookRepository the lent book repository.
     * @param lentBooksLogRepository the lent books log repository
     */
    public LentBooksController(LentBookRepository lentBookRepository, LentBooksLogRepository lentBooksLogRepository) {
        lentBooksRegister = new LentBookJPA(lentBookRepository, lentBooksLogRepository);
    }

    /**
     * Gets all the books from the register or gets all the books for one branch.
     * @param branchID the branch ID.
     * @return a list with all the books.
     */
    @GetMapping
    public List<LentBook> getLentBooks(@RequestParam(value = "branchID", required = false) Optional<Long> branchID){
        List<LentBook> returnedLentBooks;
        if (branchID.isEmpty()){
            returnedLentBooks = lentBooksRegister.getAllLentBooks();
        }else {
            returnedLentBooks = lentBooksRegister.getAllBooksWithBranchID(branchID.get());
        }
        return returnedLentBooks;
    }

    @GetMapping("/{branchBookID}")
    public LentBook getLentBook(@PathVariable long branchBookID) throws CouldNotGetLentBookException {
        return lentBooksRegister.getLentBook(branchBookID);
    }

    /**
     * Adds a lent book to the register.
     * @param body the lent book to add.
     * @throws CouldNotAddLentBookException gets thrown if the branch book id is already lent out.
     */
    @PostMapping
    public void addLentBook(@RequestBody String body) throws CouldNotAddLentBookException, JsonProcessingException {
        lentBooksRegister.addLentBook(getLentBook(body));
    }

    @PutMapping
    public void updateLentBook(@RequestBody String body) throws JsonProcessingException, CouldNotGetBranchBookException {
        lentBooksRegister.updateLentBook(getLentBook(body));
    }

    /**
     * Deletes a book from the register.
     * @param branchBookID the branch book to deliver back.
     * @throws CouldNotRemoveLentBookException gets thrown if the lent book could not be found.
     */
    @DeleteMapping
    public void removeLentBook(@RequestParam(value = "branchBookID") long branchBookID) throws CouldNotRemoveLentBookException {
        lentBooksRegister.removeLentBookWithLentBookId(branchBookID, true, LocalDate.now());
    }

    /**
     * Makes a lent book from a json body.
     * @param body the string with the json
     * @return a lent book matching the input.
     * @throws JsonProcessingException gets thrown if the lent book could not be converted.
     */
    private LentBook getLentBook(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, LentBook.class);
    }

    /**
     * Handles a duplicate object exception.
     * @param exception the exception to handle.
     * @return the response according to exception.
     */
    @ExceptionHandler(CouldNotAddLentBookException.class)
    public ResponseEntity<String> handleDuplicateObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.IM_USED).body(exception.getMessage());
    }

    /**
     * Handles the GetObjectException.
     * @param exception the exception to handle.
     * @return the response according to the exception.
     */
    @ExceptionHandler(CouldNotGetLentBookException.class)
    public ResponseEntity<String> handleGetObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles the remove object exception.
     * @param exception the exception to handle.
     * @return the response according the exception
     */
    @ExceptionHandler(CouldNotRemoveLentBookException.class)
    public ResponseEntity<String> handleRemoveObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
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
     * Checks if a string is of a valid format or not.
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
