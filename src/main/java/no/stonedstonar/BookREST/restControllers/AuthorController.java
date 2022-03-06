package no.stonedstonar.BookREST.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.stonedstonar.BookREST.model.database.AuthorJPA;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddAuthorException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveAuthorException;
import no.stonedstonar.BookREST.model.registers.AuthorRegister;
import no.stonedstonar.BookREST.model.Author;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetAuthorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a author controller endpoint.
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@RestController
@CrossOrigin
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorRegister authorRegister;

    /**
      * Makes an instance of the AuthorController class.
     */
    public AuthorController(AuthorJPA authorJPA) {
        this.authorRegister = authorJPA;
    }

    /**
     * Gets the authors from the register.
     * @param authorIDs a value that can be given to find a list of authors.
     * @return a list with all the requested authors.
     * @throws CouldNotGetAuthorException gets thrown if one of the specified authors could not be located.
     */
    @GetMapping
    public List<Author> getAuthors(@RequestParam(value = "authorID", required = false) List<Long> authorIDs) throws CouldNotGetAuthorException {
        List<Author> authors;
        if (authorIDs == null || authorIDs.isEmpty()){
            authors =  authorRegister.getAuthorList();
        }else {
            authors = new LinkedList<>();
            for (long authorID : authorIDs){
                authors.add(authorRegister.getAuthorById(authorID));
            }
        }
        return authors;
    }

    /**
     * Gets the author with a specific ID.
     * @param id the ID of the author.
     * @return the author object with that ID.
     * @throws CouldNotGetAuthorException gets thrown if the auhtor could not be located.
     */
    @GetMapping("/{id}")
    public Author getAuthorWithID(@PathVariable long id) throws CouldNotGetAuthorException {
        return authorRegister.getAuthorById(id);
    }

    /**
     * Adds an author to the system.
     * @param body the author to add as json.
     * @throws CouldNotAddAuthorException gets thrown if the author could not be added.
     */
    @PostMapping
    public void addAuthor(@RequestBody String body) throws CouldNotAddAuthorException, JsonProcessingException {
        authorRegister.addAuthor(getAuthor(body));
    }

    /**
     * Changes an authors details.
     * @param body the author to change with its original author id.
     * @throws CouldNotGetAuthorException gets thrown if the author could not be located.
     */
    @PutMapping
    public void changeAuthor(@RequestBody String body) throws CouldNotGetAuthorException, JsonProcessingException {
        authorRegister.updateAuthor(getAuthor(body));
    }

    /**
     * Gets the author from the request body.
     * @param body the body to get author from.
     * @return the author that is needed.
     * @throws JsonProcessingException gets thrown if the body is not a valid input.
     */
    private Author getAuthor(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, Author.class);
    }

    /**
     * Deletes an author from the system.
     * @param body the author to delete.
     * @throws CouldNotRemoveAuthorException gets thrown if the author could not be deleted.
     */
    @DeleteMapping
    public void deleteAuthor(@RequestBody String body) throws CouldNotRemoveAuthorException, JsonProcessingException {
        authorRegister.removeAuthor(getAuthor(body));
    }


    @ExceptionHandler(CouldNotGetAuthorException.class)
    public ResponseEntity<String> handleCouldNotGetException(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(CouldNotAddAuthorException.class)
    public ResponseEntity<String> handleCouldNotAddAuthorException(Exception exception){
        return ResponseEntity.status(HttpStatus.IM_USED).body(exception.getMessage());
    }

    @ExceptionHandler(CouldNotRemoveAuthorException.class)
    public ResponseEntity<String> handleCouldNotRemoveAuthorException(Exception exception){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.getMessage());
    }
}
