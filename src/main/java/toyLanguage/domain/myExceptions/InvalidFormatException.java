package toyLanguage.domain.myExceptions;

public class InvalidFormatException extends ToyLanguageExceptions {
    public InvalidFormatException(String filename) {
        super("The line read from file '" + filename + "' is not a valid integer.");
    }
}
