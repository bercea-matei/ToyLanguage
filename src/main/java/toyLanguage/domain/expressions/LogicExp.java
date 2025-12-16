package toyLanguage.domain.expressions;

import toyLanguage.domain.expressions.operators.LogicOp;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.values.BoolValue;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.types.BoolType;
import toyLanguage.domain.types.Type;

public class LogicExp implements Exp{
    Exp e1;
    Exp e2;
    LogicOp op;
    
    public LogicExp(Exp e1, Exp e2, LogicOp op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public String toString() {
        return this.e1.toString() + " " + this.op.toString() + " " + this.e2.toString();
    }
    @Override
    public Value eval(MyDict<String,Value> tbl, MyHeap<Integer, Value> hp) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException{
        Value v1,v2;
        v1= e1.eval(tbl, hp);
        if (v1.getType().equals(new BoolType())) {
                v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue)v1;
                BoolValue b2 = (BoolValue)v2;
                boolean n1,n2;
                n1= b1.getValue();
                n2 = b2.getValue();
                switch (op) {
                    case AND:
                        return new BoolValue(n1 && n2);
                    case OR:
                        return new BoolValue(n1 || n2);
                    default:
                        throw new UnknownOperatorException(op.toString());
                }
            }else
                throw new MissmatchValueException(new BoolType(), v2.getType());
        } else {
            throw new MissmatchValueException(new BoolType(), v1.getType());
        }
    }
    @Override
    public Exp deepCopy() {
        //this.op is an enum => imutable
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }
    @Override
    public Type typecheck(MyDict<String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend {
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
                throw new WhichOperandExceptionExtend(2, new MissmatchTypeException(new BoolType().toString(), typ2.toString()));
        }else
            throw new WhichOperandExceptionExtend(1, new MissmatchTypeException(new BoolType().toString(), typ1.toString()));
    }
}
