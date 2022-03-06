package no.stonedstonar.BookREST;

import no.stonedstonar.BookREST.model.*;
import no.stonedstonar.BookREST.model.database.*;
import no.stonedstonar.BookREST.model.exceptions.*;
import no.stonedstonar.BookREST.model.registers.*;
import no.stonedstonar.BookREST.model.repositories.*;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A class that holds methods to fill the registers with test data.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@Component
public class RegisterTestData {

    private HashMap<String, String> tablesMap;

    /**
     * Makes an instance of the RegisterTestData to fill it with test data.
     * @param bookRepository
     * @param authorRepository
     * @param companyRepository
     * @param branchRepository
     * @param userRepository
     * @param branchBookRepository
     * @param lentBookRepository
     * @param lentBooksLogRepository
     * @param addressRepository
     */
    public RegisterTestData(BookRepository bookRepository, AuthorRepository authorRepository, CompanyRepository companyRepository, BranchRepository branchRepository,
                            UserRepository userRepository, BranchBookRepository branchBookRepository, LentBookRepository lentBookRepository,
                            LentBooksLogRepository lentBooksLogRepository, AddressRepository addressRepository){
        tablesMap = new HashMap<>();

        BookRegister bookRegister = new BookJPA(bookRepository);
        AuthorRegister authorRegister = new AuthorJPA(authorRepository);
        CompanyRegister companyRegister = new CompanyJPA(companyRepository);
        BranchRegister branchRegister = new BranchJPA(branchRepository);
        UserRegister userRegister = new UserJPA(userRepository, addressRepository);
        BranchBookRegister branchBookRegister = new BranchBookJPA(branchBookRepository);
        LentBooksRegister lentBooksRegister = new LentBookJPA(lentBookRepository, lentBooksLogRepository);
        LentBooksLog lentBooksLog = new LentBooksLogJPA(lentBooksLogRepository);
        //checkIfTableIsMadeIfNotADd(connection);
        try {
            addAuthorsToRegister(authorRegister);
            addCompaniesToRegister(companyRegister);
            addBooksToRegister(bookRegister, authorRegister.getAuthorList(), companyRegister.getAllCompanies());
            addBranchesToLibrary(branchRegister);
            addUsersToRegister(userRegister);
            addBranchBooksToRegister(branchBookRegister,bookRegister.getBookList() , branchRegister.getAllBranches());
            addLentBooksToRegister(lentBooksRegister, branchBookRegister.getAllBranchBooks(), userRegister.getAllUsers());
            addReturnedBooksToRegister(lentBooksLog, branchBookRegister.getAllBranchBooks(), userRegister.getAllUsers());
        }catch (Exception exception){
            System.out.println("Failed to add test data.");
            System.out.println(exception.getMessage());
        }
    }


    /**
     * Checks if a table is already made. If it's not made this logic makes it.
     * @param connection the connection to the database.
     * @throws SQLException gets thrown if the connection to the database gets removed before this code is done.
     * @throws IOException gets thrown if the sql file could not be read.
     */
    private void checkIfTableIsMadeIfNotADd(Connection connection) throws SQLException, IOException {
        ResultSet resultSet = connection.createStatement().executeQuery("show tables");
        List<String> tablesNames = makeTablesMapAndReturnTableNames();
        List<String> allReadyAddedTables = getAllReadyAddedTables(resultSet);

        List<String> sqlTablesToRun;
        if (allReadyAddedTables.isEmpty()){
            sqlTablesToRun = tablesNames;
        }else {
            sqlTablesToRun = tablesNames.stream().filter(table -> allReadyAddedTables.stream().noneMatch(name -> name.equalsIgnoreCase(table))).toList();
        }
        Iterator<String> it = sqlTablesToRun.iterator();
        Statement statement = connection.createStatement();
        while (it.hasNext()){
            String tableName = it.next();
            String sqlStatement = tablesMap.get(tableName);
            statement.executeUpdate(sqlStatement);
        }
    }

    /**
     * Gets all the tables that are added to the database.
     * @param resultSet the result set to go through.
     * @return a list with all the tables that are allready added to the database.
     * @throws SQLException gets thrown if the connection to the database closes while the resultset is being used.
     */
    private List<String> getAllReadyAddedTables(ResultSet resultSet) throws SQLException {
        List<String> tables = new LinkedList<>();
        while (resultSet.next()){
            tables.add(resultSet.getString("Tables_in_bookrest"));
        }
        return tables;
    }

