package cz.spagetka.authenticationservice.model.document.embedded;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Builder
public class RefreshToken {
    @Field(
            name = "Token"
    )
    private String token;

    @Field(
            name = "Expiration_date"
    )
    private Instant expirationDate;
}
