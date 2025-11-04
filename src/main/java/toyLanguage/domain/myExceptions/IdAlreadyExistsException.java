package toy_language.domain.my_exceptions;

public class IdAlreadyExistsException extends ToyLanguageExceptions {
    public IdAlreadyExistsException(String id) {
        super("There is a pair with the id '" + id + "'.");
    }
}
