package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.stonedstonar.BookREST.model.Branch;
import no.stonedstonar.BookREST.JdbcConnection;
import no.stonedstonar.BookREST.model.database.LibraryDatabase;
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
@RequestMapping("/library")
public class LibraryController {

    private final JdbcConnection jdbcConnection;

    private LibraryDatabase libraryDatabase;

    /**
     * Makes an instance of the LibraryController class.
     */
    public LibraryController(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        try {
            libraryDatabase = new LibraryDatabase(jdbcConnection.connect());
        } catch (SQLException exception) {
            System.err.println("Could not connect to library database.");
        }
    }

    /**
     * Gets all the branches.
     * @return a list with branches.
     * @throws SQLException gets thrown if the connection to the database gets closed.
     */
    @GetMapping
    public List<Branch> getBranches() throws SQLException {
        return libraryDatabase.getAllBranches();
    }

    /**
     * Adds a branch to the system.
     * @param branch the branch to add.
     * @throws DuplicateObjectException gets thrown if the branch with that ID is already in the system.
     */
    @PostMapping
    public void addBranch(@RequestBody Branch branch) throws DuplicateObjectException, SQLException {
        libraryDatabase.addNewBranch(branch);
    }

    /**
     * Deletes a branch from the system.
     * @param branchID the branch to remove.
     * @throws RemoveObjectException gets thrown if the object could not be removed.
     */
    @DeleteMapping
    public void deleteBranch(@RequestParam(value = "branchID") long branchID) throws RemoveObjectException, SQLException {
        libraryDatabase.removeBranchWithID(branchID);
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
     * Handles SQL exception that happens when the connection to the database is terminated.
     * @param exception the exception.
     * @return the response according to the exception.
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not connect to the SQL server.");
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
