package toyLanguage.domain.expressions;

import toyLanguage.domain.myExceptions.IdNotDefinedException;
import toyLanguage.domain.myExceptions.IdNotFoundException;
import toyLanguage.domain.myExceptions.MissmatchTypeException;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.values.*;
import toyLanguage.domain.types.Type;

public class ValueExp implements Exp{
    Value val;
    public ValueExp(Value val) {
        this.val = val;
    }

    @Override
    public Value eval(MyDict<String,Value> tbl, MyHeap<Integer, Value> hp) throws IdNotFoundException {
        return val;
    }
    @Override
    public String toString() {
        return this.val.toString();
    }
    @Override
    public Exp deepCopy() {
        return new ValueExp(this.val.deepCopy());
    }
    @Override
    public Type typecheck(MyDict<String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException {
        return val.getType();
    }
}
