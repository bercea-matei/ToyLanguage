package toyLanguage.domain.myExceptions;

public class UnexpectedErrorOccuredException extends ToyLanguageExceptions {
    public UnexpectedErrorOccuredException(String FromWhere, String msg) {
        super(FromWhere + " had the Unexpected Error: " + msg);
    }
}
