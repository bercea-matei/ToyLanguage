package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;

public class NOP implements Stmt{

    @Override
    public String toString() {
        return "NOP";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        return state;
    }
    public Stmt deepCopy() {
        return new NOP();
    }
}
