package toyLanguage.controller;

import java.io.BufferedReader;
import java.util.List;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.list.MyList;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.UnfinishedProgramException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;

public interface MyController {
    public void initializePrgState(PrgState state) throws UnfinishedProgramException;
    //public void oneStepForAllPrg(List<PrgState> prgList) throws ToyLanguageExceptions;
    public void oneStepForAllPrg() throws ToyLanguageExceptions;
    public void allStep(ExecutionObserver observer) throws ToyLanguageExceptions;
    //public PrgState getCurrentState() throws NoProgramToRunException;
    public boolean getPrintFlag();
    public void flipPrintFlag();
    public Stmt getOriginalState() throws NoProgramToRunException;
    public void setLogFilePath(String logFilePath);
    public String getLogFilePath();
    public void logPrgStateExec() throws ToyLanguageExceptions;
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList);
    public List<PrgState> getPrgList();
    public void shutdown();
    public MyStack<Stmt> getExeStkById(int id);
    public MyDict<String,Value> getSymTableById(int id);
    public MyDict<StringValue,BufferedReader> getFileTableById(int id);
    public MyList<Value> getOutListById(int id);
    public MyHeap<Integer,Value> getHeapTableById(int id);
}

