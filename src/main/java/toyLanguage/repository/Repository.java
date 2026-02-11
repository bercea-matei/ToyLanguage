package toyLanguage.repository;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.list.MyList;
import toyLanguage.domain.adts.pair.Pair;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.InvalidFilePathException;
import toyLanguage.domain.myExceptions.NoFilePathException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.myExceptions.UnfinishedProgramException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;

import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Repository implements MyRepository {
    private List<PrgState> prgStates;
    private MyList<Value> outList;
    private MyHeap<Integer,Value> heapTable;
    private MyDict<StringValue,BufferedReader> fileTable;
    private MyDict<Integer, Pair<Integer, List<Integer>>> semaphoreTable;
    private MyDict<Integer, Integer> latchTable;
    private MyDict<Integer, Pair<Integer, List<Integer>>> barrierTable;
    private MyDict<Integer, Integer> lockTable;
    private MyDict<String, Pair<List<String>,Stmt>> procTable;
    private String logFilePath = "ourLogs.log";
    private String msgs[] = {"------------------------------","Thread ID: ", "ExeStack:", "SymTable", "Out", "FileTable", "HeapTable", "SemaphoreTable", "LatchTable", "BarrierTable", "LockTable", "ProcTable"};

    public Repository() {
        this.prgStates = new ArrayList<>();
    }
    @Override
    public synchronized void initializePrgState(PrgState state) throws UnfinishedProgramException{
        if (prgStates.size() != 0)
            throw new UnfinishedProgramException();
        this.prgStates.add(state);
        this.heapTable = state.getHeapTable();
        this.outList = state.getOutList();
        this.fileTable = state.getFileTable();
        this.semaphoreTable = state.getSemaphoreTable();
        this.latchTable = state.getLatchTable();
        this.barrierTable = state.getBarrierTable();
        this.lockTable = state.getLockTable();
        this.procTable = state.getProcTable();
    }

    @Override
    public synchronized Stmt getOriginalState() throws NoProgramToRunException{
        return this.prgStates.get(0).getOriginal().deepCopy();
    }
    @Override 
    public synchronized void finishCrtState() throws FinishUnexistentStateException{
        if (! this.prgStates.isEmpty())
            this.prgStates.remove(0);
        else
            throw new FinishUnexistentStateException();
    }
    @Override
    public synchronized void logPrgStateExec(PrgState state) throws InvalidFilePathException, NoFilePathException {
        if (this.logFilePath == null || this.logFilePath.equals(""))
            throw new NoFilePathException();
        try {
            PrintWriter logFile  = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.print(msgs[1]);
            logFile.println(state.getID());

            logFile.println(msgs[2]);
            for (Stmt stmt : state.getExeStk()) {
                logFile.println(stmt.toString());
            }

            logFile.println(msgs[3]);
            for (Map.Entry<String, Value> entry : state.getSymTable()) {
                logFile.println(entry.getKey() + " --> " + entry.getValue().toString());
            }

            logFile.println(msgs[4]);
            for (Value value : state.getOutList()) {
                logFile.println(value.toString());
            }
            logFile.println(msgs[5]);
            for (Map.Entry<StringValue, BufferedReader> entry : state.getFileTable()) {
                logFile.println(entry.getKey());
            }
            logFile.println(msgs[6]);
            for (Map.Entry<Integer, Value> entry : state.getHeapTable()) {
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println(msgs[7]);
            for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : state.getSemaphoreTable()) {
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println(msgs[8]);
            for (Map.Entry<Integer, Integer> entry : state.getLatchTable()) {
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println(msgs[9]);
            for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : state.getBarrierTable()) {
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println(msgs[10]);
            for (Map.Entry<Integer, Integer> entry : state.getLockTable()) {
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println(msgs[11]);
            for (Map.Entry<String, Pair<List<String>, Stmt>> entry : state.getProcTable()) {
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println(msgs[0]);
            logFile.close();
        } catch (IOException e) {
            throw new InvalidFilePathException();
        }
    }
    @Override
    public synchronized void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    @Override
    public synchronized String getLogFilePath() {
        return this.logFilePath;
    }
    @Override
    public synchronized List<PrgState> getPrgList() {
        return this.prgStates;
    }
    @Override
    public synchronized void setPrgList(List<PrgState> prgs) {
        this.prgStates = prgs;
    }
    @Override
    public synchronized MyStack<Stmt> getExeStkById(int id) {
        for (PrgState prg : this.prgStates)
            if(prg.getID() == id)
                return prg.getExeStk();
        return null;
    }
    @Override
    public synchronized MyDict<String,Value> getSymTableById(int id) {
        for (PrgState prg : this.prgStates)
            if(prg.getID() == id)
                return prg.getSymTable();
        return null;
    }
    @Override
    public synchronized MyDict<StringValue,BufferedReader> getFileTableById(int id) {
        for (PrgState prg : this.prgStates)
            if(prg.getID() == id)
                return prg.getFileTable();
        return null;
    }
    @Override
    public synchronized MyList<Value> getOutListById(int id) {
        for (PrgState prg : this.prgStates)
            if(prg.getID() == id)
                return prg.getOutList();
        return null;
    }
    @Override
    public synchronized MyHeap<Integer,Value> getHeapTableById(int id) {
        for (PrgState prg : this.prgStates)
            if(prg.getID() == id)
                return prg.getHeapTable();
        return null;
    }

    @Override
    public synchronized MyDict<StringValue,BufferedReader> getFileTable() {
        return this.fileTable;
    }
    @Override
    public synchronized MyList<Value> getOutList() {
        return this.outList;
    }
    @Override
    public synchronized MyHeap<Integer,Value> getHeapTable() {
        return this.heapTable;
    }
    @Override
    public synchronized MyDict<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return this.semaphoreTable;
    }
    @Override
    public synchronized MyDict<Integer,Integer> getLatchTable() {
        return this.latchTable;
    }
    @Override
    public MyDict<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return this.barrierTable;
    }
    @Override
    public synchronized MyDict<Integer,Integer> getlockTable() {
        return this.lockTable;
    }
    @Override
    public MyDict<String, Pair<List<String>,Stmt>> getProcTable() {
        return this.procTable;
    }


}

