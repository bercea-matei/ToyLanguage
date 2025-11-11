package toyLanguage.domain.myExceptions;
import toyLanguage.domain.types.*;

public class MissEvaluationException extends ToyLanguageExceptions {
    public MissEvaluationException(Type exptd, Type got) {
        super("The Expression evaluated to '" + got + "' instead of '" + exptd + "'");
    }
    public MissEvaluationException(String exptd, String got) {
        super("The Expression evaluated to '" + got + "' instead of '" + exptd + "'");
    }
}
