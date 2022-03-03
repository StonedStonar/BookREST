package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotRemoveBranchException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotRemoveBranchException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotRemoveBranchException class.
     *
     * @param message the error message.
     */
    public CouldNotRemoveBranchException(String message) {
        super(message);

    }
}