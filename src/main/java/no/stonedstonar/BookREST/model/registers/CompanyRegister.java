package no.stonedstonar.BookREST.model.registers;

import no.stonedstonar.BookREST.model.Company;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveCompanyException;

import java.sql.SQLException;
import java.util.List;

/**
 * Represents what a basic company register should have of methods.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public interface CompanyRegister {

    /**
     * Adds a new company to the register.
     * @param company the company you want to add.
     * @throws CouldNotAddCompanyException gets thrown if the company is already added.
     */
    void addCompany(Company company) throws CouldNotAddCompanyException;

    /**
     * Removes a company with the matching ID.
     * @param companyID the ID of the company.
     * @throws CouldNotRemoveCompanyException gets thrown if the company could not be removed.
     */
    void removeCompanyWithID(long companyID) throws CouldNotRemoveCompanyException;

    /**
     * Updates a company and its details.
     * @param company the company to updaate.
     * @throws CouldNotGetCompanyException gets thrown if the company is not in the system.
     */
    void updateCompany(Company company) throws CouldNotGetCompanyException;

    /**
     * Gets the company with the matching ID.
     * @param companyID the company ID.
     * @return the company with that ID.
     * @throws CouldNotGetCompanyException gets thrown when a company could not be found with that ID.
     */
    Company getCompanyWithID(long companyID) throws CouldNotGetCompanyException;

    /**
     * Gets a list with all the companies in the register.
     * @return list with all the companies.
     */
    List<Company> getAllCompanies();
}
