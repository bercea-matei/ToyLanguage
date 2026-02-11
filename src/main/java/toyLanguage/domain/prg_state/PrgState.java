package toyLanguage.domain.prg_state;

import java.io.BufferedReader;
import java.util.List;

import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.myExceptions.EmptyStackException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.adts.pair.*;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.statements.Stmt;

public class PrgState {
    private final MyStack<Stmt> exeStk;
    private final MyDict<String, Value> symTable;
    private final MyDict<Integer, Integer> latchTable;
    private final MyList<Value> outList;
    private final MyDict<StringValue, BufferedReader> fileTable;
    private final MyHeap<Integer, Value> heapTable;
    private final MyDict<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private final MyDict<Integer, Pair<Integer, List<Integer>>> barrierTable;
    private final Stmt originalProgram;
    private final int id;
    private static int newId = 1;

    public PrgState(    
            Stmt origPrg, MyStack<Stmt> stk, 
            MyDict<String, Value> dict, MyList<Value> list, 
            MyDict<StringValue, BufferedReader> fileTable, 
            MyHeap<Integer, Value> heapTable,
            MyDict<Integer, Pair<Integer, List<Integer>>> semaphoreTable,
            MyDict<Integer, Integer> latchTable,
            MyDict<Integer, Pair<Integer, List<Integer>>> barrierTable
            ) {
        this.originalProgram = origPrg.deepCopy();
        this.exeStk = stk;
        this.symTable = dict;
        this.outList = list;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.semaphoreTable = semaphoreTable;
        this.latchTable = latchTable;
        this.barrierTable = barrierTable;
        this.id = getNewId();
    }
    private static synchronized int getNewId() {
        newId += 1;
        return newId - 1; 
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
    public MyHeap<Integer, Value> getHeapTable() {
        return this.heapTable;
    }
    public MyDict<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return this.semaphoreTable;
    }
    public MyDict<Integer, Integer> getLatchTable() {
        return this.latchTable;
    }
    public MyDict<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return this.barrierTable;
    }
    public int getID() {
        return this.id;
    }
    @Override
    public String toString() {
        StringBuilder allStr = new StringBuilder();
        allStr.append("Thread ID: " + Integer.toString(this.id));
        allStr.append("\n");
        allStr.append(this.exeStk.toString());
        allStr.append("\n");
        allStr.append(this.symTable.toString());
        allStr.append("\n");
        allStr.append(this.outList.toString());
        allStr.append("\n");
        allStr.append(this.fileTable.toString());
        allStr.append("\n");
        allStr.append(this.semaphoreTable.toString());
        allStr.append("\n");
        allStr.append(this.latchTable.toString());
        allStr.append("\n");
        allStr.append(this.barrierTable.toString());
        return allStr.toString();
    }
    public PrgState deepCopy() {
        return new PrgState(this.originalProgram.deepCopy(),this.exeStk.deepCopy(), this.symTable.deepCopy(), this.outList, this.fileTable, this.heapTable, this.semaphoreTable, this.latchTable, this.barrierTable);
    }
    public Stmt getOriginal() {
        return this.originalProgram.deepCopy();
    }
    public Boolean isNotCompleted() {
        return ! this.exeStk.isEmpty();
    }
    public PrgState oneStep() throws ToyLanguageExceptions{
        if(this.exeStk.isEmpty()) 
            throw new EmptyStackException();
        Stmt crtStmt = this.exeStk.pop();
    return crtStmt.execute(this);
    }

}
