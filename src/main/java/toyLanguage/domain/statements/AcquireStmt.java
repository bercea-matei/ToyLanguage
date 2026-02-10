package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.*;
import toyLanguage.domain.values.*;

import java.util.List;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.pair.Pair;
import toyLanguage.domain.expressions.Exp;

public class AcquireStmt implements Stmt{
    private String varName;

    public AcquireStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return " acquire("+ this.varName + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();
        var semTable = state.getSemaphoreTable();
        if (!symTbl.isKeyDef(this.varName))
            throw new IdNotDefinedException(this.varName);

        Value val = symTbl.lookup(this.varName);
        if (! (val.getType() instanceof IntType))
            throw new MissmatchValueException(new IntType().toString(), val.getType().toString());

        
        synchronized (state.getSemaphoreTable()) {
            Integer foundIndex = ((IntValue) val).getValue();
            if (!semTable.isKeyDef(foundIndex))
                throw new IdNotDefinedException(this.varName);

            Pair<Integer, List<Integer>> mySem = semTable.lookup(foundIndex);
            Integer NL = mySem.getSecond().size();
            Integer N1 = mySem.getFirst();
            if (N1 > NL) {
                if (! mySem.getSecond().contains(state.getID()))
                    mySem.getSecond().add(state.getID());
            } else {
                state.getExeStk().push(this);
            }
        }
        return null;
    }
    @Override
    public Stmt deepCopy() {
        return new AcquireStmt(this.varName);
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(varName);
        if (!typevar.equals(new IntType()))
            throw new MissmatchTypeException(this.whatAmI() ,typevar.toString(), new IntType().toString());
        return typeEnv;
    } 

    private String whatAmI() {
        return "AcquireStmt";
    }

}
