package cz.spagetka.authenticationservice.model.document;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import cz.spagetka.authenticationservice.model.document.embedded.PasswordToken;
import cz.spagetka.authenticationservice.model.document.embedded.RefreshToken;
import cz.spagetka.authenticationservice.model.document.embedded.VerificationToken;
import cz.spagetka.authenticationservice.model.enums.ERole;
import cz.spagetka.authenticationservice.model.enums.ESchemalessData;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Document(collection = "User")
@Data
@Builder
public class User implements UserDetails {
    @Id
    @Field(
            name = "UserId"
    )
    private ObjectId userId;

    @Field(
            name = "Username"
    )
    @Indexed(
            name = "username_unique_index",
            unique = true)
    @Size.List({
            @Size(min = 3, message = "{validation.username.size.too_small}"),
            @Size(max = 30, message = "{validation.username.size.too_long}")
    })
    @Pattern(
            regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9])+[a-zA-Z0-9]$",
            message = "{validation.username.regexp.not_match}"
    )
    private String username;

    @Field(
            name = "FirstName"
    )
    @Size(min = 2, message = "{validation.firstName.size.too_small}")
    @Pattern(
            regexp = "^[A-ža-ž]+$",
            message = "{validation.firstName.regexp.not_match}"
    )
    private String firstName;

    @Field(
            name = "LastName",
            targetType = FieldType.STRING,
            order = 4
    )
    @Size(min = 2, message = "{validation.lastName.size.too_small}")
    @Pattern(
            regexp = "^[A-ža-ž]+$",
            message = "{validation.lastName.regexp.not_match}"
    )
    private String lastName;

    @Field(
            name = "Email"
    )
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "{validation.email.regexp.not_match}")
    @Indexed(name = "email_unique_index", unique = true)
    private String email;

    @Field(
            name = "Password"
    )
    @NotBlank(message = "{validation.password.not_blank.empty}")
    private String password;

    @Field(
            name = "Role"
    )
    @NotNull
    private ERole role;

    @Field(
            name = "IsAccountNonExpired"
    )
    private boolean isAccountNonExpired;

    @Field(
            name = "IsAccountNonLocked"
    )
    private boolean isAccountNonLocked;

    @Field(
            name = "IsCredentialsNonExpired"
    )
    private boolean isCredentialsNonExpired;

    @Field(
            name = "IsEnabled"
    )
    private boolean isEnabled;

    @Field(
            name = "SchemalessData"
    )
    private Map<String, Object> schemalessData;

    @Version
    private Integer version;

    @JsonAnySetter
    public void add(String key, Object value) {
        if (null == schemalessData) schemalessData = new HashMap<>();

        schemalessData.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> get() {
        return schemalessData;
    }

    public Optional<String> getJWT() {
        if (this.getSchemalessData() == null || !this.getSchemalessData().containsKey(ESchemalessData.JWT.getAttributeName()))
            return Optional.empty();
        else
            return Optional.of((String) this.getSchemalessData().get(ESchemalessData.JWT.getAttributeName()));
    }

    public Optional<RefreshToken> getRefreshToken() {
        if (this.getSchemalessData() == null || !this.getSchemalessData().containsKey(ESchemalessData.REFRESH_TOKEN.getAttributeName()))
            return Optional.empty();
        else
            return Optional.of((RefreshToken) this.getSchemalessData().get(ESchemalessData.REFRESH_TOKEN.getAttributeName()));
    }

    public Optional<VerificationToken> getVerificationToken() {
        if (this.getSchemalessData() == null || !this.getSchemalessData().containsKey(ESchemalessData.VERIFICATION_TOKEN.getAttributeName()))
            return Optional.empty();
        else
            return Optional.of((VerificationToken) this.getSchemalessData().get(ESchemalessData.VERIFICATION_TOKEN.getAttributeName()));
    }

    public Optional<PasswordToken> getPasswordToken() {
        if (this.getSchemalessData() == null || !this.getSchemalessData().containsKey(ESchemalessData.PASSWORD_TOKEN.getAttributeName()))
            return Optional.empty();
        else
            return Optional.of((PasswordToken) this.getSchemalessData().get(ESchemalessData.PASSWORD_TOKEN.getAttributeName()));
    }


    public void setJWT(String jwt) {
        this.add(ESchemalessData.JWT.getAttributeName(), jwt);
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.add(ESchemalessData.REFRESH_TOKEN.getAttributeName(), refreshToken);
    }

    public void setVerificationToken(VerificationToken verificationToken) {
        this.add(ESchemalessData.VERIFICATION_TOKEN.getAttributeName(), verificationToken);
    }

    public void setPasswordToken(PasswordToken passwordToken) {
        this.add(ESchemalessData.PASSWORD_TOKEN.getAttributeName(), passwordToken);
    }

    public void removeJWT() {
        if (this.getSchemalessData() != null) {
            this.getSchemalessData().remove(ESchemalessData.JWT.getAttributeName());
        }
    }

    public void removeRefreshToken() {
        if (this.getSchemalessData() != null) {
            this.getSchemalessData().remove(ESchemalessData.REFRESH_TOKEN.getAttributeName());
        }
    }

    public void removeVerificationToken() {
        if (this.getSchemalessData() != null) {
            this.getSchemalessData().remove(ESchemalessData.VERIFICATION_TOKEN.getAttributeName());
        }
    }

    public void removePasswordToken() {
        if (this.getSchemalessData() != null) {
            this.getSchemalessData().remove(ESchemalessData.PASSWORD_TOKEN.getAttributeName());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.getRole().name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

}
