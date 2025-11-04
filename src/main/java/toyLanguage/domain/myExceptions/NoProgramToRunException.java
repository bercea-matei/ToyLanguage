package toy_language.domain.my_exceptions;

public class NoProgramToRunException extends ToyLanguageExceptions {
    public NoProgramToRunException() {
        super("No program was selected or there are no more programs");
    }
}
