package toyLanguage.domain.myExceptions;

public class FileNotFoundException extends ToyLanguageExceptions {
    public FileNotFoundException(String filename, String err_msg) {
        super("The file '" + filename + "' was not found. Error: " + err_msg);
    }
}
