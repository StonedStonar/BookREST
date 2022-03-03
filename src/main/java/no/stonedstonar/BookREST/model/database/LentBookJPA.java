package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.LentBook;
import no.stonedstonar.BookREST.model.ReturnedLentBook;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddLentBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetLentBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveLentBookException;
import no.stonedstonar.BookREST.model.registers.LentBooksRegister;
import no.stonedstonar.BookREST.model.repositories.LentBookRepository;
import no.stonedstonar.BookREST.model.repositories.LentBooksLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class LentBookJPA implements LentBooksRegister {

    @Autowired
    private LentBookRepository lentBookRepository;

    @Autowired
    private LentBooksLogRepository lentBooksLogRepository;

    /**
     * Makes an instance of the LentBookJPA class.
     * @param lentBookRepository the lent book repository.
     * @param lentBooksLogRepository the lent books log repository
     */
    public LentBookJPA(LentBookRepository lentBookRepository, LentBooksLogRepository lentBooksLogRepository) {
        this.lentBookRepository = lentBookRepository;
        this.lentBooksLogRepository = lentBooksLogRepository;
    }

    @Override
    public void addLentBook(LentBook lentBook) throws CouldNotAddLentBookException {
        checkIfLentBookIsNotNull(lentBook);
        if (!lentBookRepository.existsById(lentBook.getLentBookId())){
            lentBookRepository.save(lentBook);
        }else {
            throw new CouldNotAddLentBookException("The lent book with id " + lentBook.getLentBookId() + " is already in the system");
        }
    }

    @Override
    public void removeLentBook(LentBook lentBook, boolean returned, LocalDate date) throws CouldNotRemoveLentBookException {
        checkIfLentBookIsNotNull(lentBook);
        if (lentBookRepository.existsById(lentBook.getLentBookId())){
            lentBookRepository.delete(lentBook);
            if (returned){
                try{
                    checkIfObjectIsNull(date, "returned date");
                    lentBooksLogRepository.save(new ReturnedLentBook(lentBook, date));
                }catch (IllegalArgumentException exception){
                    lentBookRepository.save(lentBook);
                    throw exception;
                }
            }
        }else {
            throw new CouldNotRemoveLentBookException("The lent book with id " + lentBook.getLentBookId() + " is not in the system.");
        }
    }

    @Override
    public void removeLentBookWithLentBookId(long lentBookID) throws CouldNotRemoveLentBookException {
        checkIfIdIsNotBelowZero(lentBookID);

    }


    @Override
    public LentBook getLentBook(long branchBookID) throws CouldNotGetLentBookException {
        return null;
    }


    @Override
    public List<LentBook> getAllDueBooks() {
        return null;
    }

    @Override
    public List<LentBook> getAllLentBooks() {
        List<LentBook> lentBooks = new LinkedList<>();
        lentBookRepository.findAll().forEach(lentBooks::add);
        return lentBooks;
    }

    @Override
    public List<LentBook> getAllDueBooksForBranch(long branchID, int amountOfDays) {
        return null;
    }

    @Override
    public List<LentBook> getAllBooksWithBranchID(long branchID) {
        return null;
    }

    @Override
    public List<LentBook> getAllDueBooksForUser(long userID) {
        return null;
    }

    /**
     * Checks if the lent book is not null.
     * @param lentBook the lent book to check.
     */
    private void checkIfLentBookIsNotNull(LentBook lentBook){
        checkIfObjectIsNull(lentBook, "lent book");
    }

    /**
     * Checks if the id is below zero.
     * @param id the id to check.
     */
    private void checkIfIdIsNotBelowZero(long id){
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
