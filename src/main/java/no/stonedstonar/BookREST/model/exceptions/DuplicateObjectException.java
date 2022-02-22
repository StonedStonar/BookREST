package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * DuplicateObjectException represents an exception that gets thrown when an object is already added in a register.
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class DuplicateObjectException extends Exception implements Serializable {

    /**
     * Makes an instance of the DuplicateObjectException class.
     * @param message the error message.
     */
    public DuplicateObjectException(String message) {
        super(message);
    }
}