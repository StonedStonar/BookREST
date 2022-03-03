package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveBranchBookException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotRemoveBranchBookException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveBranchBookException class.
     *
     * @param message the error message.
     */
    public CouldNotRemoveBranchBookException(String message) {
        super(message);

    }
}