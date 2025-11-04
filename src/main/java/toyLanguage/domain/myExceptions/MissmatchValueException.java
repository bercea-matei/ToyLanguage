package toy_language.domain.my_exceptions;
import toy_language.domain.types.*;

public class MissmatchValueException extends ToyLanguageExceptions {
    public MissmatchValueException(Type exptd, Type got) {
        super("Expected type '" + exptd + "' but got '" + got + "'");
    }
}
