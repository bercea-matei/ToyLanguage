package toyLanguage.domain.myExceptions;
import toyLanguage.domain.types.*;

public class MissmatchValueException extends ToyLanguageExceptions {
    public MissmatchValueException(Type exptd, Type got) {
        super("Expected type '" + exptd + "' but got '" + got + "'");
    }
}
