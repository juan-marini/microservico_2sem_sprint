package br.com.fiap.ms.educamais.exceptions.handler;

import br.com.fiap.ms.educamais.exceptions.DatabaseException;
import br.com.fiap.ms.educamais.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.educamais.exceptions.dto.CustomErrorDTO;
import br.com.fiap.ms.educamais.exceptions.dto.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> resourceNotFound(ResourceNotFoundException e,
                                                           HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorDTO erro = new CustomErrorDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomErrorDTO> database(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorDTO erro = new CustomErrorDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorDTO> validation(MethodArgumentNotValidException e,
                                                     HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorDTO erro = new ValidationErrorDTO(Instant.now(), status.value(), "Dados inválidos",
                request.getRequestURI());
        for (FieldError campo : e.getBindingResult().getFieldErrors()) {
            erro.addError(campo.getField(), campo.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorDTO> typeMismatch(MethodArgumentTypeMismatchException e,
                                                       HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String mensagem = "Valor inválido para o parâmetro '" + e.getName() + "': " + e.getValue();
        CustomErrorDTO erro = new CustomErrorDTO(Instant.now(), status.value(), mensagem, request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorDTO> notReadable(HttpMessageNotReadableException e,
                                                      HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorDTO erro = new CustomErrorDTO(Instant.now(), status.value(),
                "Corpo da requisição inválido ou mal formatado", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorDTO> erroInesperado(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        CustomErrorDTO erro = new CustomErrorDTO(Instant.now(), status.value(),
                "Erro inesperado no servidor", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }
}
