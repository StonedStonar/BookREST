package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.stonedstonar.BookREST.model.CompanyRegister;
import no.stonedstonar.BookREST.model.JdbcConnection;
import no.stonedstonar.BookREST.model.User;
import no.stonedstonar.BookREST.model.UserRegister;
import no.stonedstonar.BookREST.model.database.UserDatabase;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private final JdbcConnection jdbcConnection;

    private UserRegister userRegister;

    private ObjectMapper objectMapper;

    /**
      * Makes an instance of the UserController class.
      */
    public UserController(JdbcConnection jdbcConnection){
        this.jdbcConnection = jdbcConnection;
        try {
            userRegister = new UserDatabase(jdbcConnection.connect());
        }catch (SQLException exception){
            System.err.println("Could not connect the database.");
        }
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public User loginToUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password){
        checkString();

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
