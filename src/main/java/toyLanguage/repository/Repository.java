package toyLanguage.repository;

import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.InvalidFilePathException;
import toyLanguage.domain.myExceptions.NoFilePathException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;

public class Repository implements MyRepository {
    private List<PrgState> prgStates;
    private String logFilePath;
    private String msgs[] = {"------------------------------","ExeStack:", "SymTable", "Out", "FileTable", "HeapTable"};

    public Repository() {
        this.prgStates = new ArrayList<>();
    }
    @Override
    public void addPrgState(PrgState state) {
        this.prgStates.add(state);
    }
    @Override
    public PrgState getCrtPrg() throws NoProgramToRunException{
        if (! this.prgStates.isEmpty())
            return this.prgStates.get(0);
        else 
            throw new NoProgramToRunException();
    }

    @Override
    public Stmt getOriginalState() throws NoProgramToRunException{
        return this.prgStates.get(0).getOriginal();
    }
    @Override 
    public void finishCrtState() throws FinishUnexistentStateException{
        if (! this.prgStates.isEmpty())
            this.prgStates.remove(0);
        else
            throw new FinishUnexistentStateException();
    }
    @Override
    public void logPrgStateExec() throws InvalidFilePathException, NoFilePathException {
        if (this.logFilePath == null || this.logFilePath.equals(""))
            throw new NoFilePathException();
        try {
            PrintWriter logFile  = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            
            logFile.println(msgs[1]);
            for (Stmt stmt : this.prgStates.get(0).getExeStk()) {
                logFile.println(stmt.toString());
            }

            logFile.println(msgs[2]);
            for (Map.Entry<String, Value> entry : this.prgStates.get(0).getSymTable()) {
                logFile.println(entry.getKey() + " --> " + entry.getValue().toString());
            }

            logFile.println(msgs[3]);
            for (Value value : this.prgStates.get(0).getOutList()) {
                logFile.println(value.toString());
            }
            logFile.println(msgs[4]);
            for (Map.Entry<StringValue, BufferedReader> entry : this.prgStates.get(0).getFileTable()) {
                logFile.println(entry.getKey());
            }
            logFile.println(msgs[5]);
            for (Map.Entry<Integer, Value> entry : this.prgStates.get(0).getHeapTable()) {
                logFile.println(entry.getKey());
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
}


