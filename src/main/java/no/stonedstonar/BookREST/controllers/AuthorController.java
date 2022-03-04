package no.stonedstonar.BookREST.controllers;

import no.stonedstonar.BookREST.model.database.AuthorJPA;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.registers.AuthorRegister;
import no.stonedstonar.BookREST.JdbcConnection;
import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@RestController
@CrossOrigin
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRegister authorRegister;

    /**
      * Makes an instance of the AuthorController class.
     */
    public AuthorController(AuthorJPA authorJPA) {
        this.authorRegister = authorJPA;
    }

    @GetMapping
    public List<Author> getAuthors(@RequestParam(value = "authorID", required = false) List<Long> authorIDs) throws SQLException, CouldNotGetAuthorException {
        List<Author> authors;
        if (authorIDs == null || authorIDs.isEmpty()){
            authors =  authorRegister.getAuthorList();
        }else {
            System.out.println(authorIDs.size());
            authors = new LinkedList<>();
            for (long authorID : authorIDs){
                authors.add(authorRegister.getAuthorById(authorID));
            }
        }
        return authors;
    }

    @GetMapping("/{id}")
    public Author getAuthorWithID(@PathVariable long id) throws CouldNotGetAuthorException {
        return authorRegister.getAuthorById(id);
    }

    @PostMapping
    public void addAuthor(@RequestBody Author author) throws CouldNotAddAuthorException {
        authorRegister.addAuthor(author);
    }

    @PutMapping
    public void changeAuthor(@RequestBody Author author) throws CouldNotGetAuthorException {
        authorRegister.updateAuthor(author);
    }


    @ExceptionHandler(CouldNotGetAuthorException.class)
    public ResponseEntity<String> handleCouldNotGetException(Exception exception){
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
