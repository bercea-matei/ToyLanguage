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

public class ReleaseStmt implements Stmt{
    private String var;

    public ReleaseStmt(String var) {
        this.var=var;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();

        if (symTbl.isKeyDef(this.var)) {
            Value valId = (symTbl.lookup(this.var));
            if (! valId.getType().equals(new IntType()))
                throw new MissmatchValueException(new IntType(),valId.getType());

            Integer foundIndex = ((IntValue) valId).getValue();

            synchronized (state.getSemaphoreTable()) {
                Pair<Integer, List<Integer>> mySemph = state.getSemaphoreTable().lookup(foundIndex);

                if (mySemph.getSecond().contains(state.getID()))
                    mySemph.getSecond().remove((Integer)state.getID());
                }
            return null;
        } else { 
            throw new IdNotDefinedException(this.var);
        }
        //return null;
    }

    @Override
    public String toString() {
        return "release(" + this.var + ")";
    }
    @Override
    public Stmt deepCopy() {
        return new ReleaseStmt(this.var);
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(var);
        if (!typevar.equals(new IntType()))
            throw new MissmatchTypeException(this.whatAmI() ,typevar.toString(), new IntType().toString());
        return typeEnv;

    } 
    private String whatAmI() {
        return "ReleaseStmt";
    }
}
