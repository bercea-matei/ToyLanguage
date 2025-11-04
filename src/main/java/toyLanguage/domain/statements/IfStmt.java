package toy_language.domain.statements;

import toy_language.domain.values.BoolValue;
import toy_language.domain.values.Value;
import toy_language.domain.my_exceptions.*;
import toy_language.domain.expressions.Exp;
import toy_language.domain.prg_state.PrgState;

import toy_language.domain.adts.dictionary.MyDict;
import toy_language.domain.adts.stack.MyStack;
import toy_language.domain.types.BoolType;


public class IfStmt implements Stmt{
    Exp exp;
    Stmt thenS;
    Stmt elseS;

    public IfStmt(Exp exp, Stmt thenS, Stmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }
    @Override
    public String toString() {
        return "IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+")";
    }
    public PrgState execute(PrgState state) throws ToyLanguageExceptions{
        MyStack<Stmt> exeStk = state.getExeStk();
        MyDict<String,Value> symTbl = state.getSymTable();
        Value val = this.exp.eval(symTbl);
        if (val.getType().equals(new BoolType())) {
            BoolValue bool = (BoolValue)val;
            if (bool.getValue()) {
                exeStk.push(this.thenS);
            } else {
                exeStk.push(this.elseS);
            }
        }
        else 
            throw new MissmatchValueException(new BoolType(), val.getType());
        return state;
    }
    public Stmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }
}
