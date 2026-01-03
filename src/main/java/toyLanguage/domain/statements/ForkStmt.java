package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.types.Type;

public class ForkStmt implements Stmt{
    private Stmt stmt;

    public ForkStmt(Stmt s) {
        this.stmt = s;
    }
    @Override
    public String toString() {
        return " fork("+ this.stmt.toString() + ") ";
    }
    @Override
    public PrgState execute(PrgState parentState) throws ToyLanguageExceptions {

        PrgState childState = new PrgState(
            parentState.getOriginal(),
            parentState.getExeStk().deepCopy(),
            parentState.getSymTable().deepCopy(),
            parentState.getOutList().deepCopy(),
            parentState.getFileTable().deepCopy(),
            parentState.getHeapTable().deepCopy()
        );

        return childState;
    }
    @Override
    public Stmt deepCopy() {
        return new ForkStmt(this.stmt.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        this.stmt.typecheck(typeEnv);
        return typeEnv;
    }
}
