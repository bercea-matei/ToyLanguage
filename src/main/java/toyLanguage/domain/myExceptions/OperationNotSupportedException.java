package toyLanguage.domain.myExceptions;

public class OperationNotSupportedException extends ToyLanguageExceptions {
    public OperationNotSupportedException(String operation, String dataTypeName) {
        super("The operation '" + operation + "' is not supported for the object" + dataTypeName);
    }
}
