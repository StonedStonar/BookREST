package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.ReturnedLentBook;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddLentBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetLentBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveLentBookException;
import no.stonedstonar.BookREST.model.registers.LentBooksLog;
import no.stonedstonar.BookREST.model.repositories.LentBooksLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class LentBooksLogJPA implements LentBooksLog {

    private LentBooksLogRepository lentBooksLogRepository;

    /**
     * Makes an instance of the LentBooksLogJPA class.
     * @param lentBooksLogRepository the lent books log repository.
     */
    public LentBooksLogJPA(LentBooksLogRepository lentBooksLogRepository) {
        this.lentBooksLogRepository = lentBooksLogRepository;

    }

    @Override
    public void addReturnedLentBook(ReturnedLentBook returnedLentBook) throws CouldNotAddLentBookException {
        checkIfReturnedBookIsValid(returnedLentBook);
        if (!lentBooksLogRepository.existsById(returnedLentBook.getLentBookId())){
            lentBooksLogRepository.save(returnedLentBook);
        }else {
            throw new CouldNotAddLentBookException("The returned book with id " + returnedLentBook.getLentBookId() + " is already in the system.");
        }

    }

    @Override
    public void removeLentBookWithLentBookID(long lentBookId) throws CouldNotRemoveLentBookException {
        checkIfIdIsValid(lentBookId);
        if (!lentBooksLogRepository.existsById(lentBookId)){
            lentBooksLogRepository.deleteById(lentBookId);
        }else {
            throw new CouldNotRemoveLentBookException("The lent book with id " + lentBookId + " could not be located.");
        }
    }

    @Override
    public void updateReturnedLentBook(ReturnedLentBook returnedLentBook) throws CouldNotGetLentBookException {
        checkIfLentBookIsNotNull(returnedLentBook);
        if (lentBooksLogRepository.existsById(returnedLentBook.getLentBookId())){
            lentBooksLogRepository.save(returnedLentBook);
        }else {
            throw new CouldNotGetLentBookException("The lent book with id " + returnedLentBook.getLentBookId() + " could not be found.");
        }
    }

    @Override
    public List<ReturnedLentBook> getAllTheTimesBookHasBeenLent(long branchBookID) {
        return lentBooksLogRepository.findAllTheTimesBookHasBeenLentOut(branchBookID);
    }

    @Override
    public List<ReturnedLentBook> getAllReturnedBooks() {
        List<ReturnedLentBook> returnedLentBooks = new LinkedList<>();
        lentBooksLogRepository.findAll().forEach(returnedLentBooks::add);
        return returnedLentBooks;
    }

    /**
     * Checks if the returned lent book is not null.
     * @param returnedLentBook the returned lent book.
     */
    private void checkIfReturnedBookIsValid(ReturnedLentBook returnedLentBook){
        checkIfObjectIsNull(returnedLentBook, "returned lent book");
    }

    /**
     * Checks if the lent book is not null.
     * @param lentBook the lent book to check.
     */
    private void checkIfLentBookIsNotNull(LentBook lentBook){
        checkIfObjectIsNull(lentBook, "lent book");
    }

    /**
     * Checks if the id is valid.
     * @param id the id to check.
     */
    private void checkIfIdIsValid(long id){
        checkIfLongIsAboveZero(id, "id");
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
