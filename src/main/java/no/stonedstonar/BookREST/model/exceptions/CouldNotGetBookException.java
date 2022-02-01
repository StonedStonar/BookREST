package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetBookException represents an exception that gets thrown when a book could not be found.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetBookException class.
     *
     * @param message the error message.
     */
    public CouldNotGetBookException(String message) {
        super(message);

    }
}