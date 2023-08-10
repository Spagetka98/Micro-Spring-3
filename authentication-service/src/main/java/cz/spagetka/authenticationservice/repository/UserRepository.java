package cz.spagetka.authenticationservice.repository;

import cz.spagetka.authenticationservice.model.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    @Query(value = "{'SchemalessData.RefreshToken.Token': { $exists : ?0 }}")
    Optional<User> findByRefreshToken(String refreshToken);
}
