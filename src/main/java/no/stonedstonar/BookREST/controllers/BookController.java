package no.stonedstonar.BookREST.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import no.stonedstonar.BookREST.model.BookRegister;
import no.stonedstonar.BookREST.JdbcConnection;
import no.stonedstonar.BookREST.model.database.BookDatabase;
import no.stonedstonar.BookREST.model.exceptions.*;
import no.stonedstonar.BookREST.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@RestController
@CrossOrigin
@RequestMapping("/books")
public class BookController {

    private final JdbcConnection jdbcConnection;

    private BookRegister bookRegister;

    /**
      * Makes an instance of the BookController class.
     * @param jdbcConnection
     */
    public BookController(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
        LocalDate.now().plusYears(2).getYear();
        try {
            bookRegister = new BookDatabase(this.jdbcConnection.connect());
        }catch (SQLException exception){
            System.err.println("Could not connect the book database.");
        }
    }

    /**
     * Gets all the books in the register.
     * @return all the books in the register.
     */
    @GetMapping
    public List<Book> getBooks(@RequestParam(value = "authorID", required = false) Optional<Long> optionalAuthorID) throws SQLException {
        if (optionalAuthorID.isEmpty()){
            return bookRegister.getBookList();
        }else {
            return bookRegister.getAllBooksOfAuthorID(optionalAuthorID.get());
        }
    }

    /**
     * Gets a book by its id.
     * @param id the ID of the book.
     * @return a book that matches that description.
     * @throws CouldNotGetBookException gets thrown if the book could not be found.
     */
    @ApiOperation(value = "Finds the book with ID",
            notes = "Provide an ID to look up for a specific book.",
            response = Book.class)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) throws CouldNotGetBookException, SQLException {
        return bookRegister.getBook(id);
    }

    /**
     * Adds a new book to the register.
     * @param body the body that contains the JSON file.
     * @throws JsonProcessingException gets thrown if the JSON could not be translated to the wanted object.
     * @throws CouldNotAddBookException gets thrown if the book could not be added.
     */
    @PostMapping
    public void postBook(@RequestBody String body) throws JsonProcessingException, CouldNotAddBookException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book book = objectMapper.readValue(body, Book.class);
        bookRegister.addBook(book);
    }

    /**
     * Changes the existing book and its details.
     * @param body the body of the HTTP request.
     * @throws JsonProcessingException gets thrown if the body could not be made into a book.
     * @throws CouldNotGetBookException gets thrown if the target book could not be found.
     */
    @PutMapping
    public void changeBook(@RequestBody String body) throws JsonProcessingException, CouldNotGetBookException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        Book newDetails = objectMapper.readValue(body, Book.class);
        Book bookToChange = bookRegister.getBook(newDetails.getISBN());
        String title = newDetails.getTitle();
        int year = newDetails.getYear();
        int pages = newDetails.getNumberOfPages();
        if (pages > 0){
            bookToChange.setNumberOfPages(pages);
        }
        if (year > Integer.MIN_VALUE){
            bookToChange.setYear(year);
        }
        if (!title.isEmpty()){
            bookToChange.setTitle(title);
        }
        List<Long> authors = newDetails.getAuthors();
        authors.forEach(author -> {
            //Todo: Maybe change this.
            if (author < 0){
                try {
                    System.out.println(-author);
                    bookToChange.removeAuthor(-author);
                }catch (CouldNotRemoveAuthorException exception){

                }
            }else if (author > 0){
                try {
                    bookToChange.addAuthor(author);
                }catch (CouldNotAddAuthorException exception){
                }
            }
        });
    }

    /**
     * Handles a sql exception.
     * @param exception the exception to handle.
     * @return a response based on the exception.
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not connect to mysql server.");
    }


    /**
     * Deletes a book from the book register.
     * @param id the ID of the book.
     * @throws CouldNotGetBookException gets thrown if the book could not be found.
     * @throws CouldNotRemoveBookException gets thrown if the book could not be removed.
     */
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) throws CouldNotGetBookException, CouldNotRemoveBookException, SQLException {
        Book book = bookRegister.getBook(id);
        bookRegister.removeBook(book);
    }

    /**
     * Handles a invalid JSON format request.
     * @return the response according to exception.
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonFormatFault(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Format on JSON file is invalid.");
    }

    /**
     * Handles the CouldNotGetBookException.
     * @param exception the exception that was thrown.
     * @return a response according to the exception.
     */
    @ExceptionHandler(CouldNotGetBookException.class)
    public ResponseEntity<String> handleCouldNotGetBookException(CouldNotGetBookException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }


    /**
     * Handles a CouldNotRemoveBookException
     * @param exception the exception that was thrown.
     * @return a response according to the exception
     */
    @ExceptionHandler(CouldNotRemoveBookException.class)
    public ResponseEntity<String> handleCouldNotRemoveBookException(CouldNotGetBookException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles the CouldNotAddBookException.
     * @param exception the exception that was thrown.
     * @return a response according to the exception.
     */
    @ExceptionHandler(CouldNotAddBookException.class)
    public ResponseEntity<String> handleCouldNotAddBookException(CouldNotAddBookException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
