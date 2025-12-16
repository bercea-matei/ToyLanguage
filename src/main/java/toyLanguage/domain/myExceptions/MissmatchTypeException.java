package toyLanguage.domain.myExceptions;
import toyLanguage.domain.types.*;

public class MissmatchTypeException extends ToyLanguageExceptions {
    public MissmatchTypeException(Type exptd, Type got) {
        super("Expected type '" + exptd + "' but got '" + got + "'");
    }
    public MissmatchTypeException(String exptd, String got) {
        super("Expected type '" + exptd + "' but got '" + got + "'");
    }
}
