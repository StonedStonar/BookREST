package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotAddAddressException represents an exception that gets thrown when
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddAddressException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotAddAddressException class.
     * @param message the error message.
     */
    public CouldNotAddAddressException(String message) {
        super(message);
    }
}