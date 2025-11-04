package toyLanguage.domain.myExceptions;

public class EmptyStackException extends ToyLanguageExceptions {
    public EmptyStackException() {
        super("Stack is empty");
    }
}
