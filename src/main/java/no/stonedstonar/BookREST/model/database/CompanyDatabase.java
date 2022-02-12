package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Company;
import no.stonedstonar.BookREST.model.CompanyRegister;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class CompanyDatabase implements CompanyRegister {

    private Statement statement;

    /**
      * Makes an instance of the CompanyDatase class.
      */
    public CompanyDatabase(Connection connection){
        try {
            statement = connection.createStatement();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void addCompany(Company company) {
        checkCompany(company);
        try {
            statement.executeUpdate("INSERT INTO company(companyID, companyName) VALUES(" + company.getCompanyID() + "," + company.getCompanyName() +");");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
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

    /**
     * Checks if the company is not null.
     * @param company the company to check.
     */
    private void checkCompany(Company company){
        checkIfObjectIsNull(company, "company");
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
}
