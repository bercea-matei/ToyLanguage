package toyLanguage.domain.expressions;

import toyLanguage.domain.values.Value;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.myExceptions.*;

public interface Exp {
    public Value eval(MyDict<String,Value> tbl) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException;
    public Exp deepCopy();
}
