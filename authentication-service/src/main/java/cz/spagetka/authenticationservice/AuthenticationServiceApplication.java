package cz.spagetka.authenticationservice;

import cz.spagetka.authenticationservice.properties.JwtProperties;
import cz.spagetka.authenticationservice.properties.MongoProperties;
import cz.spagetka.authenticationservice.properties.RefreshTokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({JwtProperties.class, MongoProperties.class, RefreshTokenProperties.class})
@EnableScheduling
public class AuthenticationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

}
