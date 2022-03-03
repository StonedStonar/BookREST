package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetLentBookException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetLentBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetLentBookException class.
     *
     * @param message the error message.
     */
    public CouldNotGetLentBookException(String message) {
        super(message);

    }
}