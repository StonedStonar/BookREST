package no.stonedstonar.BookREST.model;

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
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void addCompany(Company company) throws CouldNotAddCompanyException, SQLException;

    /**
     * Removes a company with the matching ID.
     * @param companyID the ID of the company.
     * @throws CouldNotRemoveCompanyException gets thrown if the company could not be removed.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    void removeCompanyWithID(long companyID) throws CouldNotRemoveCompanyException, SQLException;

    /**
     * Gets the company with the matching ID.
     * @param companyID the company ID.
     * @return the company with that ID.
     * @throws CouldNotGetCompanyException gets thrown when a company could not be found with that ID.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    Company getCompanyWithID(long companyID) throws CouldNotGetCompanyException, SQLException;

    /**
     * Gets a list with all the companies in the register.
     * @return list with all the companies.
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    List<Company> getAllCompanies() throws SQLException;
}
