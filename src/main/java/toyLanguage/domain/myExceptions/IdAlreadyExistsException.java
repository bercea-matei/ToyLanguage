package toyLanguage.domain.myExceptions;

public class IdAlreadyExistsException extends ToyLanguageExceptions {
    public IdAlreadyExistsException(String id) {
        super("There is a pair with the id '" + id + "'.");
    }
}