    /**
     * Takes the sql stored in the sqlSetup.txt file and makes it into a map with table name as key and value with sql code.
     * @return a list with all the table names in the right order.
     * @throws IOException gets thrown if the file could not be read.
     */
    private List<String> makeTablesMapAndReturnTableNames() throws IOException {
        InputStream in = RegisterTestData.class.getClassLoader().getResourceAsStream("sqlSetup.txt");
        List<String> tableNames = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))) {
            String sentance = bufferedReader.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            String nameOfTable = "";
            do {
                if (!sentance.isEmpty()){
                    String[] strings = sentance.split(" ");
                    if (strings[0].equals("CREATE")){
                        nameOfTable = getNameOfTable(sentance);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(sentance);
                    }else if (sentance.equals(");")){
                        tableNames.add(nameOfTable);
                        stringBuilder.append(sentance);
                        String sqlSyntax = stringBuilder.toString();
                        tablesMap.put(nameOfTable, sqlSyntax);
                    }else {
                        stringBuilder.append(sentance);
                    }
                }
                sentance = bufferedReader.readLine();
            }while (sentance != null);
        } catch (IOException exception) {
            System.err.println("Could not read the file to make the database.");
            throw exception;
        }
        return tableNames;
    }

    private String getNameOfTable(String string){
        String[] strings = string.split(" ");
        return strings[2];
    }

    /**
     * Adds predefined books to a register.
     * @param bookRegister the book register to fill.
     * @throws CouldNotAddBookException gets thrown if a book could not be added.
     */
    private void addBooksToRegister(BookRegister bookRegister, List<Author> originalAuthors, List<Company> companies) throws CouldNotAddBookException, SQLException {
        checkIfObjectIsNull(bookRegister, "book register");
        if (bookRegister.getBookList().isEmpty()){
            List<Author> authors = new ArrayList<>();
            List<Author> authors2 = new ArrayList<>();
            List<Author> authors3 = new ArrayList<>();
            List<Author> authors4 = new ArrayList<>();
            List<Author> jkRowling = new ArrayList<>();
            List<Author> dennis = new ArrayList<>();
            List<Author> øystein = new ArrayList<>();
            List<Author> johan = new ArrayList<>();
            List<Author> nora = new ArrayList<>();

            authors.add(getAuthorFromList("Jo", originalAuthors));
            authors2.add(getAuthorFromList("Lars", originalAuthors));
            authors3.add(getAuthorFromList("Nils", originalAuthors));
            authors3.add(getAuthorFromList("Øyvind", originalAuthors));
            authors4.add(getAuthorFromList("Abid", originalAuthors));
            jkRowling.add(getAuthorFromList("J.K", originalAuthors));
            dennis.add(getAuthorFromList("Dennis", originalAuthors));
            øystein.add(getAuthorFromList("Øystein", originalAuthors));
            johan.add(getAuthorFromList("Johan", originalAuthors));
            nora.add(getAuthorFromList("Nora", originalAuthors));


            bookRegister.addBook(new Book(9788203192128L, "Snømannen", authors,2007, 438,companies.get(0)));
            bookRegister.addBook(new Book(9788203364181L, "Kniv", authors,2019, 519, companies.get(0)));
            bookRegister.addBook(new Book(9788202562939L, "Lars Monsen - mitt liv", authors2, 2020, 246, companies.get(1)));
            bookRegister.addBook(new Book(9788234702235L, "Verden ifølge Vinni", authors3, 2021, 236, companies.get(2)));
            bookRegister.addBook(new Book(9788202713461L, "Min skyld - en historie om frigjøring", authors4, 2021, 239, companies.get(2)));
            bookRegister.addBook(new Book(9788203361890L, "Gjenferd", authors, 2017,466 , companies.get(0)));
            bookRegister.addBook(new Book(9788203366741L, "Kongeriket",authors , 2021, 630, companies.get(0)));
            bookRegister.addBook(new Book(9788203379635L, "Spiselig hage", dennis, 2022, 185, companies.get(0)));
            bookRegister.addBook(new Book(9788269264104L, "Helt konge! - en dritenkel metode for alle som vil lykkes med alt de gjør!", øystein, 2022, 293, companies.get(3)));
            bookRegister.addBook(new Book(9788202459772L, "Harry Potter og de vises stein", jkRowling, 2015,246, companies.get(1)));
            bookRegister.addBook(new Book( 9788202475994L,"Harry Potter og mysteriekammeret", jkRowling, 2016, 259, companies.get(1)));
            bookRegister.addBook(new Book(9788202476007L,"Harry Potter og fangen fra Azkaban", jkRowling, 2017, 325, companies.get(1)));
            bookRegister.addBook(new Book(9788202476014L,"Harry Potter og ildbegeret", jkRowling, 2019, 450, companies.get(1)));
            bookRegister.addBook(new Book(9788241955242L ,"En nasjon i sjokk", johan, 2022, 426, companies.get(4)));
            bookRegister.addBook(new Book(9788243013513L,"Ok boomer!", nora,2021, 155, companies.get(5)));
        }
    }

    /**
     * Gets authors from the list of authors.
     * @param firstNameOfAuthor the first name of the author.
     * @param originalAuthors the original list of authors that are in the database.
     * @return a author that matches the first name.
     */
    private Author getAuthorFromList(String firstNameOfAuthor, List<Author> originalAuthors){
        return originalAuthors.stream().filter(author -> author.getFirstName().equalsIgnoreCase(firstNameOfAuthor)).findFirst().get();
    }

    /**
     * Adds predefined returned book to register.
     * @param lentBooksLog the lent books log to add to.
     * @throws CouldNotAddLentBookException gets thrown if the returned book is already in the register.
     */
    private void addReturnedBooksToRegister(LentBooksLog lentBooksLog, List<BranchBook> branchBooks, List<User> users) throws CouldNotAddLentBookException {
        if (lentBooksLog.getAllReturnedBooks().isEmpty()){
            lentBooksLog.addReturnedLentBook(new ReturnedLentBook(branchBooks.get(0), users.get(0), LocalDate.now().minusDays(3), LocalDate.now(), LocalDate.now()));
            lentBooksLog.addReturnedLentBook(new ReturnedLentBook(branchBooks.get(6), users.get(1), LocalDate.now().minusDays(7), LocalDate.now().minusDays(4), LocalDate.now().minusDays(3)));
            lentBooksLog.addReturnedLentBook(new ReturnedLentBook(branchBooks.get(6), users.get(1), LocalDate.now().minusDays(8), LocalDate.now().minusDays(7), LocalDate.now().minusDays(3)));
        }
    }

    /**
     *
     * @param lentBooksRegister
     * @throws CouldNotAddLentBookException
     */
    private void addLentBooksToRegister(LentBooksRegister lentBooksRegister, List<BranchBook> branchBooks, List<User> users) throws CouldNotAddLentBookException {
        checkIfObjectIsNull(lentBooksRegister, "lent books register");
        if (lentBooksRegister.getAllLentBooks().isEmpty()){
            lentBooksRegister.addLentBook(new LentBook(branchBooks.get(1), users.get(0), LocalDate.now(), LocalDate.now().plusDays(1)));
            lentBooksRegister.addLentBook(new LentBook(branchBooks.get(0), users.get(0), LocalDate.now().minusDays(4), LocalDate.now().minusDays(1)));
            lentBooksRegister.addLentBook(new LentBook(branchBooks.get(5), users.get(0), LocalDate.now().minusDays(14), LocalDate.now().minusDays(10)));
            lentBooksRegister.addLentBook(new LentBook(branchBooks.get(10), users.get(1), LocalDate.now(), LocalDate.now().plusDays(15)));
            lentBooksRegister.addLentBook(new LentBook(branchBooks.get(15), users.get(4), LocalDate.now().minusDays(1), LocalDate.now().plusDays(5)));
            lentBooksRegister.addLentBook(new LentBook(branchBooks.get(35), users.get(3), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10)));

        }

    }

    /**
     * Adds a predefined amount of branch books to the register.
     * @param branchBookRegister the branch book register to add to.
     * @throws CouldNotAddBranchBookException gets thrown if the branch book is already in the system.
     */
    private void addBranchBooksToRegister(BranchBookRegister branchBookRegister, List<Book> books ,List<Branch> branches) throws CouldNotAddBranchBookException {
        checkIfObjectIsNull(branchBookRegister, "branch book register");
        if (!branchBookRegister.checkIfBranchBooksRegisterHasBooks()){
            AtomicLong amount = new AtomicLong();
            for (Book book : books) {
                for (Branch branch : branches) {
                    branchBookRegister.addBranchBook(new BranchBook(amount.incrementAndGet() * 1000, book, branch));
                }
            }
        }
    }

    /**
     * Adds all the predefined authors to an author register.
     * @param authorRegister the author register to add to.
     * @throws CouldNotAddAuthorException gets thrown if one author could not be added.
     * @return a list with all the authors added.
     */
    private void addAuthorsToRegister(AuthorRegister authorRegister) throws CouldNotAddAuthorException, SQLException {
        checkIfObjectIsNull(authorRegister, "authorregister");
        if (authorRegister.getAuthorList().isEmpty()){
            authorRegister.addAuthor(new Author(1, "Jo", "Nesbø", 1960));
            authorRegister.addAuthor(new Author(2, "Lars", "Monsen", 1963));
            authorRegister.addAuthor(new Author(3, "Øyvind", "Sauvik", 1976));
            authorRegister.addAuthor(new Author(4, "Nils", "Anker", 1961));
            authorRegister.addAuthor(new Author(5, "Abid", "Raja", 1975));
            authorRegister.addAuthor(new Author(6, "J.K", "Rowling", 1965));
            authorRegister.addAuthor(new Author(7, "Dennis", "Asbjørnsen", 1998));
            authorRegister.addAuthor(new Author(8, "Øystein", "Pettersen", 1983));
            authorRegister.addAuthor(new Author(9, "Johan", "Høst", 1971));
            authorRegister.addAuthor(new Author(10, "Nora", "Jungeilges Heyerdahl", 1999));

        }
    }

    /**
     * Adds all the predefined companies to a company register.
     * @param companyRegister the company register to add to.
     * @throws CouldNotGetCompanyException gets thrown when a company could not be found with that ID.
     */
    private void addCompaniesToRegister(CompanyRegister companyRegister) throws CouldNotAddCompanyException, SQLException {
        checkIfObjectIsNull(companyRegister, "companyregister");
        if (companyRegister.getAllCompanies().isEmpty()){
            companyRegister.addCompany(new Company(1, "Aschehoug"));
            companyRegister.addCompany(new Company(2, "Cappelen Damm"));
            companyRegister.addCompany(new Company(3, "Strawberry Publishing"));
            companyRegister.addCompany(new Company(4, "Gode dager"));
            companyRegister.addCompany(new Company(5, "Vigmostad Bjørke"));
            companyRegister.addCompany(new Company(6, "Spartacus"));
        }
    }

    /**
     * Adds a predefined amount of users to the register.
     * @param userRegister the user register to add to.
     * @throws CouldNotAddUserException gets thrown if one of the users could not be added.
     */
    private void addUsersToRegister(UserRegister userRegister) throws CouldNotAddUserException, SQLException, CouldNotAddAddressException {
        checkIfObjectIsNull(userRegister, "userregister");
        if (!userRegister.checkIfRegisterHasUsers()){
            User bjarne = new User(1, "Bjarne", "Bjarnesen", "bjarne@bjarne.com", "pas");
            User leif = new User(2, "Leif", "Lofsen", "leif@gmail.com", "password");
            User tbone = new User(3, "Tbone", "Lavar", "t@gmail.com", "pp");
            User testMan = new User(4, "Test", "Man", "test@man.com", "testMaN");
            User feil = new User(5, "feil", "main", "fa@il.com", "failure");

            bjarne.addAddress(new Address("Ålevegen", 12, 2010));
            bjarne.addAddress(new Address("BrasseTassen", 15, 2000));

            leif.addAddress(new Address("Rave", 15, 6015));
            tbone.addAddress(new Address("Trese", 16, 7015));

            feil.addAddress(new Address("Bjarnesgate", 16, 6016));
            testMan.addAddress(new Address("Feilgate", 600, 7050));

            userRegister.addUser(bjarne);
            userRegister.addUser(leif);
            userRegister.addUser(tbone);
            userRegister.addUser(testMan);
            userRegister.addUser(feil);
        }
    }

    /**
     * Adds a preset amount of branches to the library database.
     * @param libraryDatabase the library database to add to.
     * @throws CouldNotAddBranchException gets thrown if the branch is already added to the database.
     */
    private void addBranchesToLibrary(BranchRegister libraryDatabase) throws CouldNotAddBranchException {
        checkIfObjectIsNull(libraryDatabase, "library database");
        if (libraryDatabase.getAllBranches().isEmpty()){
            libraryDatabase.addBranch(new Branch(1, "Oslo"));
            libraryDatabase.addBranch(new Branch(2, "Ålesund"));
            libraryDatabase.addBranch(new Branch(3, "Trondheim"));
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
