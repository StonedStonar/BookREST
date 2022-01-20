package no.stonedstonar.BookREST.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveAuthorException represents an exception that gets thrown when an author could not be removed.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotRemoveAuthorException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveAuthorException class.
     *
     * @param message the error message.
     */
    public CouldNotRemoveAuthorException(String message) {
        super(message);

    }
}