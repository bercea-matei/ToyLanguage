package toy_language.domain.statements;

import toy_language.domain.my_exceptions.ToyLanguageExceptions;
import toy_language.domain.prg_state.PrgState;

public interface Stmt{
    public PrgState execute(PrgState state) throws ToyLanguageExceptions;
    public Stmt deepCopy();
}


