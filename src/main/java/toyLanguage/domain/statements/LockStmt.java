package toyLanguage.domain.statements;

import toyLanguage.domain.values.IntValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.Type;

public class LockStmt implements Stmt{
    private String id;

    public LockStmt(String id) {
        this.id=id;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();
        MyDict<Integer, Integer> lockTbl = state.getLockTable();


        if (symTbl.isKeyDef(this.id)) {
            Value foundIndex = symTbl.lookup(this.id);
            Type typId = (foundIndex).getType();
            if (! typId.equals(new IntType()))
                throw new MissmatchValueException(new IntType(),typId);

            Integer fndIdx = ((IntValue) foundIndex).getValue();
            synchronized (lockTbl) {
                
                if (! lockTbl.isKeyDef(fndIdx))
                    throw new IdNotFoundException(fndIdx.toString());
                
                Integer lockVal = lockTbl.lookup(fndIdx);
                if ( -1 == lockVal)
                    lockTbl.update(fndIdx, state.getID());
                else
                    state.getExeStk().push(this);
            }
            return null;
            
        } else { 
            throw new IdNotDefinedException(this.id);
        }
        //return null;
    }

    @Override
    public String toString() {
        return "lock(" + this.id + ")";
    }
    @Override
    public Stmt deepCopy() {
        return new LockStmt(this.id);
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(id);
        if (typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new MissmatchTypeException(this.whatAmI() ,typevar.toString(), (new IntType()).toString());
    } 
    private String whatAmI() {
        return "LockStmt";
    }
}
