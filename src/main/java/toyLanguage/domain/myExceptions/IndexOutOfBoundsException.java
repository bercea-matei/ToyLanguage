package toy_language.domain.my_exceptions;

public class IndexOutOfBoundsException extends ToyLanguageExceptions {
    public IndexOutOfBoundsException(int index) {
        super("index '" + index + "' is out of bounds");
    }
}
