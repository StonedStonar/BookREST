package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.stonedstonar.BookREST.model.Branch;
import no.stonedstonar.BookREST.JdbcConnection;
import no.stonedstonar.BookREST.model.database.BranchJPA;
import no.stonedstonar.BookREST.model.exceptions.*;
import no.stonedstonar.BookREST.model.registers.BranchRegister;
import no.stonedstonar.BookREST.model.repositories.BranchRepository;
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

    private final BranchRegister branchRegister;

    /**
     * Makes an instance of the LibraryController class.
     * @param branchRepository the branch repository.
     */
    public LibraryController(BranchRepository branchRepository) {
        branchRegister = new BranchJPA(branchRepository);
    }

    /**
     * Gets all the branches.
     * @return a list with branches.
     * @throws SQLException gets thrown if the connection to the database gets closed.
     */
    @GetMapping
    public List<Branch> getBranches() throws SQLException {
        return branchRegister.getAllBranches();
    }

    /**
     * Adds a branch to the system.
     * @param branch the branch to add.
     * @throws CouldNotAddBranchException gets thrown if the branch with that ID is already in the system.
     */
    @PostMapping
    public void addBranch(@RequestBody Branch branch) throws SQLException, CouldNotAddBranchException {
        branchRegister.addBranch(branch);
    }

    /**
     * Deletes a branch from the system.
     * @param branchID the branch to remove.
     * @throws RemoveObjectException gets thrown if the object could not be removed.
     */
    @DeleteMapping
    public void deleteBranch(@RequestParam(value = "branchID") long branchID) throws CouldNotRemoveBranchException {
        branchRegister.removeBranchWithBranchID(branchID);
    }

    /**
     * Handles a duplicate object exception.
     * @param exception the exception to handle.
     * @return the response according to exception.
     */
    @ExceptionHandler(CouldNotAddBranchException.class)
    public ResponseEntity<String> handleDuplicateObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.IM_USED).body(exception.getMessage());
    }

    /**
     * Handles the GetObjectException.
     * @param exception the exception to handle.
     * @return the response according to the exception.
     */
    @ExceptionHandler(CouldNotGetBranchException.class)
    public ResponseEntity<String> handleGetObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles the remove object exception.
     * @param exception the exception to handle.
     * @return the response according the exception
     */
    @ExceptionHandler(CouldNotRemoveBranchException.class)
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
