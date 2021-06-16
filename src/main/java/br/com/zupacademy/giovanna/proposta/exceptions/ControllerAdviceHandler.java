package br.com.zupacademy.giovanna.proposta.exceptions;

import br.com.zupacademy.giovanna.proposta.validations.ValidationStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@RestControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        Collection<String> mensagens = new ArrayList<>();
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            String message = String.format("Campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
            mensagens.add(message);
        });

        ErrorResponse errorResponse = new ErrorResponse(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErrorResponse> handleApiErrorException(ApiErrorException apiErrorException) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(apiErrorException.getReason());

        ErrorResponse errorResponse = new ErrorResponse(mensagens);
        return ResponseEntity.status(apiErrorException.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        String mensagem = constraintViolationException.getMessage().substring(constraintViolationException.getMessage().indexOf(":")+2);

        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        ErrorResponse errorResponse = new ErrorResponse(Arrays.asList(mensagem));

        for(ConstraintViolation violation : violations) {
            String errorCode = violation.getPropertyPath().toString().substring(violation.getPropertyPath().toString().lastIndexOf(".")+1);
            ValidationStatus validationStatus = ValidationStatus.valueOf(errorCode);
            if(validationStatus != null) {
                HttpStatus status = validationStatus.getStatus();
                return ResponseEntity.status(status).body(errorResponse);
            }

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
