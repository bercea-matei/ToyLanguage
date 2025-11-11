package toyLanguage.domain.myExceptions;

public class FileAlreadyOpenException extends ToyLanguageExceptions {
    public FileAlreadyOpenException(String file) {
        super("The file '" + file + "' is already open.");
    }
}
