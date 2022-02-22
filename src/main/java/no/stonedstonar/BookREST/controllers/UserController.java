package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import no.stonedstonar.BookREST.model.CompanyRegister;
import no.stonedstonar.BookREST.model.JdbcConnection;
import no.stonedstonar.BookREST.model.User;
import no.stonedstonar.BookREST.model.UserRegister;
import no.stonedstonar.BookREST.model.database.UserDatabase;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetUserException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotLoginToUser;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

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

    /**
     * Logs into a user using the email and matching password.
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the user that matches the email and password.
     * @throws CouldNotLoginToUser gets thrown if the password does not match the users set password.
     * @throws CouldNotGetUserException gets thrown if there is no user with that email.
     */
    @GetMapping
    public User loginToUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) throws CouldNotLoginToUser, CouldNotGetUserException {
        checkString(email, "email");
        checkString(password, "password");
        return userRegister.loginToUser(email, password);
    }

    @PostMapping
    public void addUser(@RequestBody User user) throws CouldNotAddUserException {
        userRegister.addUser(user);
    }

    @DeleteMapping("/{userID}")
    public void deleteUser(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) throws CouldNotLoginToUser, CouldNotGetUserException, CouldNotRemoveUserException {
        checkString(password, "password");
        checkString(email, "email");
        User user = userRegister.loginToUser(email, password);

        userRegister.removeUser(user);
    }

    /**
     * Handles the exception that comes when a user's password does not match.
     * @param exception the exception to handle.
     * @return the response according to exception.
     */
    @ExceptionHandler(CouldNotLoginToUser.class)
    public ResponseEntity<String> handleCouldNotLogin(Exception exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    /**
     * Handles the exception that comes when a user could not be located.
     * @param exception the exception to handle.
     * @return the response according to exception
     */
    @ExceptionHandler(CouldNotGetUserException.class)
    public ResponseEntity<String> handleCouldNotGetUser(Exception exception){
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

    @ExceptionHandler(CouldNotAddUserException.class)
    public ResponseEntity<String> handleCouldNotAddUser(Exception exception){
        return ResponseEntity.status(HttpStatus.IM_USED).body(exception.getMessage());
    }


    @ExceptionHandler(CouldNotRemoveUserException.class)
    public ResponseEntity<String> handleRemoveException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
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
