package no.stonedstonar.BookREST.model.registers;

import no.stonedstonar.BookREST.model.Branch;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddBranchException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBranchBookException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetBranchException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveBranchException;

import java.util.List;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface BranchRegister {

    /**
     * Adds a branch to the system.
     * @param branch the new branch to add.
     * @throws CouldNotAddBranchException gets thrown if the branch could not be added.
     */
    void addBranch(Branch branch) throws CouldNotAddBranchException;

    /**
     * Removes a branch form the system.
     * @param branch the branch to remove.
     * @throws CouldNotRemoveBranchException gets thrown if the branch is not a part of this system.
     */
    void removeBranch(Branch branch) throws CouldNotRemoveBranchException;

    /**
     * Removes a branch with branch id.
     * @param branchId the branch id to remove with.
     * @throws CouldNotRemoveBranchException gets thrown if the system could not delete the branch.
     */
    void removeBranchWithBranchID(long branchId) throws CouldNotRemoveBranchException;

    /**
     * Updates the details of a branch.
     * @param branch the branch to update.
     * @throws CouldNotGetBranchException gets thrown if the branch could not be found and changed.
     */
    void updateBranch(Branch branch) throws CouldNotGetBranchException;

    /**
     * Gets a branch by the branch ID.
     * @param branchID the branch id.
     * @return a branch that has this id.
     * @throws CouldNotGetBranchException gets thrown if the branch could not be found.
     */
    Branch getBranchByID(long branchID) throws CouldNotGetBranchException;

    /**
     * Gets a list with all the branches.
     * @return a list with all the branches.
     */
    List<Branch> getAllBranches();
}
