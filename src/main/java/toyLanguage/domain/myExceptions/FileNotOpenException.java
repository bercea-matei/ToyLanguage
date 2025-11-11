package toyLanguage.domain.myExceptions;

public class FileNotOpenException extends ToyLanguageExceptions {
    public FileNotOpenException(String file) {
        super("The file '" + file + "' is not open.");
    }
}
