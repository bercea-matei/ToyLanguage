package toyLanguage.domain.prg_state;

import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.statements.Stmt;

public class PrgState {
    private final MyStack<Stmt> exeStk;
    private final MyDict<String, Value> symTable;
    private final MyList<Value> outList;
    private final Stmt originalProgram;

    public PrgState(MyStack<Stmt> stk, MyDict<String, Value> dict, MyList<Value> list, Stmt origPrg) {
        this.exeStk = stk;
        this.symTable = dict;
        this.outList = list;
        this.originalProgram = origPrg.deepCopy();
    }
    
    public MyDict<String, Value> getSymTable() {
        return this.symTable;
    }
    public MyStack<Stmt> getExeStk() {
        return this.exeStk;
    }
    public MyList<Value> getOutList() {
        return this.outList;
    }
    @Override
    public String toString() {
        StringBuilder allStr = new StringBuilder();
        allStr.append(this.exeStk.toString());
        allStr.append("\n");
        allStr.append(this.symTable.toString());
        allStr.append("\n");
        allStr.append(this.outList.toString());
        return allStr.toString();
    }
    public PrgState deepCopy() {
        return new PrgState(this.exeStk.deepCopy(), this.symTable.deepCopy(), this.outList.deepCopy(), this.originalProgram);
    }
    public Stmt getOriginal() {
        //MyStack<Stmt> copyStk = new ExeStk<>();
        //MyDict<String, Value> copyDict = new SymbolTable<>();
        //MyList<Value> copyList = new OutList<>();
        //Stmt copyOP = this.originalProgram.deepCopy();
        //copyStk.push(copyOP);
        //return new PrgState(copyStk, copyDict, copyList, copyOP);
        return this.originalProgram.deepCopy();
    }

}
