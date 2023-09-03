package cz.spagetka.apigateway.model.enums;

public enum EHeaders {
    USERNAME("Username"),
    USER_ID("UserID"),
    ROLE("Role"),
    CONTENT_TYPE("Content-Type"),
    EMAIL("Email"),
    IS_ACCOUNT_NON_EXPIRED("isAccountNonExpired"),
    IS_ACCOUNT_NON_LOCKED("isAccountNonLocked"),
    IS_CREDENTIALS_NON_EXPIRED("isCredentialsNonExpired"),
    IS_ENABLED("isEnabled");

    private final String headerName;

    EHeaders(String value) {
        this.headerName = value;
    }

    public String getHeaderName(){
        return this.headerName;
    }
}
