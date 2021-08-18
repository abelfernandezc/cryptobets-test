package fernandez.abel.cryptobets.exception;

import lombok.Getter;

@Getter
public class NotBetValidException extends RuntimeException {

    public NotBetValidException(String message) {
        super(message);
    }
}
