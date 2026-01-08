package toyLanguage.domain.statements;

import toyLanguage.domain.values.BoolValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.prg_state.PrgState;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.types.BoolType;
import toyLanguage.domain.types.Type;


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
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();

        Value val = this.exp.eval(symTbl, heapTbl);
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
        return null;
    }
    @Override
    public Stmt deepCopy() {
        return new IfStmt(this.exp.deepCopy(), this.thenS.deepCopy(), this.elseS.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MissmatchTypeException(new BoolType().toString(), typexp.toString());
    }
}
