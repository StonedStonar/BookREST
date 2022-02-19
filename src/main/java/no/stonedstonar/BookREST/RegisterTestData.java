package no.stonedstonar.BookREST;

import no.stonedstonar.BookREST.model.*;
import no.stonedstonar.BookREST.model.database.LibraryDatabase;
import no.stonedstonar.BookREST.model.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that holds methods to fill the registers with test data.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
public class RegisterTestData {

    /**
     * Adds predefined books to a register.
     * @param bookRegister the book register to fill.
     * @throws CouldNotAddBookException gets thrown if a book could not be added.
     */
    public static void addBooksToRegister(BookRegister bookRegister) throws CouldNotAddBookException {
        checkIfObjectIsNull(bookRegister, "book register");
        List<Long> authors = new ArrayList<>();
        List<Long> authors2 = new ArrayList<>();
        List<Long> authors3 = new ArrayList<>();
        List<Long> authors4 = new ArrayList<>();

        authors.add(1L);
        authors2.add(2L);
        authors3.add(3L);
        authors3.add(4L);
        authors4.add(5L);

        bookRegister.addBook(new Book(9788203192128L, "Snømannen", authors,2007, 438,1));
        bookRegister.addBook(new Book(9788203364181L, "Kniv", authors,2019, 519, 1));
        bookRegister.addBook(new Book(9788202562939L, "Lars Monsen - mitt liv", authors2, 2020, 246, 2));
        bookRegister.addBook(new Book(9788234702235L, "Verden ifølge Vinni", authors3, 2021, 236, 3));
        bookRegister.addBook(new Book(9788202713461L, "Min skyld - en historie om frigjøring", authors4, 2021, 239, 2));
    }

    public static void addLentBooksToRegister(LentBooksRegister lentBooksRegister){
        checkIfObjectIsNull(lentBooksRegister, "lent books register");
        lentBooksRegister.addLentBook(new LentBook(1, 1, LocalDate.now(),));
    }

    /**
     * Adds a predefined amount of branch books to the register.
     * @param branchBookRegister the branch book register to add to.
     * @throws DuplicateObjectException gets thrown if the branch book is already in the system.
     */
    public static void addBranchBooksToRegister(BranchBookRegister branchBookRegister) throws DuplicateObjectException {
        checkIfObjectIsNull(branchBookRegister, "branch book register");
        int amount = 1;
        for (int i = 1; i < 4; i++){
            branchBookRegister.addBranchBook(new BranchBook(amount, 9788202562939L, i));
            branchBookRegister.addBranchBook(new BranchBook(amount + 1 , 9788202713461L, i));
            branchBookRegister.addBranchBook(new BranchBook(amount + 2, 9788203192128L, i));
            branchBookRegister.addBranchBook(new BranchBook(amount + 3, 9788203364181L, i));
            branchBookRegister.addBranchBook(new BranchBook(amount + 4, 9788234702235L ,i));
            amount += 5;
        }

    }

    /**
     * Adds all the predefined authors to an author register.
     * @param authorRegister the author register to add to.
     * @throws CouldNotAddAuthorException gets thrown if one author could not be added.
     */
    public static void addAuthorsToRegister(AuthorRegister authorRegister) throws CouldNotAddAuthorException {
        checkIfObjectIsNull(authorRegister, "authorregister");
        authorRegister.addAuthor(new Author(1, "Jo", "Nesbø", 1960));
        authorRegister.addAuthor(new Author(2, "Lars", "Monsen", 1963));
        authorRegister.addAuthor(new Author(3, "Øyvind", "Sauvik", 1976));
        authorRegister.addAuthor(new Author(4, "Nils", "Anker", 1961));
        authorRegister.addAuthor(new Author(5, "Abid", "Raja", 1975));
    }

    /**
     * Adds all the predefined companies to a company register.
     * @param companyRegister the company register to add to.
     * @throws CouldNotGetCompanyException gets thrown when a company could not be found with that ID.
     */
    public static void addCompaniesToRegister(CompanyRegister companyRegister) throws CouldNotAddCompanyException {
        checkIfObjectIsNull(companyRegister, "companyregister");
        companyRegister.addCompany(new Company(1, "Aschehoug"));
        companyRegister.addCompany(new Company(2, "Cappelen Damm"));
        companyRegister.addCompany(new Company(3, "Strawberry Publishing"));
    }

    /**
     * Adds a predefined amount of users to the register.
     * @param userRegister the user register to add to.
     * @throws CouldNotAddUserException gets thrown if one of the users could not be added.
     */
    public static void addUsersToRegister(UserRegister userRegister) throws CouldNotAddUserException {
        checkIfObjectIsNull(userRegister, "userregister");
        userRegister.addUser(new User(1, "Bjarne", "Bjarnesen", "bjarne@bjarne.com"));
        userRegister.addUser(new User(2, "Leif", "Lofsen", "leif@gmail.com"));
        userRegister.addUser(new User(3, "Tbone", "Lavar", "t@gmail.com"));
    }

    /**
     * Adds a preset amount of branches to the library database.
     * @param libraryDatabase the library database to add to.
     * @throws DuplicateObjectException gets thrown if the branch is already added to the database.
     */
    public static void addBranchesToLibrary(LibraryDatabase libraryDatabase) throws DuplicateObjectException {
        checkIfObjectIsNull(libraryDatabase, "library database");
        libraryDatabase.addNewBranch(new Branch(1, "Oslo"));
        libraryDatabase.addNewBranch(new Branch(2, "Ålesund"));
        libraryDatabase.addNewBranch(new Branch(3, "Trondheim"));
    }
    
    /**
     * Checks if an object is null.
     * @param object the object you want to check.
     * @param error the error message the exception should have.
     */
    private static void checkIfObjectIsNull(Object object, String error){
       if (object == null){
           throw new IllegalArgumentException("The " + error + " cannot be null.");
       }
    }
}
