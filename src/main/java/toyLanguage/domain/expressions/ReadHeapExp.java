package toyLanguage.domain.expressions;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.types.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;

public class ReadHeapExp implements Exp{
    private Exp expression;

    public ReadHeapExp(Exp expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return " rH("+ this.expression.toString() + ")";
    }

    @Override
    public Value eval(MyDict<String,Value> tbl, MyHeap<Integer,Value> hp) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException {

        Value val = this.expression.eval(tbl,hp);
        if (! (val.getType() instanceof RefType))
            throw new MissmatchValueException("RefType", val.getType().toString());

        int addr = ((RefValue)val).getAddr();
        if(! hp.isKeyDef(addr))
            throw new IdNotFoundException(String.valueOf(addr));

        return hp.lookup(addr);
    }

    public Exp deepCopy() {
        return new ReadHeapExp(this.expression.deepCopy());
    }
}
