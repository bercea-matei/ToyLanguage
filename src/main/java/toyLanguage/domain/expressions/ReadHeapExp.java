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
    @Override
    public Exp deepCopy() {
        return new ReadHeapExp(this.expression.deepCopy());
    }
    @Override
    public Type typecheck(MyDict<String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend {
        Type typ= this.expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else {
            throw new MissmatchTypeException("RefType", typ.toString());
        }
    }
    
}
