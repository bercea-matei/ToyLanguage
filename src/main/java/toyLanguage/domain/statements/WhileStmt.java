package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.BoolType;
import toyLanguage.domain.values.BoolValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.expressions.Exp;

public class WhileStmt implements Stmt{
    private Exp expression;
    private Stmt statement;
    
    public WhileStmt(Exp expression, Stmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "( while "+expression.toString() + statement.toString()+" )";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyStack<Stmt> exeStk = state.getExeStk();
        MyDict<String,Value> symTbl = state.getSymTable();
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();

        Value val = this.expression.eval(symTbl, heapTbl);
        if (val.getType().equals(new BoolType())) {
            BoolValue bool = (BoolValue)val;
            if (bool.getValue()) {
                exeStk.push(this);
                exeStk.push(this.statement);
                return state;
            }
        }
        else 
            throw new MissmatchValueException(new BoolType(), val.getType());
        return state;
    }
    public Stmt deepCopy() {
        return new WhileStmt(this.expression.deepCopy(), this.statement.deepCopy());
    }
}   
