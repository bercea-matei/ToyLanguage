package toyLanguage.domain.prg_state;

import java.io.BufferedReader;

import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.statements.Stmt;

public class PrgState {
    private final MyStack<Stmt> exeStk;
    private final MyDict<String, Value> symTable;
    private final MyList<Value> outList;
    private final MyDict<StringValue, BufferedReader> fileTable;
    private final Stmt originalProgram;

    public PrgState(MyStack<Stmt> stk, MyDict<String, Value> dict, MyList<Value> list, MyDict<StringValue, BufferedReader> fileTable, Stmt origPrg) {
        this.exeStk = stk;
        this.symTable = dict;
        this.outList = list;
        this.fileTable = fileTable;
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
    public MyDict<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }
    @Override
    public String toString() {
        StringBuilder allStr = new StringBuilder();
        allStr.append(this.exeStk.toString());
        allStr.append("\n");
        allStr.append(this.symTable.toString());
        allStr.append("\n");
        allStr.append(this.outList.toString());
        allStr.append("\n");
        allStr.append(this.fileTable.toString());
        return allStr.toString();
    }
    public PrgState deepCopy() {
        return new PrgState(this.exeStk.deepCopy(), this.symTable.deepCopy(), this.outList.deepCopy(), this.fileTable.deepCopy(), this.originalProgram.deepCopy());
    }
    public Stmt getOriginal() {
        return this.originalProgram.deepCopy();
    }

}
