 package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Company;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveCompanyException;
import no.stonedstonar.BookREST.model.registers.CompanyRegister;
import no.stonedstonar.BookREST.model.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Service
public class CompanyJPA implements CompanyRegister {

    private CompanyRepository companyRepository;

    /**
     * Makes an instance of the CompanyJPA class.
     * @param companyRepository the company repository.
     */
    public CompanyJPA(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void addCompany(Company company) throws CouldNotAddCompanyException {
        checkIfCompanyIsValid(company);
        if (!companyRepository.existsById(company.getCompanyID())){
            companyRepository.save(company);
        }else {
            throw new CouldNotAddCompanyException("The company with id " + company.getCompanyID() + " is already in the system.");
        }
    }

    @Override
    public void removeCompanyWithID(long companyID) throws CouldNotRemoveCompanyException {
        checkIfCompanyIdIsAboveZero(companyID);
        if (companyRepository.existsById(companyID)){
            companyRepository.deleteById(companyID);
        }else{
            throw new CouldNotRemoveCompanyException("The company with company id " + companyID + " could not be found in the system.");
        }

    }

    @Override
    public void updateCompany(Company company) throws CouldNotGetCompanyException {
        checkIfCompanyIsValid(company);
        if (companyRepository.existsById(company.getCompanyID())){
            companyRepository.save(company);
        }else {
            throw new CouldNotGetCompanyException("The company with company id " + company.getCompanyID() + " could not be found.");
        }
    }

    @Override
    public Company getCompanyWithID(long companyID) throws CouldNotGetCompanyException {
        checkIfCompanyIdIsAboveZero(companyID);
        Optional<Company> opCom = companyRepository.findById(companyID);
        if (opCom.isEmpty()){
            throw new CouldNotGetCompanyException("The company with id " + companyID + " is not in the system.");
        }
        return opCom.get();
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new LinkedList<>();
        companyRepository.findAll().forEach(companies::add);
        return companies;
    }

    /**
     * Checks if the company is not null.
     * @param company the company to check.
     */
    private void checkIfCompanyIsValid(Company company){
        checkIfObjectIsNull(company, "company");
    }

    /**
     * Checks if teh company id is above zero.
     * @param companyID the company id to check for.
     */
    private void checkIfCompanyIdIsAboveZero(long companyID){
        checkIfLongIsAboveZero(companyID, "company id");
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
