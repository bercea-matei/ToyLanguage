package toy_language.domain.statements;

import toy_language.domain.my_exceptions.*;
import toy_language.domain.prg_state.PrgState;
import toy_language.domain.adts.list.MyList;
import toy_language.domain.adts.dictionary.MyDict;
import toy_language.domain.expressions.Exp;
import toy_language.domain.values.Value;

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
        outList.append(exp.eval(symTable));
        return state;
    }
    public Stmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }
}

