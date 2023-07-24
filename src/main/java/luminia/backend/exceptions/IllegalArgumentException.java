package luminia.backend.exceptions;

public class IllegalArgumentException extends LuminiaException {
    public IllegalArgumentException(String message) {
        super(message, 10, 400);
    }
}
