package models.exceptions;

// Unchecked Exception
public class InventoryFullException extends RuntimeException {
    public InventoryFullException(String message) {
        super(message);
    }
}
