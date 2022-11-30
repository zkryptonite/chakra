package io.zkrytonite.chakra.controllers;

import io.zkrytonite.chakra.dto.RegisterRequest;
import io.zkrytonite.chakra.exception.*;
import io.zkrytonite.chakra.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest)
            throws ActivationMailException, ExistEmailException, ExistUsernameException {
        authService.signup(registerRequest);
        return ResponseEntity.ok("User Registration Successful!!!");
    }

    @GetMapping("/verification/{token}")
    public ResponseEntity<String> verifyAccount(final @PathVariable String token) throws TokenExpiredException, TokenNotFoundException {
        authService.verifyAccount(token);
        return ResponseEntity.ok("Verify Account Successful!!!");
    }


}
