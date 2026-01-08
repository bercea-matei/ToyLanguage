package toyLanguage.repository;

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
    private String logFilePath;
    private String msgs[] = {"------------------------------","Thread ID: ", "ExeStack:", "SymTable", "Out", "FileTable", "HeapTable"};

    public Repository() {
        this.prgStates = new ArrayList<>();
    }
    @Override
    public void initializePrgState(PrgState state) throws UnfinishedProgramException{
        if (prgStates.size() != 0)
            throw new UnfinishedProgramException();
        this.prgStates.add(state);
    }

    @Override
    public Stmt getOriginalState() throws NoProgramToRunException{
        return this.prgStates.get(0).getOriginal().deepCopy();
    }
    @Override 
    public void finishCrtState() throws FinishUnexistentStateException{
        if (! this.prgStates.isEmpty())
            this.prgStates.remove(0);
        else
            throw new FinishUnexistentStateException();
    }
    @Override
    public void logPrgStateExec(PrgState state) throws InvalidFilePathException, NoFilePathException {
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
            logFile.println(msgs[0]);
            logFile.close();
        } catch (IOException e) {
            throw new InvalidFilePathException();
        }
    }
    @Override
    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }
    @Override
    public String getLogFilePath() {
        return this.logFilePath;
    }
    @Override
    public List<PrgState> getPrgList() {
        return this.prgStates;
    }
    @Override
    public void setPrgList(List<PrgState> prgs) {
        this.prgStates = prgs;
    }
}


