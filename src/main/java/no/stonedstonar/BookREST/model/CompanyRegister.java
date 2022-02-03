package no.stonedstonar.BookREST.model;

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
     */
    void addCompany(Company company);

    /**
     * Adds a new company to the register.
     * @param companyName the new company's name.
     */
    void addCompanyWithDetails(String companyName);

    /**
     * Removes a company with the matching ID.
     * @param companyID the ID of the company.
     */
    void removeCompanyWithID(long companyID);

    /**
     * Gets the company with the matching ID.
     * @param companyID the company ID.
     */
    void getCompanyWithID(long companyID);

    /**
     * Gets a list with all the companies in the register.
     * @return list with all the companies.
     */
    List<Company> getAllCompanies();
}
