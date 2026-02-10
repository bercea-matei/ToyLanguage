package toyLanguage.domain.myExceptions;


public class IdNotFoundException extends ToyLanguageExceptions {
    public IdNotFoundException(String id) {
        super("There is no pair with the id '" + id + "'");
    }
}
