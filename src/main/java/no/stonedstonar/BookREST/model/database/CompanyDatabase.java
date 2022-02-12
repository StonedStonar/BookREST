package no.stonedstonar.BookREST.model.database;

import no.stonedstonar.BookREST.model.Company;
import no.stonedstonar.BookREST.model.CompanyRegister;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveCompanyException;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
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
      * Makes an instance of the CompanyDatabase class.
      * @param connection the connection to the database.
      */
    public CompanyDatabase(Connection connection){
        try {
            statement = connection.createStatement();
        }catch (Exception exception){
            System.err.println(exception.getMessage());
        }
    }

    @Override
    public void addCompany(Company company) throws CouldNotAddCompanyException {
        checkCompany(company);
        try {
            statement.executeUpdate("INSERT INTO company(companyID, companyName) VALUES(" + company.getCompanyID() + "," + makeSQLString(company.getCompanyName())+ ");");
        } catch (SQLException exception) {
            throw new CouldNotAddCompanyException("The company with the id " + company.getCompanyID() + " is already in the system." );
        }
    }

    @Override
    public void removeCompanyWithID(long companyID) throws CouldNotRemoveCompanyException {
        checkCompanyID(companyID);
        try {
            statement.executeUpdate("DELETE FROM company WHERE companyID = " + companyID + ";");
        } catch (SQLException exception) {
            throw new CouldNotRemoveCompanyException("The company with the id " + companyID + " could not be found.");
        }
    }

    @Override
    public Company getCompanyWithID(long companyID) throws CouldNotGetCompanyException {
        checkCompanyID(companyID);
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM company WHERE companyID = " + companyID + ";");
            return makeSQLIntoCompany(resultSet);
        } catch (SQLException exception) {
            throw new CouldNotGetCompanyException("The company with the id " + companyID + " could not be found in the system.");
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        return null;
    }

    /**
     * Takes a result set and makes a company out of it.
     * @param resultSet the wanted result set.
     * @return the company that is the first in this set.
     * @throws SQLException gets thrown if the resultset is empty.
     */
    private Company makeSQLIntoCompany(ResultSet resultSet) throws SQLException {
        return new Company(resultSet.getLong("companyID"), resultSet.getString("companyName"));
    }

    /**
     * Makes a string into the format SQL needs for a string. The quotes is added. So hi turns into "hi".
     * @param statement the statement you want to make into a string.
     * @return a string with " around it.
     */
    private String makeSQLString(String statement){
        return "\"" + statement + "\"";
    }

    /**
     * Checks if the company ID is a valid format.
     * @param companyID the companyID to check.
     */
    private void checkCompanyID(long companyID){
        checkIfLongIsAboveZero(companyID, "company id");
    }

    /**
     * Checks if the company is not null.
     * @param company the company to check.
     */
    private void checkCompany(Company company){
        checkIfObjectIsNull(company, "company");
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
