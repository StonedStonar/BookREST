package no.stonedstonar.BookREST.model.exceptions;

import java.io.Serializable;

/**
 * CouldNotLoginToUser represents an exception that gets thrown when
 *
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class CouldNotLoginToUser extends Exception implements Serializable {

    /**
     * Makes an instance of the CouldNotLoginToUser class.
     *
     * @param message the error message.
     */
    public CouldNotLoginToUser(String message) {
        super(message);

    }
}