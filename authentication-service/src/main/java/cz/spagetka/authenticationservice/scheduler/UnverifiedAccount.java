package cz.spagetka.authenticationservice.scheduler;

import cz.spagetka.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class UnverifiedAccount {
    private final UserRepository userRepository;

    //@Scheduled(cron = "0 0/1 * * * ?")
    @Scheduled(cron = "0 0 6 * * ?",zone = "Europe/Prague" )
    public void deleteUnverifiedAccount(){
        this.userRepository.deleteUnVerifiedAccountsOlderThan(Instant.now());
    }
}
