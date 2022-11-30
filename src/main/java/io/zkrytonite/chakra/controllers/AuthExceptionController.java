package io.zkrytonite.chakra.controllers;

import io.zkrytonite.chakra.dto.ExceptionDto;
import io.zkrytonite.chakra.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionController {
    @ExceptionHandler(ActivationMailException.class)
    public ResponseEntity<ExceptionDto> handleActivationMailException(final ActivationMailException exception) {
        return new ResponseEntity<>(ExceptionDto.builder()
                .title("Cannot send activation email").detail(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("A00001").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ExistUsernameException.class, ExistEmailException.class})
    public ResponseEntity<ExceptionDto> handleDuplicatedRegistrationInfo(final ActivationMailException exception) {
        return new ResponseEntity<>(ExceptionDto.builder()
                .title("Duplicate Registration Info").detail(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("A00002").build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionDto> handleTokenExpiredException(final TokenExpiredException exception) {
        return new ResponseEntity<>(ExceptionDto.builder()
                .title("Verification token expired").detail(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("A00003").build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleTokenNotFoundException(final TokenNotFoundException exception) {
        return new ResponseEntity<>(ExceptionDto.builder()
                .title("Token not found").detail(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .code("A00004").build(), HttpStatus.BAD_REQUEST);
    }
}
