package no.stonedstonar.BookREST.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class ReturnedBookController {

    /**
     * Makes an instance of the ReturnedBookController class.
     */
    public ReturnedBookController() {

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
