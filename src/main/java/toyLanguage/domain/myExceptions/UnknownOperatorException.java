package toy_language.domain.my_exceptions;

public class UnknownOperatorException extends ToyLanguageExceptions {
    public UnknownOperatorException(String op) {
        super("Unknown operator: '" + op + "'");
    }
}
