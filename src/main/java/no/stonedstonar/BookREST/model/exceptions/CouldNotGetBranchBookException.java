package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetBranchBookException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetBranchBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetBranchBookException class.
     *
     * @param message the error message.
     */
    public CouldNotGetBranchBookException(String message) {
        super(message);

    }
}