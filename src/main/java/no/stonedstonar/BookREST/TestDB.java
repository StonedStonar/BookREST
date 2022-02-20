package no.stonedstonar.BookREST;

import no.stonedstonar.BookREST.model.*;
import no.stonedstonar.BookREST.model.database.*;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddUserException;
import no.stonedstonar.BookREST.model.exceptions.DuplicateObjectException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class TestDB {


    public static void main(String[] args) {
        makeTestLentBooks();
    }


    private static void makeTestLentBooks(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/bookREST", "dekstop", "SzzSacbkbachw");
            LentBooksRegister lentBooksRegister = new LentBookDatabase(connection);
            RegisterTestData.addLentBooksToRegister(lentBooksRegister);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (DuplicateObjectException e) {
            e.printStackTrace();
        }
    }

    private static void makeTestBranchBooks(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/bookREST", "dekstop", "SzzSacbkbachw");
            BranchBookRegister branchBookRegister = new BranchBookDatabase(connection);
            RegisterTestData.addBranchBooksToRegister(branchBookRegister);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (DuplicateObjectException e) {
            e.printStackTrace();
        }
    }

    private static void makeTestBranches(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/bookREST", "dekstop", "SzzSacbkbachw");
            LibraryDatabase libraryDatabase = new LibraryDatabase(connection);
            RegisterTestData.addBranchesToLibrary(libraryDatabase);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (DuplicateObjectException e) {
            e.printStackTrace();
        }
    }

    private static void makeTestUsers(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/bookREST", "dekstop", "SzzSacbkbachw");
            UserRegister userRegister = new UserDatabase(connection);
            RegisterTestData.addUsersToRegister(userRegister);
        }catch (SQLException exception) {
            exception.printStackTrace();
        } catch (CouldNotAddUserException e) {
            e.printStackTrace();
        }
    }

    private static void makeTestDatabaseFromBottom(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/bookREST", "dekstop", "SzzSacbkbachw");
            BookRegister bookRegister = new BookDatabase(connection);
            AuthorRegister authorRegister = new AuthorDatabase(connection);
            CompanyRegister companyRegister = new CompanyDatabase(connection);
            try {
                RegisterTestData.addAuthorsToRegister(authorRegister);
                RegisterTestData.addCompaniesToRegister(companyRegister);
                RegisterTestData.addBooksToRegister(bookRegister);
            }catch (Exception exception){
                System.out.println("Failed to add test data.");
                System.out.println(exception.getMessage());
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
