package toyLanguage.domain.statements;

import toyLanguage.domain.values.IntValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.Type;

public class NewLatchStmt implements Stmt{
    private static int newFreeLocation = -1;
    private String var;
    private Exp exp;

    public NewLatchStmt(String var ,Exp exp) {
        this.var=var;
        this.exp=exp;
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();
        MyHeap<Integer,Value> heapTbl = state.getHeapTable();


        if (symTbl.isKeyDef(this.var)) {
            Type typId = (symTbl.lookup(this.var)).getType();
            if (! typId.equals(new IntType()))
                throw new MissmatchValueException(new IntType(),typId);

            Value val = this.exp.eval(symTbl, heapTbl);
            if ((val.getType()).equals(typId)) {
                synchronized (state.getLatchTable()) {
                newFreeLocation++;
                state.getLatchTable().add(newFreeLocation, ((IntValue)val).getValue());
                state.getSymTable().update(this.var, new IntValue(newFreeLocation));
                return null;
                }

            } else {
                throw new MissmatchValueException(typId, val.getType());
            }
        } else { 
            throw new IdNotDefinedException(this.var);
        }
        //return null;
    }

    @Override
    public String toString() {
        return "newLatch(" + this.var + ", " + this.exp.toString() + ")";
    }
    @Override
    public Stmt deepCopy() {
        return new NewLatchStmt(this.var, this.exp.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(var);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new IntType()))
            return typeEnv;
        else
            throw new MissmatchTypeException(this.whatAmI() ,typevar.toString(), typexp.toString());
    } 
    private String whatAmI() {
        return "NewLatchStmt";
    }
}
