package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveAddressException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotRemoveAddressException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveAddressException class.
     *
     * @param message the error message.
     */
    public CouldNotRemoveAddressException(String message) {
        super(message);

    }
}