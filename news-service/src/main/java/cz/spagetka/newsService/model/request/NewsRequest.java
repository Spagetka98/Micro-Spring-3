package cz.spagetka.newsService.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record NewsRequest(
        @NotBlank
        @Size.List({
                @Size(min = 3, message = "The username must have more than 3 characters !"),
                @Size(max = 30, message = "The username must have less than 30 characters !")
        })
        @Pattern(
                regexp = "^[A-ža-ž!? ]+$",
                message = "Title must have only letters!"
        )
        String title,
        @NotBlank
        @Size.List({
                @Size(min = 2, message = "The text must have more than 2 characters !"),
                @Size(max = 512, message = "The text must have less than 512 characters !")
        })
        String text
){}
