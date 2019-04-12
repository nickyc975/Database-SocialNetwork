package exceptions;

public class InvalidDataException extends Exception {
    private static final long serialVersionUID = 99878912987503L;

    public InvalidDataException(String invalidData, String detail) {
        super("Invalid data: \"" + invalidData + "\"\n" + detail);
    }
}