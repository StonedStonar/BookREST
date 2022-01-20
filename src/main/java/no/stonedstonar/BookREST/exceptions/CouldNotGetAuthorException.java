package no.stonedstonar.BookREST.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetAuthorException represents an exception that gets thrown when an author could not be found.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetAuthorException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetAuthorException class.
     *
     * @param message the error message.
     */
    public CouldNotGetAuthorException(String message) {
        super(message);

    }
}