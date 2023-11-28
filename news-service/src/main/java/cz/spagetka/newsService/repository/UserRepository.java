package cz.spagetka.newsService.repository;

import cz.spagetka.newsService.model.db.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByAuthId(@NotNull String authId);
}
