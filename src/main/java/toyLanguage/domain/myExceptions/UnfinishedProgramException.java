package toyLanguage.domain.myExceptions;

public class UnfinishedProgramException extends ToyLanguageExceptions {
    public UnfinishedProgramException() {
        super("You must finish the current program before initializing a new one");
    }
}
