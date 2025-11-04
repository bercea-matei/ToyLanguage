package toy_language.domain.my_exceptions;

public class EmptyStackException extends ToyLanguageExceptions {
    public EmptyStackException() {
        super("Stack is empty");
    }
}
