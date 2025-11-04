package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.adts.list.MyList;
import toyLanguage.domain.adts.dictionary.MyDict;
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
        outList.append(exp.eval(symTable));
        return state;
    }
    public Stmt deepCopy() {
        return new PrintStmt(this.exp.deepCopy());
    }
}

