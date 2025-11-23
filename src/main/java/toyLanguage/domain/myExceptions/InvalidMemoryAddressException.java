package toyLanguage.domain.myExceptions;

public class InvalidMemoryAddressException extends ToyLanguageExceptions {
    public InvalidMemoryAddressException() {
        super("Address must be greater than 0");
    }
}
