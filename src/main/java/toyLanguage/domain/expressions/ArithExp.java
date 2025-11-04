package toy_language.domain.expressions;

import toy_language.domain.expressions.operators.ArithOp;
import toy_language.domain.values.Value;
import toy_language.domain.values.IntValue;
import toy_language.domain.adts.dictionary.MyDict;
import toy_language.domain.my_exceptions.*;
import toy_language.domain.types.IntType;

public class ArithExp implements Exp{
    Exp e1;
    Exp e2;
    ArithOp op;
    
    public ArithExp(char s_op, Exp e1, Exp e2) throws UnknownOperatorException {
        this.e1 = e1;
        this.e2 = e2;
        switch (s_op) {
            case '+':
                this.op = ArithOp.ADD;
                break;
            case '-':
                this.op = ArithOp.SUBTRACT;
                break;
            case '*':
                this.op = ArithOp.MULTIPLY;
                break;
            case '/':
                this.op = ArithOp.DIVIDE;
                break;
            default:
                throw new UnknownOperatorException(String.valueOf(s_op));
        }
    }
    @Override
    public String toString() {
        return this.e1.toString() + " " + this.op.toString() + " " + this.e2.toString();
    }
    @Override
    public Value eval(MyDict<String,Value> tbl) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException{
        Value v1,v2;
        v1= e1.eval(tbl);
        if (v1.getType().equals(new IntType())) {
                v2 = e2.eval(tbl);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getValue();
                n2 = i2.getValue();
                switch (op) {
                    case ADD:
                        return new IntValue(n1+n2);
                    case SUBTRACT:
                        return new IntValue(n1-n2);
                    case MULTIPLY:
                        return new IntValue(n1*n2);
                    case DIVIDE:
                        if (n2 == 0) {throw new DivisionByZeroException();};
                        return new IntValue(n1/n2);
                    default:
                        throw new UnknownOperatorException(op.toString());
                }
            }else
                throw new MissmatchValueException(new IntType(), v2.getType());
        } else {
            throw new MissmatchValueException(new IntType(), v1.getType());
        }
    }
    @Override
    public Exp deepCopy(){
        String c_ = this.op.toString();
        try {
            return new ArithExp(c_.charAt(0), this.e1.deepCopy(), this.e2);
        } catch (UnknownOperatorException e) {
            //should never happen
            throw new AssertionError("An impossible error occurred during deep copy: " + e.getMessage(), e);
        }
    }


}
