package toyLanguage.domain.statements;

import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.prg_state.PrgState;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.types.BoolType;
import toyLanguage.domain.types.Type;


public class CondAssignStmt implements Stmt{
    private String id;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;

    public CondAssignStmt(String id, Exp exp1, Exp exp2, Exp exp3) {
        //v=exp1?exp2:exp3
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.id = id;
        
    }
    @Override
    public String toString() {
        return this.id+"=" +exp1.toString() +"?"+exp2.toString()+":"+exp3.toString();
    }
    public PrgState execute(PrgState state) throws ToyLanguageExceptions{
        MyStack<Stmt> exeStk = state.getExeStk();
        MyDict<String,Value> symTbl = state.getSymTable();
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();

        if (symTbl.isKeyDef(this.id)) {
            Value val1 = this.exp1.eval(symTbl, heapTbl);
            if((val1.getType()).equals(new BoolType()))
            {
                Value val2 = this.exp2.eval(symTbl, heapTbl);
                Value val3 = this.exp3.eval(symTbl, heapTbl);
                Type typId= (symTbl.lookup(this.id)).getType();
                if(!(val2.getType()).equals(typId))
                    throw new MissmatchValueException(typId, val2.getType());
                if(!(val3.getType()).equals(typId))
                    throw new MissmatchValueException(typId, val3.getType());
                
                AssignStmt thenS = new AssignStmt(id, exp2);
                AssignStmt elseS = new AssignStmt(id, exp3);
                IfStmt ifStmt = new IfStmt(exp1, thenS, elseS);
                exeStk.push(ifStmt);

                }
            else {
                throw new MissmatchValueException(new BoolType(), val1.getType());
            }
        } else { 
            throw new IdNotDefinedException(this.id);
        }
        return null;
    }
    @Override
    public Stmt deepCopy() {
        return new CondAssignStmt(this.id, this.exp1.deepCopy(), this.exp2.deepCopy(), this.exp3.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typexp1=exp1.typecheck(typeEnv);
        if (typexp1.equals(new BoolType())) {
            Type typexp2=exp2.typecheck(typeEnv);
            Type typexp3=exp3.typecheck(typeEnv);
            Type typevar = typeEnv.lookup(id);
            if (! typevar.equals(typexp2))
                throw new MissmatchTypeException(this.whatAmI(), typevar.toString(), typexp2.toString());
            if (! typevar.equals(typexp3))
                throw new MissmatchTypeException(this.whatAmI(), typevar.toString(), typexp3.toString());
            else
                return typeEnv;
        }
        else
            throw new MissmatchTypeException(this.whatAmI(), new BoolType().toString(), typexp1.toString());
    } 
    private String whatAmI() {
        return "CondAssignStmt";
    }

}
