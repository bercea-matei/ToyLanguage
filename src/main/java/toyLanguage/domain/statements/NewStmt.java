package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.expressions.Exp;

public class NewStmt implements Stmt{
    private Exp expression;
    private String varName;

    public NewStmt(String varName,Exp expression) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public String toString() {
        return " new("+ this.varName + "," + this.expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl = state.getSymTable();
        MyHeap<Integer,Value> heapTbl = state.getHeapTable();
        if (!symTbl.isKeyDef(this.varName))
            throw new IdNotDefinedException(this.varName);

        Value val = symTbl.lookup(this.varName);
        if (! (val.getType() instanceof RefType))
            throw new MissmatchValueException("RefType", val.getType().toString());

        RefValue refVal = (RefValue) val;
        Type innerType = refVal.getLocationType();
        Value newVal = this.expression.eval(symTbl, heapTbl);
        if(! newVal.getType().equals(innerType))
            throw new MissmatchValueException(innerType, newVal.getType());
        
        int newAddr = heapTbl.allocate(newVal);
        symTbl.update(this.varName, new RefValue(newAddr, innerType));
        return state;
    }
    @Override
    public Stmt deepCopy() {
        return new NewStmt(this.varName, this.expression.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typevar = typeEnv.lookup(varName);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MissmatchTypeException(typevar.toString(), typexp.toString());
    }
}
