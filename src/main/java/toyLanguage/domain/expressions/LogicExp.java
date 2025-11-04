package toy_language.domain.expressions;

import toy_language.domain.expressions.operators.LogicOp;
import toy_language.domain.values.Value;
import toy_language.domain.values.BoolValue;
import toy_language.domain.adts.dictionary.MyDict;
import toy_language.domain.my_exceptions.*;
import toy_language.domain.types.BoolType;

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
    public Value eval(MyDict<String,Value> tbl) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException{
        Value v1,v2;
        v1= e1.eval(tbl);
        if (v1.getType().equals(new BoolType())) {
                v2 = e2.eval(tbl);
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
    public Exp deepCopy() {
        //this.op is an enum => imutable
        return new LogicExp(this.e1.deepCopy(), this.e2.deepCopy(), this.op);
    }
}
