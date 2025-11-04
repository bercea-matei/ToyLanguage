package toyLanguage.domain.myExceptions;

public class EmptyListException extends ToyLanguageExceptions {
    public EmptyListException() {
        super("List is empty");
    }
}
