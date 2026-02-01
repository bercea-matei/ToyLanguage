package toyLanguage.repository;

import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.adts.list.MyList;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.InvalidFilePathException;
import toyLanguage.domain.myExceptions.NoFilePathException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.myExceptions.UnfinishedProgramException;

import java.io.BufferedReader;
import java.util.List;

public interface MyRepository {
    public void initializePrgState(PrgState state) throws UnfinishedProgramException;
    public Stmt getOriginalState() throws NoProgramToRunException; 
    public void finishCrtState() throws FinishUnexistentStateException;
    public void logPrgStateExec(PrgState state) throws InvalidFilePathException, NoFilePathException;
    public void setLogFilePath(String logFilePath);
    public String getLogFilePath();
    public List<PrgState> getPrgList();
    public void setPrgList(List<PrgState> prgs);
    public MyStack<Stmt> getExeStkById(int id);
    public MyDict<String,Value> getSymTableById(int id);
    public MyDict<StringValue,BufferedReader> getFileTableById(int id);
    public MyList<Value> getOutListById(int id);
    public MyHeap<Integer,Value> getHeapTableById(int id);
}
