package cz.spagetka.authenticationservice.model.document.embedded;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class VerificationToken {
    @Field(
            name = "Token"
    )
    private String token;

    @Field(
            name = "Expiration_date"
    )
    private Instant expirationDate;
}