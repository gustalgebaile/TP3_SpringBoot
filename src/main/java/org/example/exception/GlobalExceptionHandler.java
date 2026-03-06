package org.example.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erros do @Valid nos DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.toList());
        return construirErro("Solicitação inválida", detalhes, HttpStatus.BAD_REQUEST);
    }

    // Erros do @Validated nos RequestParams (paginação)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> detalhes = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());
        return construirErro("Solicitação inválida", detalhes, HttpStatus.BAD_REQUEST);
    }

    // Erro ao enviar Enum inválido
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        return construirErro("Solicitação inválida", List.of("Valor não reconhecido ou formato incorreto. Verifique as classes e espécies permitidas."), HttpStatus.BAD_REQUEST);
    }

    // Erro de Tipo na URL
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return construirErro("Solicitação inválida", List.of("Parâmetro '" + ex.getName() + "' inválido."), HttpStatus.BAD_REQUEST);
    }

    // Erro customizado de Aventureiro não encontrado
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(RecursoNaoEncontradoException ex) {
        return construirErro("Recurso não encontrado", List.of(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Map<String, Object>> construirErro(String mensagem, List<String> detalhes, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("mensagem", mensagem);
        body.put("detalhes", detalhes);
        return ResponseEntity.status(status).body(body);
    }
}

