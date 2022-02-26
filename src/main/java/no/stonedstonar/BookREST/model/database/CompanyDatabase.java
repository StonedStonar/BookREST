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
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a interface to a database with companies.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class CompanyDatabase implements CompanyRegister {

    private Statement statement;

    /**
      * Makes an instance of the CompanyDatabase class.
      * @param connection the connection to the database.
      * @throws SQLException gets thrown if the connection to the DB could not be made.
      */
    public CompanyDatabase(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public void addCompany(Company company) throws CouldNotAddCompanyException, SQLException {
        checkCompany(company);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM company WHERE companyID = " + company.getCompanyName());
        if (!resultSet.next()){
            statement.executeUpdate("INSERT INTO company(companyID, companyName) VALUES(" + company.getCompanyID() + "," + makeSQLString(company.getCompanyName())+ ");");
        }else {
            throw new CouldNotAddCompanyException("The company with the id " + company.getCompanyID() + " is already in the system." );
        }
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public void removeCompanyWithID(long companyID) throws CouldNotRemoveCompanyException, SQLException {
        checkCompanyID(companyID);
        int amount = statement.executeUpdate("DELETE FROM company WHERE companyID = " + companyID + ";");
        if (amount == 0){
            throw new CouldNotRemoveCompanyException("The company with the id " + companyID + " could not be found.");
        }
    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public Company getCompanyWithID(long companyID) throws CouldNotGetCompanyException, SQLException {
        checkCompanyID(companyID);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM company WHERE companyID = " + companyID + ";");
        if (!resultSet.next()){
            throw new CouldNotGetCompanyException("The company with the id " + companyID + " could not be found in the system.");
        }
        return makeSQLIntoCompany(resultSet);

    }

    /**
     * @throws SQLException gets thrown if the connection to the DB could not be made.
     */
    @Override
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new LinkedList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM company;");
        while (resultSet.next()){
            companies.add(makeSQLIntoCompany(resultSet));
        }
        return companies;
    }

    /**
     * Takes a result set and makes a company out of it.
     * @param resultSet the wanted result set.
     * @return the company that is the first in this set.
     * @throws SQLException gets thrown if the resultset is empty.
     */
    private Company makeSQLIntoCompany(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()){
            resultSet.next();
        }
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
