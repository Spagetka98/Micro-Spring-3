package cz.spagetka.apigateway.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("gateway-prop")
public record GatewayProperties(String cookieNameJWT, String validationURI, List<String> excludedUrls) {
}
