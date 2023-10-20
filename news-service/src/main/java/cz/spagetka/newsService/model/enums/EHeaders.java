package cz.spagetka.newsService.model.enums;

public enum EHeaders {
    USERNAME("Username"),
    USER_ID("UserID"),
    ROLE("Role"),
    EMAIL("Email"),
    IS_ACCOUNT_NON_EXPIRED("isAccountNonExpired"),
    IS_ACCOUNT_NON_LOCKED("isAccountNonLocked"),
    IS_CREDENTIALS_NON_EXPIRED("isCredentialsNonExpired"),
    IS_ENABLED("isEnabled");

    private final String value;

    EHeaders(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
