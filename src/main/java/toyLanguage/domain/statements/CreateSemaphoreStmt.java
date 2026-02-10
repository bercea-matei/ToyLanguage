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

public class CreateSemaphoreStmt implements Stmt{
    private static int newFreeLocation = -1;
    private String var;
    private Exp exp1;

    public CreateSemaphoreStmt(String var, Exp exp1) {
        this.var=var;
        this.exp1 = exp1;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();
        MyHeap<Integer,Value> heapTbl = state.getHeapTable();

        if (symTbl.isKeyDef(this.var)) {
            Type typId = (symTbl.lookup(this.var)).getType();
            if (! typId.equals(new IntType()))
                throw new MissmatchValueException(new IntType(),typId);
            Value number1 = this.exp1.eval(symTbl, heapTbl);
            if (!(number1.getType()).equals(new IntType()))
                throw new MissmatchValueException(number1.getType(), new IntType());

            synchronized (state.getSemaphoreTable()) {
                newFreeLocation++;
                Integer N1 = ((IntValue)number1).getValue();
                List<Integer> threadList = new ArrayList<>(); 
                Pair<Integer, List<Integer>> newSem = new Pair<>(N1, threadList);
                state.getSemaphoreTable().add(newFreeLocation, newSem);
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
        return "createSemaphore(" + this.var + "," + this.exp1.toString() +")";
    }
    @Override
    public Stmt deepCopy() {
        return new CreateSemaphoreStmt(this.var, this.exp1.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(var);
        if (!typevar.equals(new IntType()))
            throw new MissmatchTypeException(this.whatAmI() ,typevar.toString(), new IntType().toString());
        Type typexp = exp1.typecheck(typeEnv);
        if (!typexp.equals(new IntType()))
            throw new MissmatchTypeException(this.whatAmI() ,typexp.toString(), new IntType().toString());
        return typeEnv;

    } 
    private String whatAmI() {
        return "CreateSemaphoreStmt";
    }
}
