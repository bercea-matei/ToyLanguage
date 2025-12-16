package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.expressions.Exp;

public class WriteHeapStmt implements Stmt{
    private Exp expression;
    private String varName;

    public WriteHeapStmt(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return " wH(" + this.varName + "," + this.expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl= state.getSymTable();
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();       
        if (! symTbl.isKeyDef(this.varName))
            throw new IdNotFoundException(this.varName);
        Value val = symTbl.lookup(this.varName);

        if (! (val.getType() instanceof RefType))
            throw new MissmatchValueException("RefType", val.getType().toString());

        RefValue refVal = (RefValue) val;
        int addr = refVal.getAddr();
        if(! heapTbl.isKeyDef(addr))
            throw new IdNotFoundException(String.valueOf(addr));

        Value newVal = this.expression.eval(symTbl, heapTbl);
        Type locationType = refVal.getLocationType();
        if (!newVal.getType().equals(locationType))
            throw new MissmatchValueException(locationType.toString(), newVal.getType().toString());        heapTbl.update(addr, newVal);
        return state;
    }
    @Override
    public Stmt deepCopy() {
        return new WriteHeapStmt(this.varName,this.expression.deepCopy());
    }
    @Override
    public MyDict<String, Type> typecheck(MyDict<String, Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type varType = typeEnv.lookup(this.varName);
        if (!(varType instanceof RefType)) {
            throw new MissmatchTypeException("RefType", this.varName.toString());
        }

        Type expType = this.expression.typecheck(typeEnv);

        Type innerTypeOfRef = ((RefType) varType).getInner();

        if (innerTypeOfRef.equals(expType)) {
            return typeEnv;
        } else {
            throw new MissmatchTypeException(innerTypeOfRef.toString(), expType.toString());
        }
    }
}
