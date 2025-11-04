package toy_language.domain.my_exceptions;

public class DivisionByZeroException extends ToyLanguageExceptions {
    public DivisionByZeroException() {
        super("Cannot divide by zero");
    }
}
