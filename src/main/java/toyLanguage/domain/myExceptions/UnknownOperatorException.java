package toyLanguage.domain.myExceptions;

public class UnknownOperatorException extends ToyLanguageExceptions {
    public UnknownOperatorException(String op) {
        super("Unknown operator: '" + op + "'");
    }
}
