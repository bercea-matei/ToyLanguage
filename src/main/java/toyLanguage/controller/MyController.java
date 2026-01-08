package toyLanguage.controller;

import java.util.List;

import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.UnfinishedProgramException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;

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
}

