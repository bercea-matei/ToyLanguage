package toyLanguage.domain.myExceptions;

public class ClosingFileException extends ToyLanguageExceptions {
    public ClosingFileException(String filename, String err_msg) {
        super("Error closing file '" + filename + "': " + err_msg);
    }
}
