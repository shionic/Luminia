package luminia.backend.exceptions;

public class NotFoundException extends LuminiaException {

    public NotFoundException() {
        super("Entity not Found", 1, 404);
    }

    public NotFoundException(String entityName) {
        super(entityName.concat(" not found"), 1, 404);
    }
}
