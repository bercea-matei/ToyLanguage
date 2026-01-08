package toyLanguage.domain.statements;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.Type;

public class NOP implements Stmt{

    @Override
    public String toString() {
        return "NOP";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        return null;
    }
    @Override
    public Stmt deepCopy() {
        return new NOP();
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        return typeEnv;
    }
}
