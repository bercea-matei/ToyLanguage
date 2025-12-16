package toyLanguage.domain.myExceptions;

public class TypeCheckFailedException extends ToyLanguageExceptions {
    public TypeCheckFailedException(Exception excp) {
        super("Type Check failed with error: " + excp.getMessage());
    }
}
