package no.stonedstonar.BookREST;

import no.stonedstonar.BookREST.exceptions.CouldNotAddAuthorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@RestController
public class AuthorController {

    private AuthorRegister authorRegister;

    /**
      * Makes an instance of the AuthorController class.
      */
    public AuthorController() throws CouldNotAddAuthorException {
        authorRegister = new AuthorRegister();
        RegisterTestData.addAuthorsToRegister(authorRegister);
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors(){
        return authorRegister.getAuthorList();
    }
    
    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix){
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()){
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }
    
    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }
}
