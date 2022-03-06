package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.BranchBook;
import no.stonedstonar.BookREST.model.exceptions.*;
import no.stonedstonar.BookREST.model.registers.BranchBookRegister;
import no.stonedstonar.BookREST.model.repositories.BranchBookRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class BranchBookJPA implements BranchBookRegister {

    private BranchBookRepository branchBookRepository;

    /**
     * Makes an instance of the BranchBookJPA class.
     * @param branchBookRepository the branch book repository.
     */
    public BranchBookJPA(BranchBookRepository branchBookRepository) {
        this.branchBookRepository = branchBookRepository;
    }

    @Override
    public void addBranchBook(BranchBook branchBook) throws CouldNotAddBranchBookException {
        checkIfBranchBookIsValid(branchBook);
        if (!branchBookRepository.existsById(branchBook.getBranchBookID())){
            branchBookRepository.save(branchBook);
        }else {
            throw new CouldNotAddBranchBookException("The branch book with id " + branchBook.getBranchBookID() + " is already in the system.");
        }

    }

    @Override
    public void removeBranchBook(BranchBook branchBook) throws CouldNotRemoveBranchBookException {
        checkIfBranchBookIsValid(branchBook);
        removeBranchBookWithId(branchBook.getBranchBookID());
    }

    @Override
    public void removeBranchBookWithId(long branchBookId) throws CouldNotRemoveBranchBookException {
        checkIfBranchBookIdIsAboveZero(branchBookId);
        if (branchBookRepository.existsById(branchBookId)){
            branchBookRepository.deleteById(branchBookId);
        }else {
            throw new CouldNotRemoveBranchBookException("The branch book with id " + branchBookId + "could not be removed since its not in the system.");
        }
    }

    @Override
    public void updateBranchBook(BranchBook branchBook) throws CouldNotGetBranchBookException {
        checkIfBranchBookIsValid(branchBook);
        if (branchBookRepository.existsById(branchBook.getBranchBookID())){
            branchBookRepository.save(branchBook);
        }else {
            throw new CouldNotGetBranchBookException("The branch book with id " + branchBook.getBranchBookID() + " could not be modified since its not in the system.");
        }

    }

    @Override
    public List<BranchBook> getAllBranchBooksWithISBN(long isbn) {
        List<BranchBook> branchBooks = branchBookRepository.getAllBooksWithISBNThatIsFree(isbn);
        branchBookRepository.getAllBooksWithISBNThatIsLent(isbn).forEach(branchBook -> {
            branchBook.setTaken(true);
            branchBooks.add(branchBook);
        });
        return branchBooks;
    }

    @Override
    public BranchBook getBranchBook(long branchBookID) throws CouldNotGetBranchBookException {
        checkIfBranchBookIdIsAboveZero(branchBookID);
        Optional<BranchBook> optionalBranchBook = branchBookRepository.findById(branchBookID);
        if (optionalBranchBook.isEmpty()){
            throw new CouldNotGetBranchBookException("The branch book with id " + branchBookID + " could not be found in the register.");
        }
        BranchBook branchBook = optionalBranchBook.get();
        branchBook.setTaken(branchBookRepository.checkIfBranchBookIsLent(branchBookID) > 1);
        return branchBook;
    }

    @Override
    public List<BranchBook> getAllBranchBooksForBranchWithID(long branchID) {
        List<BranchBook> branchBooks = branchBookRepository.getAllBranchBooksThatIsLent(branchID);
        branchBookRepository.getAllBranchBooksThatIsLent(branchID).forEach(branchBook -> {
            branchBook.setTaken(true);
            branchBooks.add(branchBook);
        });
        return branchBooks;
    }

    @Override
    public List<BranchBook> getAllBranchBooks() {
        List<BranchBook> branchBooks = branchBookRepository.getAllBranchBooksThatIsNotLent();
        branchBookRepository.getAllBranchBooksThatIsLent().forEach(branchBook -> {
            branchBook.setTaken(true);
            branchBooks.add(branchBook);
        });
        return branchBooks;
    }

    @Override
    public boolean checkIfBranchBooksRegisterHasBooks() {
        return branchBookRepository.count() > 0;
    }

    /**
     * Checks if the branch book is not null.
     * @param branchBook the branch to check
     */
    private void checkIfBranchBookIsValid(BranchBook branchBook){
        checkIfObjectIsNull(branchBook, "branch book");
    }


    /**
     * Checks if the branchBookID is above zero.
     * @param branchBookID the branchBookID to check.
     */
    private void checkIfBranchBookIdIsAboveZero(long branchBookID){
        checkIfLongIsAboveZero(branchBookID, "branch book id");
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
