package luminia.backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import luminia.backend.configurations.LuminiaControllerAdvice;

@Getter
public abstract class LuminiaException extends RuntimeException {
    private int code;
    private int httpCode;

    public LuminiaException(String message, int code, int httpCode) {
        super(message);
        this.code = code;
        this.httpCode = httpCode;
    }

    public LuminiaException(String message, Throwable cause, int code, int httpCode) {
        super(message, cause);
        this.code = code;
        this.httpCode = httpCode;
    }

    public LuminiaControllerAdvice.ErrorDescription getErrorDescription() {
        return new LuminiaControllerAdvice.ErrorDescription(code, getMessage());
    }
}
