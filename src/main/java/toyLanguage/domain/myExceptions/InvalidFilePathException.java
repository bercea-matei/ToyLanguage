package toyLanguage.domain.myExceptions;

public class InvalidFilePathException extends ToyLanguageExceptions {
    public InvalidFilePathException() {
        super("The provided file path is invalid");
    }
}
