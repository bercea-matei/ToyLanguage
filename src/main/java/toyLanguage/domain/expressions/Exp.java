package toyLanguage.domain.expressions;

import toyLanguage.domain.values.Value;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.types.Type;

public interface Exp {
    public Value eval(MyDict<String,Value> tbl, MyHeap<Integer,Value> hp) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException;
    public Exp deepCopy();
    public Type typecheck(MyDict<String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend;
}
