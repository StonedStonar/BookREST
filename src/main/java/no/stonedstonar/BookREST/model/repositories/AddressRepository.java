package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
}
