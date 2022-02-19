package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * GetObjectException represents an exception that gets thrown when an object could not be found.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class GetObjectException extends Exception implements Serializable {

    /**
     * Makes an instance of the GetObjectException class.
     *
     * @param message the error message.
     */
    public GetObjectException(String message) {
        super(message);

    }
}