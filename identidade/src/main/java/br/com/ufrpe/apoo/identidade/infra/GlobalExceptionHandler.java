package br.com.ufrpe.apoo.identidade.infra;

import br.com.ufrpe.apoo.identidade.dto.ErroDTO;
import br.com.ufrpe.apoo.identidade.excecao.RecursoNaoEncontradoException;
import br.com.ufrpe.apoo.identidade.excecao.RegraNegocioException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex,
            HttpServletRequest request) {
        ErroDTO error = new ErroDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroDTO> handleRegraNegocio(RegraNegocioException ex, HttpServletRequest request) {
        ErroDTO error = new ErroDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Regra de negócio violada",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> handleException(Exception ex, HttpServletRequest request) {
        ErroDTO error = new ErroDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
