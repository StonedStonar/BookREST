 package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Branch;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddBranchException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBranchException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveBranchException;
import no.stonedstonar.BookREST.model.registers.BranchRegister;
import no.stonedstonar.BookREST.model.repositories.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class BranchJPA implements BranchRegister {

    private BranchRepository branchRepository;

    /**
     * Makes an instance of the LibraryJPA class.
     * @param branchRepository the branch repository
     */
    public BranchJPA(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public void addBranch(Branch branch) throws CouldNotAddBranchException {
        checkIfBranchIsNotNull(branch);
        if (!branchRepository.existsById(branch.getBranchID())){
            branchRepository.save(branch);
        }else {
            throw new CouldNotAddBranchException("The branch with the branch id " + branch.getBranchID() + " is already in the system");
        }
    }

    @Override
    public void removeBranch(Branch branch) throws CouldNotRemoveBranchException {
        checkIfBranchIsNotNull(branch);
        removeBranchWithBranchID(branch.getBranchID());

    }

    @Override
    public void removeBranchWithBranchID(long branchId) throws CouldNotRemoveBranchException {
        if (branchRepository.existsById(branchId)){
            branchRepository.deleteById(branchId);
        }else {
            throw new CouldNotRemoveBranchException("The branch with the id " + branchId + " is not in the system.");
        }
    }

    @Override
    public void updateBranch(Branch branch) throws CouldNotGetBranchException {
        checkIfBranchIsNotNull(branch);
        if (branchRepository.existsById(branch.getBranchID())){
            branchRepository.save(branch);
        }else {
            throw new CouldNotGetBranchException("The branch with id " + branch.getBranchID() + " is not in the system.");
        }

    }

    @Override
    public Branch getBranchByID(long branchID) throws CouldNotGetBranchException {
        checkIfBranchIDIsValid(branchID);
        Optional<Branch> optionalBranch = branchRepository.findById(branchID);
        if (optionalBranch.isEmpty()){
            throw new CouldNotGetBranchException("The branch with id " + branchID + " could not be found in the system.");
        }
        return optionalBranch.get();
    }

    @Override
    public List<Branch> getAllBranches() {
        List<Branch> branches = new LinkedList<>();
        branchRepository.findAll().forEach(branches::add);
        return branches;
    }

    private void checkIfBranchIsNotNull(Branch branch){
        checkIfObjectIsNull(branch, "branch");
    }

    /**
     * Checks if the branch id is above zero.
     * @param branchID the branch id to check.
     */
    private void checkIfBranchIDIsValid(long branchID){
        checkIfLongIsAboveZero(branchID, "branch id");
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
