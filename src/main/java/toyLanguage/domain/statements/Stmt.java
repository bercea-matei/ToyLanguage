package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.prg_state.PrgState;

public interface Stmt{
    public PrgState execute(PrgState state) throws ToyLanguageExceptions;
    public Stmt deepCopy();
}


