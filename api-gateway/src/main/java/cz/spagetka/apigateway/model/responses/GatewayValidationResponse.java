package cz.spagetka.apigateway.model.responses;

import java.util.Set;

public record GatewayValidationResponse(String username, String userID, Set<String> roles) {
}
