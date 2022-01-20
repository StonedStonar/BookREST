package no.stonedstonar.BookREST.exceptions;

import java.io.Serializable;

/**
 * CouldNotAddBookException represents an exception that gets thrown when a book could not be added.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotAddBookException class.
     *
     * @param message the error message.
     */
    public CouldNotAddBookException(String message) {
        super(message);

    }
}