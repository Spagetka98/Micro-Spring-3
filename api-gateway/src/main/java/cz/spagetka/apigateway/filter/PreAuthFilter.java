package cz.spagetka.apigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.spagetka.apigateway.model.enums.EHeaders;
import cz.spagetka.apigateway.model.properties.GatewayProperties;
import cz.spagetka.apigateway.model.responses.AuthExceptionResponse;
import cz.spagetka.apigateway.model.responses.GatewayValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class PreAuthFilter extends AbstractGatewayFilterFactory<PreAuthFilter.Config> {
    private final WebClient.Builder webClientBuilder;
    private final GatewayProperties gatewayProperties;

    @Autowired
    public PreAuthFilter(GatewayProperties gatewayProperties, WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.gatewayProperties = gatewayProperties;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String bearerToken = request.getHeaders().getFirst(EHeaders.AUTHORIZATION.getHeaderName());

            if (gatewayProperties.excludedUrls().stream().noneMatch(uri -> request.getURI().getPath().contains(uri))) {
                return webClientBuilder.build().get()
                        .uri(gatewayProperties.validationURI())
                        .header(EHeaders.AUTHORIZATION.getHeaderName(), bearerToken)
                        .retrieve().bodyToMono(GatewayValidationResponse.class)
                        .map(response -> {
                            exchange.getRequest().mutate().header(EHeaders.USER_ID.getHeaderName(), response.userId());
                            exchange.getRequest().mutate().header(EHeaders.USERNAME.getHeaderName(), response.email());
                            exchange.getRequest().mutate().header(EHeaders.EMAIL.getHeaderName(), response.username());
                            exchange.getRequest().mutate().header(EHeaders.ROLE.getHeaderName(), response.userRole());
                            exchange.getRequest().mutate().header(EHeaders.IS_ACCOUNT_NON_EXPIRED.getHeaderName(), String.valueOf(response.isAccountNonExpired()));
                            exchange.getRequest().mutate().header(EHeaders.IS_ACCOUNT_NON_LOCKED.getHeaderName(), String.valueOf(response.isAccountNonLocked()));
                            exchange.getRequest().mutate().header(EHeaders.IS_CREDENTIALS_NON_EXPIRED.getHeaderName(), String.valueOf(response.isCredentialsNonExpired()));
                            exchange.getRequest().mutate().header(EHeaders.IS_ENABLED.getHeaderName(), String.valueOf(response.isEnabled()));

                            return exchange;
                        }).flatMap(chain::filter).onErrorResume(error -> {
                            HttpStatusCode errorCode;
                            String errorMsg;

                            if (error instanceof WebClientResponseException webClientException) {
                                errorCode = webClientException.getStatusCode();
                                errorMsg = webClientException.getStatusText();
                            } else {
                                errorCode = HttpStatus.BAD_GATEWAY;
                                errorMsg = HttpStatus.BAD_GATEWAY.getReasonPhrase();
                            }
//
                            return onError(exchange, errorCode, errorMsg);
                        });
            }

            return chain.filter(exchange);
        };

    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatusCode httpStatus, String errorMsg) {
        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        try {
            ObjectMapper objMapper = new ObjectMapper();

            response.getHeaders().add(EHeaders.CONTENT_TYPE.getHeaderName(), "application/json");

            AuthExceptionResponse data = new AuthExceptionResponse(httpStatus.value(), errorMsg, Instant.now().toString());

            byte[] byteData = objMapper.writeValueAsBytes(data);
            return response.writeWith(Mono.just(byteData).map(dataBufferFactory::wrap));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response.setComplete();
    }

    public static class Config {
    }
}
