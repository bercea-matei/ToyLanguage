package toyLanguage.domain.statements;

import toyLanguage.domain.values.IntValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;

import java.util.List;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.pair.Pair;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.Type;

public class BarrAwaitStmt implements Stmt{
    private String id;

    public BarrAwaitStmt(String id) {
        this.id=id;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();
        MyDict<Integer, Pair<Integer, List<Integer>>> barrTbl = state.getBarrierTable();


        if (symTbl.isKeyDef(this.id)) {
            Value foundIndex = symTbl.lookup(this.id);
            Type typId = (foundIndex).getType();
            if (! typId.equals(new IntType()))
                throw new MissmatchValueException(new IntType(),typId);

            synchronized (barrTbl) {
                Integer fndIdx = ((IntValue) foundIndex).getValue();
                
                if (! barrTbl.isKeyDef(fndIdx))
                    throw new IdNotFoundException(fndIdx.toString());
                
                Pair<Integer, List<Integer>> myBarr = barrTbl.lookup(fndIdx);
                synchronized(myBarr) {
                    Integer threadId = state.getID();
                    if ( myBarr.getSecond().contains(threadId)) {
                        if (myBarr.getSecond().size() < myBarr.getFirst())
                            state.getExeStk().push(this);
                    } else {
                        myBarr.getSecond().add(threadId);

                        if (myBarr.getSecond().size() < myBarr.getFirst())
                            state.getExeStk().push(this);
                    }
                }
            }
            return null;
            
        } else { 
            throw new IdNotDefinedException(this.id);
        }
        //return null;
    }

    @Override
    public String toString() {
        return "await(" + this.id + ")";
    }
    @Override
    public Stmt deepCopy() {
        return new BarrAwaitStmt(this.id);
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
        return "BarrAwaitStmt";
    }
}
