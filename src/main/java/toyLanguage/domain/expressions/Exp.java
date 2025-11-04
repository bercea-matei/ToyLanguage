package toy_language.domain.expressions;

import toy_language.domain.values.Value;
import toy_language.domain.adts.dictionary.MyDict;
import toy_language.domain.my_exceptions.*;

public interface Exp {
    public Value eval(MyDict<String,Value> tbl) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException;
    public Exp deepCopy();
}
