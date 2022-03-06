package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.stonedstonar.BookREST.model.BranchBook;
import no.stonedstonar.BookREST.model.database.BranchBookJPA;
import no.stonedstonar.BookREST.model.exceptions.*;
import no.stonedstonar.BookREST.model.registers.BranchBookRegister;
import no.stonedstonar.BookREST.model.repositories.BranchBookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@RestController
@CrossOrigin
@RequestMapping("/branchBooks")
public class BranchBookController {

    private final BranchBookRegister branchBookRegister;

    /**
     * Makes an instance of the BranchBookController class.
     * @param branchBookRepository the branch book repository.
     */
    public BranchBookController(BranchBookRepository branchBookRepository) {
        branchBookRegister = new BranchBookJPA(branchBookRepository);
    }

    /**
     * Gets all the branch books that is in the defined branch.
     * @param branchId the id of the branch.
     * @return a list with all the branch books in that branch.
     */
    @GetMapping
    public List<BranchBook> getBranchBooks(@RequestParam(value = "branchID", required = false) Optional<Long> branchId, @RequestParam(value = "isbn", required = false) Optional<Long> isbn) {
        List<BranchBook> branchBooks;
        if (branchId.isEmpty() && isbn.isEmpty()){
            branchBooks = branchBookRegister.getAllBranchBooks();
        }else if (branchId.isPresent()){
            branchBooks = branchBookRegister.getAllBranchBooksForBranchWithID(branchId.get());
        }else {
            branchBooks = branchBookRegister.getAllBranchBooksWithISBN(isbn.get());
        }
        return branchBooks;
    }

    /**
     * Get sa branch book based on the ID.
     * @param id the id of the branch book.
     * @return the branch book that has this id.
     * @throws CouldNotGetBranchBookException gets thrown if the branch book does not exsist.
     */
    @GetMapping("/{id}")
    public BranchBook getBranchBookWithId(@PathVariable long id) throws CouldNotGetBranchBookException {
        return branchBookRegister.getBranchBook(id);
    }

    /**
     * Adds a new branch book to the register.
     * @param body the branch book to add.
     * @throws CouldNotAddBranchBookException gets thrown when the object could not be added.
     * @throws JsonProcessingException gets thrown if the format is invalid.
     */
    @PostMapping
    public void addBranchBook(@RequestBody String body) throws CouldNotAddBranchBookException, JsonProcessingException {
        branchBookRegister.addBranchBook(getBranchBook(body));
    }

    /**
     * Updates a book and its details.
     * @param body the branch book as a json.
     * @throws CouldNotGetBranchBookException gets thrown if the branch book could not be located.
     * @throws JsonProcessingException gets thrown if the format is invalid.
     */
    @PutMapping
    public void updateBranchBook(@RequestBody String body) throws JsonProcessingException, CouldNotGetBranchBookException {
        branchBookRegister.updateBranchBook(getBranchBook(body));
    }

    /**
     * Removes a branch book from the system.
     * @param branchBookID the branch book id to remove.
     * @throws CouldNotRemoveBranchBookException gets thrown if the book could not be removed.
     */
    @DeleteMapping("/{branchBookID}")
    public void removeBranchBook(@PathVariable long branchBookID) throws CouldNotRemoveBranchBookException {
        branchBookRegister.removeBranchBookWithId(branchBookID);
    }

    /**
     * Gets a branch book from a json body.
     * @param body the json body.
     * @return the branch book that was in the json.
     * @throws JsonProcessingException gets thrown if the format is invalid.
     */
    private BranchBook getBranchBook(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, BranchBook.class);
    }

    /**
     * Handles a duplicate object exception.
     * @param exception the exception to handle.
     * @return the response according to exception.
     */
    @ExceptionHandler(CouldNotAddBranchBookException.class)
    public ResponseEntity<String> handleDuplicateObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.IM_USED).body(exception.getMessage());
    }

    /**
     * Handles the GetObjectException.
     * @param exception the exception to handle.
     * @return the response according to the exception.
     */
    @ExceptionHandler(CouldNotGetBranchBookException.class)
    public ResponseEntity<String> handleGetObjectException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles the remove object exception.
     * @param exception the exception to handle.
     * @return the response according the exception
     */
    @ExceptionHandler(CouldNotRemoveBranchBookException.class)
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
