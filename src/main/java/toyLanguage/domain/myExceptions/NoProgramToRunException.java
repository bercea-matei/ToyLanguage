package toyLanguage.domain.myExceptions;

public class NoProgramToRunException extends ToyLanguageExceptions {
    public NoProgramToRunException() {
        super("No program was selected or there are no more programs");
    }
}
