package toyLanguage.domain.expressions;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.expressions.operators.RelOp;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.values.BoolValue;
import toyLanguage.domain.values.IntValue;
import toyLanguage.domain.values.Value;

public class RelExp implements Exp {
    private final Exp e1;
    private final Exp e2;
    private final RelOp op;

    public RelExp(String s_op, Exp e1, Exp e2) throws UnknownOperatorException{
        this.e1 = e1;
        this.e2 = e2;
        switch (s_op) {
            case "<":
                this.op = RelOp.LT;
                break;
            case "<=":
                this.op = RelOp.LTE;
                break;
            case "==":
                this.op = RelOp.EQ;
                break;
            case "!=":
                this.op = RelOp.NEQ;
                break;
            case ">":
                this.op = RelOp.GT;
                break;
            case ">=":
                this.op = RelOp.GTE;
                break;
            default:
                throw new UnknownOperatorException("Invalid relational operator: " + s_op);
            }
        }
    @Override
    public String toString() {
        return "(" + e1.toString() + " " + op.toString() + " " + e2.toString() + ")";
    }

    @Override
    public Exp deepCopy() {
        try {
            return new RelExp(op.toString(), e1.deepCopy(), e2.deepCopy());
        } catch (UnknownOperatorException e) {
            throw new AssertionError("An impossible error occurred during deep copy: " + e.getMessage(), e);
        }
    }

    @Override
    public Value eval(MyDict<String, Value> tbl) throws IdNotFoundException, IdAlreadyExistsException, MissmatchValueException, UnknownOperatorException, DivisionByZeroException{
        Value v1 = e1.eval(tbl);

        if (!v1.getType().equals(new IntType())) {
            throw new MissmatchValueException(new IntType(), v1.getType());
        }

        Value v2 = e2.eval(tbl);

        if (!v2.getType().equals(new IntType())) {
            throw new MissmatchValueException(new IntType(), v2.getType());
        }

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;

        int n1 = i1.getValue();
        int n2 = i2.getValue();

        boolean result;
        switch (op) {
            case LT:
                result = n1 < n2;
                break;
            case LTE:
                result = n1 <= n2;
                break;
            case EQ:
                result = n1 == n2;
                break;
            case NEQ:
                result = n1 != n2;
                break;
            case GT:
                result = n1 > n2;
                break;
            case GTE:
                result = n1 >= n2;
                break;
            default:
                throw new UnknownOperatorException("Unknown relational operator: " + op);
        }
        return new BoolValue(result);
    }
}
