package no.stonedstonar.BookREST.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveBookException represents an exception that gets thrown when a book could not be removed.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotRemoveBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveBookException class.
     *
     * @param message the error message.
     */
    public CouldNotRemoveBookException(String message) {
        super(message);

    }
}