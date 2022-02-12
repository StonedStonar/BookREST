package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetUserException represents an exception that gets thrown when a user could not be found.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetUserException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetUserException class.
     *
     * @param message the error message.
     */
    public CouldNotGetUserException(String message) {
        super(message);

    }
}