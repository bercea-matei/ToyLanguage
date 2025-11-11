package toyLanguage.domain.myExceptions;

public class IOFileException extends ToyLanguageExceptions {
    public IOFileException(String filename, String err_msg) {
        super("Error reading from file '" + filename + "': " + err_msg);
    }
}
