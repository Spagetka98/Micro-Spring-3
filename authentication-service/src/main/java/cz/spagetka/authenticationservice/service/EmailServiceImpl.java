package cz.spagetka.authenticationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(String subject, String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(subject);
        message.setTo(to);
        message.setText(text);

        this.emailSender.send(message);
    }

    @Override
    public void sendUserVerificationEmail(String userEmail,String userVerificationToken) {
        final String SUBJECT = "Potvrzení emailu!";
        final String TEXT = String.format("localhost:4200/verification/%s",userEmail);

        this.sendEmail(SUBJECT,userEmail,TEXT);
    }
}
