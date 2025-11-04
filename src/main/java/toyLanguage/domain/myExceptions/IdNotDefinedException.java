package toyLanguage.domain.myExceptions;

public class IdNotDefinedException extends ToyLanguageExceptions {
    public IdNotDefinedException(String id) {
        super("The variable '" + id + "' was not declared before.");
    }
}
