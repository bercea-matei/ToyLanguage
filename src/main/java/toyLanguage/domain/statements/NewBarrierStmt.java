package toyLanguage.domain.statements;

import toyLanguage.domain.values.IntValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;

import java.util.ArrayList;
import java.util.List;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.pair.Pair;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.Type;

public class NewBarrierStmt implements Stmt{
    private static int newFreeLocation = -1;
    private String id;
    private Exp exp;

    public NewBarrierStmt(String id, Exp exp) {
        this.id=id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();
        MyHeap<Integer,Value> heapTbl = state.getHeapTable();

        if (symTbl.isKeyDef(this.id)) {
            Type typId = (symTbl.lookup(this.id)).getType();
            if (! typId.equals(new IntType()))
                throw new MissmatchValueException(new IntType(),typId);
            Value val = this.exp.eval(symTbl, heapTbl);
            if (!(val.getType()).equals(typId))
                throw new MissmatchValueException(typId, val.getType());

            synchronized (state.getBarrierTable()) {
                newFreeLocation++;
                List<Integer> threadList = new ArrayList<>(); 
                Integer intVal = ((IntValue)val).getValue();
                Pair<Integer, List<Integer>> newBarr = new Pair<>(intVal, threadList);
                state.getBarrierTable().add(newFreeLocation, newBarr);
                state.getSymTable().update(this.id, new IntValue(newFreeLocation));
            }
            return null;
        } else { 
            throw new IdNotDefinedException(this.id);
        }
        //return null;
    }

    @Override
    public String toString() {
        return "newBarrier(" + this.id + "," + this.exp.toString() +")";
    }
    @Override
    public Stmt deepCopy() {
        return new NewBarrierStmt(this.id, this.exp.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(id);
        if (typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new MissmatchTypeException(this.whatAmI() ,typevar.toString(), new IntType().toString());
    } 
    private String whatAmI() {
        return "NewBarrierStmt";
    }
}
