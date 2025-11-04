package toy_language.domain.statements;

import toy_language.domain.my_exceptions.*;
import toy_language.domain.prg_state.PrgState;

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
