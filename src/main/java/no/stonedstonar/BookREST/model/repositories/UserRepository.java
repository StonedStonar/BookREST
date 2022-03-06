package no.stonedstonar.BookREST.model.repositories;

import no.stonedstonar.BookREST.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM `user` WHERE eMail = :em AND password = :pass", nativeQuery = true)
    Optional<User> getUserByEmailAndMatchingPassword(@Param("em") String em, @Param("pass") String pass);

    @Query(value = "SELECT count(email) FROM `user` WHERE eMail = :email", nativeQuery = true)
    int getIfEmailIsTaken(@Param(value = "email") String email);
}
