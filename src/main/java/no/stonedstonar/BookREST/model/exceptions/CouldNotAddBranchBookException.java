package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotAddBranchBookException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddBranchBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotAddBranchBookException class.
     *
     * @param message the error message.
     */
    public CouldNotAddBranchBookException(String message) {
        super(message);

    }
}