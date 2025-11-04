package toyLanguage.domain.myExceptions;

public class FinishUnexistentStateException extends ToyLanguageExceptions {
    public FinishUnexistentStateException() {
        super("Cannot finish a state that does not exist");
    }
}
