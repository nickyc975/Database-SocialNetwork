package exceptions;

public class InvalidDataException extends Exception {
    public InvalidDataException(String invalidData, String detail) {
        super("Invalid data: \"" + invalidData + "\"\n" + detail);
    }
}