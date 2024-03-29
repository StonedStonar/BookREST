package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotGetCompanyException represents an exception that gets thrown when a company could not be found.
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotGetCompanyException extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotGetCompanyException class.
     *
     * @param message the error message.
     */
    public CouldNotGetCompanyException(String message) {
        super(message);

    }
}