package toyLanguage.domain.myExceptions;
import toyLanguage.domain.types.*;

public class MissmatchTypeException extends ToyLanguageExceptions {
    public MissmatchTypeException(String FromWhere,Type exptd, Type got) {
        super(FromWhere + " had the error: Expected type '" + exptd + "' but got '" + got + "'");
    }
    public MissmatchTypeException(String FromWhere,String exptd, String got) {
        super(FromWhere + " had the error: Expected type '" + exptd + "' but got '" + got + "'");
    }
}
