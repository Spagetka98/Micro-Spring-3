package cz.spagetka.apigateway.model.enums;

public enum EHeaders {
    AUTHORIZATION("Authorization"),
    USERNAME("Username"),
    USER_ID("UserID"),
    ROLES("Roles"),
    CONTENT_TYPE("Content-Type");

    private final String headerName;

    EHeaders(String value) {
        this.headerName = value;
    }

    public String getHeaderName(){
        return this.headerName;
    }
}
