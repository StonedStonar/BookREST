package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveLentBookException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotRemoveLentBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveLentBookException class.
     *
     * @param message the error message.
     */
    public CouldNotRemoveLentBookException(String message) {
        super(message);

    }
}