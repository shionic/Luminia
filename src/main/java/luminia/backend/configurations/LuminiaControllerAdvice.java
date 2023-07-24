package luminia.backend.configurations;


import lombok.extern.slf4j.Slf4j;
import luminia.backend.exceptions.LuminiaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class LuminiaControllerAdvice {

    @ExceptionHandler({ LuminiaException.class })
    public ResponseEntity<ErrorDescription> handleException(LuminiaException e) {
        return ResponseEntity.status(e.getHttpCode()).body(e.getErrorDescription());
    }

    @ExceptionHandler({ SecurityException.class })
    public ResponseEntity<ErrorDescription> handleException(SecurityException e) {
        return ResponseEntity.status(400).body(new ErrorDescription(1001, e.getMessage()));
    }

    @ExceptionHandler({ Throwable.class })
    public ResponseEntity<ErrorDescription> handleAnyException(Throwable e) {
        log.error("Unhandled Exception", e);
        return ResponseEntity.status(500).body(new ErrorDescription(1002, "Server Internal Error"));
    }

    public record ErrorDescription(int code, String message) {}
}
