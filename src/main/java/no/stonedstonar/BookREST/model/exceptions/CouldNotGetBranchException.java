package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetBranchException represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetBranchException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetBranchException class.
     *
     * @param message the error message.
     */
    public CouldNotGetBranchException(String message) {
        super(message);

    }
}