package toy_language.domain.my_exceptions;

public class EmptyListException extends ToyLanguageExceptions {
    public EmptyListException() {
        super("List is empty");
    }
}
