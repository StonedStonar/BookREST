package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.stonedstonar.BookREST.model.JdbcConnection;
import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.LentBooksRegister;
import no.stonedstonar.BookREST.model.database.LentBookDatabase;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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

    private LentBooksRegister lentBooksRegister;

    private final JdbcConnection jdbcConnection;

    /**
     * Makes an instance of the LentBooksController class.
     */
    public LentBooksController(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        try {
            lentBooksRegister = new LentBookDatabase(jdbcConnection.connect());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets all the books from the register or gets all the books for one branch.
     * @param branchID the branch ID.
     * @return a list with all the books.
     */
    @GetMapping
    public List<LentBook> getLentBooks(@RequestParam(value = "branchID", required = false) Optional<Long> branchID) throws SQLException {
        List<LentBook> returnedLentBooks;
        if (branchID.isEmpty()){
            returnedLentBooks = lentBooksRegister.getAllDueBooks();
        }else {
            returnedLentBooks = lentBooksRegister.getAllBooksWithBranchID(branchID.get());
        }
        return returnedLentBooks;
    }

    /**
     * Adds a lent book to the register.
     * @param lentBook the lent book to add.
     * @throws DuplicateObjectException gets thrown if the branch book id is already lent out.
     */
    @PostMapping
    public void addLentBook(@RequestBody LentBook lentBook) throws DuplicateObjectException {
        lentBooksRegister.addLentBook(lentBook);
    }

    /**
     * Deletes a book from the register.
     * @param branchBookID the branch book to deliver back.
     * @throws RemoveObjectException gets thrown if the lent book could not be found.
     */
    @DeleteMapping
    public void removeLentBook(@RequestParam(value = "branchBookID") long branchBookID) throws RemoveObjectException {
        lentBooksRegister.removeLentBookByBranchBookID(branchBookID);
    }

    /**
     * Handles a duplicate object exception.
     * @param exception the exception to handle.
     * @return the response according to exception.
     */
    @ExceptionHandler(DuplicateObjectException.class)
    public ResponseEntity<String> handleDuplicateObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.IM_USED).body(exception.getMessage());
    }

    /**
     * Handles the GetObjectException.
     * @param exception the exception to handle.
     * @return the response according to the exception.
     */
    @ExceptionHandler(GetObjectException.class)
    public ResponseEntity<String> handleGetObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles the remove object exception.
     * @param exception the exception to handle.
     * @return the response according the exception
     */
    @ExceptionHandler(RemoveObjectException.class)
    public ResponseEntity<String> handleRemoveObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles a sql exception.
     * @param exception the exception to handle.
     * @return a response based on the exception.
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not connect to mysql server.");
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
