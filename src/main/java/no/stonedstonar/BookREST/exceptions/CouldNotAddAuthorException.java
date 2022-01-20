package no.stonedstonar.BookREST.exceptions;


import java.io.Serializable;

/**
 * CouldNotAddAuthorException represents an exception that gets thrown when a author could not be added.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddAuthorException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotAddAuthorException class.
     *
     * @param message the error message.
     */
    public CouldNotAddAuthorException(String message) {
        super(message);

    }
}