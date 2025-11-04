package toy_language.domain.my_exceptions;

public class InvalidInstructionException extends ToyLanguageExceptions {
    public InvalidInstructionException(String msg) {
        super(msg);
    }
}
