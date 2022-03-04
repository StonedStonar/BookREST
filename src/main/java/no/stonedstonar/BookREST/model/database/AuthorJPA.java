package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;
import no.stonedstonar.BookREST.model.registers.AuthorRegister;
import no.stonedstonar.BookREST.model.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class AuthorJPA implements AuthorRegister {

    private AuthorRepository authorRepository;

    /**
     * Makes an instance of the AuthorJPA class.
     */
    public AuthorJPA(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void addAuthor(Author author) throws CouldNotAddAuthorException {
        checkIfAuthorIsRightFormat(author);
        if (!authorRepository.existsById(author.getAuthorID())){
            authorRepository.save(author);
        }else {
            throw new CouldNotAddAuthorException("The author with the ID " + author.getAuthorID() + " is already in the system.");
        }
    }

    @Override
    public void removeAuthor(Author author) throws CouldNotRemoveAuthorException {
        checkIfAuthorIsRightFormat(author);
        removeAuthorWithId(author.getAuthorID());
    }

    @Override
    public void removeAuthorWithId(long authorID) throws CouldNotRemoveAuthorException {
        checkIfAuthorIdIsAboveZero(authorID);
        if (authorRepository.existsById(authorID)){
            authorRepository.deleteById(authorID);
        }else {
            throw new CouldNotRemoveAuthorException("The author with the id " + authorID + " is not in the system.");
        }
    }

    @Override
    public void updateAuthor(Author author) throws CouldNotGetAuthorException {
        checkIfAuthorIsRightFormat(author);
        if (authorRepository.existsById(author.getAuthorID())){
            authorRepository.save(author);
        }else {
            throw new CouldNotGetAuthorException("The author with the ID " + author.getAuthorID() + " could not be found in the system.");
        }
    }

    @Override
    public Author getAuthorById(long authorID) throws CouldNotGetAuthorException {
        checkIfAuthorIdIsAboveZero(authorID);
        Optional<Author> optionalAuthor = authorRepository.findById(authorID);
        if (optionalAuthor.isEmpty()){
            throw new CouldNotGetAuthorException("The author with authorID " + authorID + " could not be found in the system.");
        }
        return optionalAuthor.get();
    }

    @Override
    public List<Author> getAuthorList() {
        List<Author> authors = new LinkedList<>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }

    /**
     * Checks if the author is not null.
     * @param author the author to check
     */
    private void checkIfAuthorIsRightFormat(Author author){
        checkIfObjectIsNull(author, "author");
    }

    /**
     * Checks if the author id is above zero.
     * @param authorID the author id to check.
     */
    private void checkIfAuthorIdIsAboveZero(long authorID){
        checkIfLongIsAboveZero(authorID, "authorID");
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
     * Checks if a string is of a valid format or not.
     *
     * @param stringToCheck the string you want to check.
     * @param errorPrefix   the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix) {
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()) {
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
