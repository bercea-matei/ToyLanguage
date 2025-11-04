package toyLanguage.domain.myExceptions;

/*
 * low level exceptions
 * needs to be caught and rethrown as IdNotDefinedException
 */
public class IdNotFoundException extends ToyLanguageExceptions {
    public IdNotFoundException(String id) {
        super("There is no pair with the id '" + id + "'");
    }
}
