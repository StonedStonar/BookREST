package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.stonedstonar.BookREST.RegisterTestData;
import no.stonedstonar.BookREST.model.BranchBook;
import no.stonedstonar.BookREST.model.BranchBookRegister;
import no.stonedstonar.BookREST.JdbcConnection;
import no.stonedstonar.BookREST.model.database.BranchBookDatabase;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;
import no.stonedstonar.BookREST.model.exceptions.GetObjectException;
import no.stonedstonar.BookREST.model.exceptions.RemoveObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@RestController
@CrossOrigin
@RequestMapping("/branchBooks")
public class BranchBookController {

    private final JdbcConnection jdbcConnection;

    private BranchBookRegister branchBookRegister;

    /**
     * Makes an instance of the BranchBookController class.
     */
    public BranchBookController(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        try {
            branchBookRegister = new BranchBookDatabase(jdbcConnection.connect());
        } catch (SQLException exception) {
            System.err.println("Could not connect the branch book database.");
        }
    }

    /**
     * Gets all the branch books that is in the defined branch.
     * @param branchId the id of the branch.
     * @return a list with all the branch books in that branch.
     */
    @GetMapping
    public List<BranchBook> getBranchBooks(@RequestParam(value = "branchID") long branchId) throws SQLException {
        return branchBookRegister.getAllBranchBooksForBranchWithID(branchId);
    }

    /**
     * Adds a new branch book to the register.
     * @param branchBook the branch book to add.
     * @throws DuplicateObjectException gets thrown when the object could not be added.
     */
    @PostMapping
    public void addBranchBook(@RequestBody BranchBook branchBook) throws DuplicateObjectException, SQLException {
        branchBookRegister.addBranchBook(branchBook);
    }

    /**
     * Removes a branch book from the system.
     * @param branchBookID the branch book id to remove.
     * @throws RemoveObjectException gets thrown if the book could not be removed.
     * @throws GetObjectException gets thrown if the book could not be found.
     */
    @DeleteMapping("/{branchBookID}")
    public void removeBranchBook(@PathVariable long branchBookID) throws RemoveObjectException, GetObjectException, SQLException {
        BranchBook branchBook = branchBookRegister.getBranchBook(branchBookID);
        branchBookRegister.removeBranchBook(branchBook);
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
     * Handles a invalid JSON format request.
     * @return the response according to exception.
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonFormatFault(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format on JSON file is invalid.");
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
