package cz.spagetka.authenticationservice.model.enums;

public enum ESchemalessData {
    JWT("Jwt"),REFRESH_TOKEN("RefreshToken"),VERIFICATION_TOKEN("VerificationToken");
    private final String attributeName;

    public String getAttributeName() {
        return attributeName;
    }

    ESchemalessData(String attributeName) {
        this.attributeName = attributeName;
    }
}
