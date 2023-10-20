package cz.spagetka.newsService.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record NewsRequest(
        @NotBlank
        @Size.List({
                @Size(min = 3, message = "{validation.username.size.too_small}"),
                @Size(max = 30, message = "{validation.username.size.too_long}")
        })
        @Pattern(
                regexp = "^[A-ža-ž]+$",
                message = "{validation.firstName.regexp.not_match}"
        )
        String title,
        MultipartFile thumbnail,
        @NotBlank
        @Size.List({
                @Size(min = 2, message = "{validation.username.size.too_small}"),
                @Size(max = 512, message = "{validation.username.size.too_long}")
        })
        String text
){}
