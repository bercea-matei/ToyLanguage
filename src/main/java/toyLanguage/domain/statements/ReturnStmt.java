package toyLanguage.domain.statements;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.Type;

public class ReturnStmt implements Stmt {
    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        state.getSymTables().pop();
        return null;
    }
    @Override public String toString() { 
        return "return"; 
    }
    @Override public Stmt deepCopy() { 
        return new ReturnStmt(); 
    }
    @Override public MyDict<String, Type> typecheck(MyDict<String, Type> typeEnv) {
        return typeEnv; 
    }
}
