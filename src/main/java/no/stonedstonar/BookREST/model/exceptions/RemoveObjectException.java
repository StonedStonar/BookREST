package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * RemoveObjectException represents an exception that gets thrown when an object could not be removed.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class RemoveObjectException extends Exception implements Serializable {

    /**
     * Makes an instance of the RemoveObjectException class.
     *
     * @param message the error message.
     */
    public RemoveObjectException(String message) {
        super(message);

    }
}