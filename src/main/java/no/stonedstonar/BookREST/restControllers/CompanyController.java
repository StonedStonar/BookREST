package no.stonedstonar.BookREST.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import no.stonedstonar.BookREST.model.Company;
import no.stonedstonar.BookREST.model.database.CompanyJPA;
import no.stonedstonar.BookREST.model.registers.CompanyRegister;
import no.stonedstonar.BookREST.model.exceptions.CouldNotAddCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotGetCompanyException;
import no.stonedstonar.BookREST.model.exceptions.CouldNotRemoveCompanyException;
import no.stonedstonar.BookREST.model.repositories.CompanyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @version 0.1
 * @author Steinar Hjelle Midthus
 */
@RestController
@CrossOrigin
@RequestMapping("/company")
public class CompanyController {

    private final CompanyRegister companyRegister;


    /**
      * Makes an instance of the CompanyController class.
      * @param companyRepository the company repository.
      */
    public CompanyController(CompanyRepository companyRepository){
        companyRegister = new CompanyJPA(companyRepository);
    }

    /**
     * Gets all the companies in the register.
     * @return a list with all the companies.
     */
    @GetMapping
    public List<Company> getCompanies(@RequestParam(value = "companyID", required = false)Optional<Long> opCompanyID) throws CouldNotGetCompanyException {
        List<Company> companies;
        if (opCompanyID.isPresent()){
            companies = new LinkedList<>();
            companies.add(companyRegister.getCompanyWithID(opCompanyID.get()));
        }else {
            companies = companyRegister.getAllCompanies();
        }
        return companies;
    }

    /**
     * Gets the company with the matching ID.
     * @param companyID the companies ID.
     * @return the company with that id.
     * @throws CouldNotGetCompanyException gets thrown if the company could not be found.
     */
    @GetMapping("/{companyID}")
    public Company getCompanyWithId(@PathVariable long companyID) throws CouldNotGetCompanyException {
        return companyRegister.getCompanyWithID(companyID);
    }

    /**
     * Adds a company to the register.
     * @param company the company to add.
     * @throws JsonProcessingException gets thrown when the JSON format is invalid.
     * @throws CouldNotAddCompanyException gets thrown if the company could not be added.
     */
    @PostMapping
    public void postCompany(@RequestBody Company company) throws JsonProcessingException, CouldNotAddCompanyException {
        companyRegister.addCompany(company);
    }

    /**
     * Removes a company that matches the companyID.
     * @param companyID the companyID to remove.
     * @throws CouldNotRemoveCompanyException gets thrown if the company is removed from the database.
     */
    @DeleteMapping("/{companyID}")
    public void removeCompany(@PathVariable long companyID) throws CouldNotRemoveCompanyException {
        companyRegister.removeCompanyWithID(companyID);
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
     * Handles a company that could not be added
     * @param exception the exception to handle.
     * @return the response according to the exception.
     */
    @ExceptionHandler(CouldNotAddCompanyException.class)
    public ResponseEntity<String> handleCouldNotAddCompany(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles an invalid get argument.
     * @param exception the exception to handle.
     * @return the response according to the exception.
     */
    @ExceptionHandler(CouldNotGetCompanyException.class)
    public ResponseEntity<String> handleCouldNotGetCompany(Exception exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    /**
     * Handles an invalid remove argument.
     * @param exception the exception to handle.
     * @return the response according to the exception.
     */
    @ExceptionHandler(CouldNotRemoveCompanyException.class)
    public ResponseEntity<String> handleCouldNotRemoveCompany(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
