package toyLanguage.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.InvalidFilePathException;
import toyLanguage.domain.myExceptions.NoFilePathException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.UnknownOperatorException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.values.Value;

public interface MyController {
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
    public void loadOption1();
    public void loadOption2() throws UnknownOperatorException;
    public void loadOption3();
    public void loadOption4();
    public void loadOption5();
    public void loadOption6();
    public void loadOption7() throws UnknownOperatorException;
    public void loadOption8() throws UnknownOperatorException;
    public void loadOption9() throws UnknownOperatorException;
    public void logPrgStateExec() throws InvalidFilePathException, NoFilePathException;
}

