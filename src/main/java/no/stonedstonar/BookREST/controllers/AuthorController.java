package no.stonedstonar.BookREST.controllers;

import no.stonedstonar.BookREST.model.AuthorRegister;
import no.stonedstonar.BookREST.model.JdbcConnection;
import no.stonedstonar.BookREST.model.database.AuthorDatabase;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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

    private final JdbcConnection jdbcConnection;

    private AuthorRegister authorRegister;

    /**
      * Makes an instance of the AuthorController class.
     * @param jdbcConnection
     */
    public AuthorController(JdbcConnection jdbcConnection) throws CouldNotAddAuthorException {
        this.jdbcConnection = jdbcConnection;
        try {
            authorRegister = new AuthorDatabase(this.jdbcConnection.connect());

        }catch (SQLException exception){
            System.err.println("Could not connect the database.");
        }

    }

    @GetMapping
    public List<Author> getAuthors(){
        return authorRegister.getAuthorList();
    }

    @GetMapping("/{id}")
    public Author getAuthorWithID(@PathVariable long id) throws CouldNotGetAuthorException {
        return authorRegister.getAuthorById(id);
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
