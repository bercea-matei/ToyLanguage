package toyLanguage.repository;

import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;

public interface Repository {
    public PrgState getCrtPrg() throws NoProgramToRunException; // returns first prg in line
    //public PrgState getCrtPrg(String id);
    //public PrgState getAllCrtPrg();
    public void addPrgState(PrgState state);
    public Stmt getOriginalState() throws NoProgramToRunException; //returns from 1st prg in line
    //public Stmt getOriginalState(String id);
    //public Stmt getAllOriginalState();
    public void finishCrtState() throws FinishUnexistentStateException;
}
