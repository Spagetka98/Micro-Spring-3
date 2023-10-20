package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {
    User getUser(@NotBlank(message = "Parameter authId cannot be null or empty!") String authId);
}
