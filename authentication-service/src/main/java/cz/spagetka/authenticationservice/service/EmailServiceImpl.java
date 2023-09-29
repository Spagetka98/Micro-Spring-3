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
        final String TEXT = String.format("Potvrzení provedete zde: http://localhost:4200/verification/%s",userVerificationToken);

        this.sendEmail(SUBJECT,userEmail,TEXT);
    }

    @Override
    public void sendPasswordResetEmail(String userEmail, String passwordToken) {
        final String SUBJECT = "Reset hesla!";
        final String TEXT = String.format("Reset hesla provedete zde: http://localhost:4200/resetPassword/%s",passwordToken);

        this.sendEmail(SUBJECT,userEmail,TEXT);
    }
}
