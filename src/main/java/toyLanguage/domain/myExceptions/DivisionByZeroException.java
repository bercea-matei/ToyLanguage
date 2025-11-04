package toyLanguage.domain.myExceptions;

public class DivisionByZeroException extends ToyLanguageExceptions {
    public DivisionByZeroException() {
        super("Cannot divide by zero");
    }
}
