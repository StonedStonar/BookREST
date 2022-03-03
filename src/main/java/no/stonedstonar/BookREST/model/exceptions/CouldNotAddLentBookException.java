package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotAddLentBookException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddLentBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotAddLentBookException class.
     *
     * @param message the error message.
     */
    public CouldNotAddLentBookException(String message) {
        super(message);

    }
}