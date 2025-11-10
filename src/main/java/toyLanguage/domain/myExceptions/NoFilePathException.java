package toyLanguage.domain.myExceptions;

public class NoFilePathException extends ToyLanguageExceptions {
    public NoFilePathException() {
        super("A file path must be provided.");
    }
}
