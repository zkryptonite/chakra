package io.zkrytonite.chakra.services;

import io.zkrytonite.chakra.dto.RegisterRequest;
import io.zkrytonite.chakra.entities.NotificationEmail;
import io.zkrytonite.chakra.entities.User;
import io.zkrytonite.chakra.entities.VerificationToken;
import io.zkrytonite.chakra.exception.*;
import io.zkrytonite.chakra.repositories.TokenRepository;
import io.zkrytonite.chakra.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private MailService mailService;

    public void signup(RegisterRequest registerRequest) throws ActivationMailException, ExistUsernameException, ExistEmailException {
        if (userRepository.existsUserByUsername(registerRequest.getUsername())) {
            throw new ExistUsernameException("Username " + registerRequest.getUsername() + " existed.");
        }

        if (userRepository.existsUserByEmail(registerRequest.getEmail())) {
            throw new ExistEmailException("Email " + registerRequest.getUsername() + "existed.");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdDate(Instant.now())
                .enabled(false)
                .build();

        userRepository.save(user);

        VerificationToken verificationToken = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate Your Account",
                user.getEmail(), "Thank you for signing up to Chakra, " +
                "please click on the below link to activate your account:\n" +
                "http://localhost:8081/api/auth/verification/" + verificationToken.getToken())
        );
    }

    private VerificationToken generateVerificationToken(User user) {
        String randomToken = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(randomToken);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plus(5, ChronoUnit.MINUTES));

        tokenRepository.save(verificationToken);

        return verificationToken;
    }

    public void verifyAccount(String token) throws TokenNotFoundException, TokenExpiredException {
        Optional<VerificationToken> tokenOptional = tokenRepository.findByToken(token);
        VerificationToken verificationToken = tokenOptional
                .orElseThrow(() -> new TokenNotFoundException("Verification Token Not Found"));

        if (verificationToken.getExpiryDate().isBefore(Instant.now())) {
            throw new TokenExpiredException("Verification token expired");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);

        userRepository.save(user);
    }
}
