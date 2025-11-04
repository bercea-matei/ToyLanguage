package toyLanguage.domain.myExceptions;

public class IndexOutOfBoundsException extends ToyLanguageExceptions {
    public IndexOutOfBoundsException(int index) {
        super("index '" + index + "' is out of bounds");
    }
}
