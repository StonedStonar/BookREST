package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotAddBranchException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotAddBranchException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotAddBranchException class.
     *
     * @param message the error message.
     */
    public CouldNotAddBranchException(String message) {
        super(message);

    }
}