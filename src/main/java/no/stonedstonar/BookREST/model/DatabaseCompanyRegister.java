package no.stonedstonar.BookREST.model;

import no.stonedstonar.BookREST.model.Company;
import no.stonedstonar.BookREST.model.CompanyRegister;

import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class DatabaseCompanyRegister implements CompanyRegister {


    /**
      * Makes an instance of the DatabaseCompanyRegister class.
      */
    public DatabaseCompanyRegister(){

    }
    
    /**
     * Checks if a string is of a valid format or not.
     * @param stringToCheck the string you want to check.
     * @param errorPrefix the error the exception should have if the string is invalid.
     */
    private void checkString(String stringToCheck, String errorPrefix){
        checkIfObjectIsNull(stringToCheck, errorPrefix);
        if (stringToCheck.isEmpty()){
            throw new IllegalArgumentException("The " + errorPrefix + " cannot be empty.");
        }
    }
    
    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }

    @Override
    public void addCompany(Company company) {

    }

    @Override
    public void addCompanyWithDetails(String companyName) {

    }

    @Override
    public void removeCompanyWithID(long companyID) {

    }

    @Override
    public void getCompanyWithID(long companyID) {

    }

    @Override
    public List<Company> getAllCompanies() {
        return null;
    }
}
