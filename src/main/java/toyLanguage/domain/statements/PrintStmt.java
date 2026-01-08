package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.Type;
import toyLanguage.domain.adts.list.MyList;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.values.Value;

public class PrintStmt implements Stmt{
    private Exp exp;
    
    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }
    public PrgState execute(PrgState state) throws ToyLanguageExceptions{
        MyList<Value> outList = state.getOutList();
        MyDict<String, Value> symTable = state.getSymTable();
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();

        outList.append(exp.eval(symTable, heapTbl));
        return null;
    }
    @Override
    public Stmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}

