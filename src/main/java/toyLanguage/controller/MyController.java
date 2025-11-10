package toyLanguage.controller;

import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.EmptyStackException;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.repository.*;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.adts.stack.MyStack;

public class MyController implements Controller {
    private Repository repo;
    private boolean printFlag = true; 

    public MyController(Repository repo) { 
        this.repo = repo;
    }

    @Override
    public void addPrgState(PrgState state) {
        this.repo.addPrgState(state);
    }

    @Override
    public PrgState oneStep(PrgState state) throws ToyLanguageExceptions {
         if (state == null)
            throw new NoProgramToRunException();
        MyStack<Stmt> stk=state.getExeStk();
        if(stk.isEmpty()) 
            //this.repo.finishCrtState();    
            throw new EmptyStackException();
        Stmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }
    
    @Override
    public void allStep(ExecutionObserver observer) throws ToyLanguageExceptions {
        PrgState prg = repo.getCrtPrg();
        if (prg == null)
            throw new NoProgramToRunException();
        
        if (this.printFlag) {
            //observer.onExecutionStart(prg);
            this.repo.logPrgStateExec();
        }
        while (!prg.getExeStk().isEmpty()) {
            oneStep(prg);
            if (this.printFlag) {
                //observer.onStepExecuted(prg);
                this.repo.logPrgStateExec();
            }
        }
        if (this.printFlag) {
            //observer.onExecutionFinish(prg);
            this.repo.logPrgStateExec();
        }
        //this.repo.finishCrtState();
    }
    //TODo -- we keep this depending on threading part
    @Override
    public void goToNextState() throws FinishUnexistentStateException{
        this.repo.finishCrtState();
    }

    @Override
    public PrgState getCurrentState() throws NoProgramToRunException {
        PrgState prg = this.repo.getCrtPrg();
        if (prg == null)
            throw new NoProgramToRunException();
        return prg;
    }
    @Override
    public boolean getPrintFlag() {
        return this.printFlag;
    }
    @Override 
    public void flipPrintFlag() {
        if (printFlag)
            printFlag = false;
        else
            printFlag = true;
    }
    @Override
    public Stmt getOriginalState() throws NoProgramToRunException {
        PrgState prg = this.repo.getCrtPrg();
        if (prg == null)
            throw new NoProgramToRunException();
        return prg.getOriginal();
    }
    @Override
    public void setLogFilePath(String logFilePath) {
        this.repo.setLogFilePath(logFilePath);
    }
    @Override
    public String getLogFilePath() {
        return this.repo.getLogFilePath();
    }

}
