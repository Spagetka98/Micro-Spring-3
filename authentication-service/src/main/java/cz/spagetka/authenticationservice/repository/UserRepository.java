package cz.spagetka.authenticationservice.repository;

import cz.spagetka.authenticationservice.model.document.User;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = "{'SchemalessData.RefreshToken.Token': ?0 }")
    Optional<User> findByRefreshToken(String refreshToken);

    @Query(value = "{'SchemalessData.VerificationToken.Token': ?0 }")
    Optional<User> findByVerificationToken(String verificationToken);

    @DeleteQuery(value = "{'SchemalessData.VerificationToken.Expiration_date': {'$lt': ?0 },'IsEnabled': false}")
    void deleteUnVerifiedAccountsOlderThan(Instant date);

    @Query(value = "{'SchemalessData.PasswordToken.Token': ?0 }")
    Optional<User> findByPasswordToken(String passwordToken);

}
