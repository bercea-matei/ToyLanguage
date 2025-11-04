package toy_language.domain.my_exceptions;

public class FinishUnexistentStateException extends ToyLanguageExceptions {
    public FinishUnexistentStateException() {
        super("Cannot finish a state that does not exist");
    }
}
