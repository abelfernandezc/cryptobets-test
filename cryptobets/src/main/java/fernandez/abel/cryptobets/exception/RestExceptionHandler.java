package fernandez.abel.cryptobets.exception;

import fernandez.abel.cryptobets.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response> unknownException(Exception ex) {
        log.error("unknownException", ex.getMessage());
        return new ResponseEntity<Response>(new Response("error-0001", ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response> badRequestException(BadRequestException ex) {
        log.error("badRequestException", ex.getMessage());
        return new ResponseEntity<>(new Response("error-0002", ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Response> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            log.error("Arguments are not valid", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return new ResponseEntity<>(new Response("", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        log.error("Arguments are not valid");
        return new ResponseEntity<>(new Response("error-0003", "Arguments are not valid. " + ex.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotBetValidException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ResponseEntity<Response> notBetValidException(NotBetValidException ex) {
        log.error("Not bet valid. {}", ex.getMessage());
        return new ResponseEntity<>(new Response("error-0004", ex.getMessage()), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
    }
}
