package toyLanguage.controller;

import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;

public interface Controller {
    public void addPrgState(PrgState state);
    public PrgState oneStep(PrgState state) throws ToyLanguageExceptions;
    public void allStep(ExecutionObserver observer) throws ToyLanguageExceptions;
    public PrgState getCurrentState() throws NoProgramToRunException;
    public boolean getPrintFlag();
    public void flipPrintFlag();
    public Stmt getOriginalState() throws NoProgramToRunException;
    public void goToNextState() throws FinishUnexistentStateException;
    public void setLogFilePath(String logFilePath);
    public String getLogFilePath();
}

