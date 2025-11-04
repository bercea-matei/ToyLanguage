package toy_language.domain.my_exceptions;

public class IdNotDefinedException extends ToyLanguageExceptions {
    public IdNotDefinedException(String id) {
        super("The variable '" + id + "' was not declared before.");
    }
}
