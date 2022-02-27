package no.stonedstonar.BookREST;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Component
public class JdbcConnection {

    @Value("${MYSQL_HOST}")
    private String address;

    @Value("${MYSQL_USER}")
    private String user;

    @Value("${MYSQL_PASSWORD}")
    private String password;

    private Connection connection;

    /**
      * Makes an instance of the JdbcConnection class.
      */
    public JdbcConnection(){

    }

    /**
     * Connects the JDBC connection to the database.
     */
    public Connection connect() throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(address, user, password);
        }
        return connection;
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
