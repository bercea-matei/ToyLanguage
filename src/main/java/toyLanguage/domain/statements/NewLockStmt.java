package toyLanguage.domain.statements;

import toyLanguage.domain.values.IntValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.Type;

public class NewLockStmt implements Stmt{
    private static int newFreeLocation = -1;
    private String var;

    public NewLockStmt(String var) {
        this.var=var;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();

        if (symTbl.isKeyDef(this.var)) {
            Type typId = (symTbl.lookup(this.var)).getType();
            if (! typId.equals(new IntType()))
                throw new MissmatchValueException(new IntType(),typId);

            synchronized (state.getLockTable()) {
                newFreeLocation++;
                state.getLockTable().add(newFreeLocation, -1);
                state.getSymTable().update(this.var, new IntValue(newFreeLocation));
            }
            return null;
        } else { 
            throw new IdNotDefinedException(this.var);
        }
        //return null;
    }

    @Override
    public String toString() {
        return "newLock(" + this.var +")";
    }
    @Override
    public Stmt deepCopy() {
        return new NewLockStmt(this.var);
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(var);
        if (typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new MissmatchTypeException(this.whatAmI() ,typevar.toString(), new IntType().toString());
    } 
    private String whatAmI() {
        return "NewLockStmt";
    }
}
