package no.stonedstonar.BookREST.model.normalRegisters;

import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.AuthorRegister;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents an author register.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class NormalAuthorRegister implements no.stonedstonar.BookREST.model.AuthorRegister {

    private List<Author> authorList;

    /**
      * Makes an instance of the authorRegister class.
      */
    public NormalAuthorRegister(){
        authorList = new ArrayList<>();
    }

    @Override
    public void addAuthor(Author author) throws CouldNotAddAuthorException {
        checkAuthor(author);
        if (!authorList.contains(author)){
            authorList.add(author);
        }else {
            throw new CouldNotAddAuthorException("The author is already in the register.");
        }
    }

    @Override
    public void removeAuthor(Author author) throws CouldNotRemoveAuthorException {
        checkAuthor(author);
        if (!authorList.remove(author)){
            throw new CouldNotRemoveAuthorException("The author could not be found in this register.");
        }
    }

    @Override
    public Author getAuthorById(long authorID) throws CouldNotGetAuthorException {
        checkID(authorID);
        Optional<Author> optionalAuthor = authorList.stream().filter(author -> author.getID() == authorID).findFirst();
        if (optionalAuthor.isPresent()){
            return optionalAuthor.get();
        }else {
            throw new CouldNotGetAuthorException("The author with the ID " + authorID + " could not be found in the register.");
        }
    }



    /**
     * Checks if the author is null.
     * @param authorToCheck the author to check.
     */
    private void checkAuthor(Author authorToCheck){
        checkIfObjectIsNull(authorToCheck, "author");
    }

    /**
     * Checks if the ID is above zero.
     * @param ID the ID to check.
     */
    private void checkID(long ID){
        checkIfLongIsAboveZero(ID, "ID");
    }

    /**
     * Checks if the input long is above zero.
     * @param number the number to check.
     * @param prefix the prefix the error should have.
     */
    private void checkIfLongIsAboveZero(long number, String prefix){
        if (number <= 0){
            throw new IllegalArgumentException("The " + prefix + " must be above zero.");
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

    /**
     * Gets the list of all the authors.
     * @return a list with all the authors.
     */
    public List<Author> getAuthorList(){
        return authorList;
    }
}
