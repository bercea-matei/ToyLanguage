package toyLanguage.repository;

import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.InvalidFilePathException;
import toyLanguage.domain.myExceptions.NoFilePathException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.myExceptions.UnfinishedProgramException;

public interface MyRepository {
    public PrgState getCrtPrg() throws NoProgramToRunException; // returns first prg in line
    public void initializePrgState(PrgState state) throws UnfinishedProgramException;
    public Stmt getOriginalState() throws NoProgramToRunException; //returns from 1st prg in line
    public void finishCrtState() throws FinishUnexistentStateException;
    public void logPrgStateExec() throws InvalidFilePathException, NoFilePathException;
    public void setLogFilePath(String logFilePath);
    public String getLogFilePath();
}
