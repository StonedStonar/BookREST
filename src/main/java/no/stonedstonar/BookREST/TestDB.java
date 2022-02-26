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
        makeTestReturnedBooks();
    }


    private static void makeTestReturnedBooks(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookREST", "root", "SzzSacbkbachw");
            LentBooksLog lentBooksLog = new LentBooksLogDatabase(connection);
            RegisterTestData.addReturnedBooksToRegister(lentBooksLog);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (DuplicateObjectException e) {
            e.printStackTrace();
        }
    }

    private static void makeTestLentBooks(){
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookREST", "root", "SzzSacbkbachw");
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookREST", "root", "SzzSacbkbachw");
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookREST", "root", "SzzSacbkbachw");
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookREST", "root", "SzzSacbkbachw");
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/bookREST", "root", "SzzSacbkbachw");
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
