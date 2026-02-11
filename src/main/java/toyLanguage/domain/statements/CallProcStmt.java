package toyLanguage.domain.statements;

import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.prg_state.PrgState;

import java.util.ArrayList;
import java.util.List;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.dictionary.SymbolTable;
import toyLanguage.domain.types.Type;

public class CallProcStmt implements Stmt{
    private String fname;
    private List<Exp> expList;

    public CallProcStmt(String fname , List<Exp> exps) {
        this.fname=fname;
        this.expList=exps;
    }

    @Override
public PrgState execute(PrgState state) throws ToyLanguageExceptions {
    var procTable = state.getProcTable(); 
    if (!procTable.isKeyDef(fname)) throw new ToyLanguageExceptions("Procedure " + fname + " not defined.");

    var procDetails = procTable.lookup(fname);
    List<String> formalParams = procDetails.getFirst();
    Stmt body = procDetails.getSecond();

    List<Value> evaluatedArgs = new ArrayList<>();
    for (Exp e : expList) {
        evaluatedArgs.add(e.eval(state.getSymTable(), state.getHeapTable()));
    }

    MyDict<String, Value> newSymTable = new SymbolTable<>();
    
    for (int i = 0; i < formalParams.size(); i++) {
        newSymTable.add(formalParams.get(i), evaluatedArgs.get(i));
    }

    state.getSymTables().push(newSymTable);
    state.getExeStk().push(new ReturnStmt());
    state.getExeStk().push(body); 

    return null;
}

    @Override
    public String toString() {
        return "call " + fname + "(" + this.expList.toString() + ")";
    }
    @Override
    public Stmt deepCopy() {
        List<Exp> copyExpList = new ArrayList<>();
        for (Exp e : this.expList) 
            copyExpList.add(e.deepCopy());
        return new CallProcStmt(this.fname, copyExpList);
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        for (Exp e : expList) {
            e.typecheck(typeEnv);
        }
        return typeEnv;
    } 
    private String whatAmI() {
        return "CallProcStmt";
    }
}
