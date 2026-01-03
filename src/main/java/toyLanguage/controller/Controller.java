package toyLanguage.controller;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.repository.*;
import toyLanguage.domain.statements.*;
import toyLanguage.domain.types.*;
import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.heapMap.*;
import toyLanguage.domain.adts.heapMap.HeapTable;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.expressions.*;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Controller implements MyController {
    private MyRepository repo;
    private boolean printFlag = true; 

    public Controller(MyRepository repo) { 
        this.repo = repo;
    }

    @Override
    public void initializePrgState(PrgState state) throws UnfinishedProgramException {
        this.repo.initializePrgState(state);
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
        PrgState newState = crtStmt.execute(state);
        return newState;
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
        if(prg.getExeStk().isEmpty()) 
            //this.repo.finishCrtState();    
            throw new EmptyStackException();
        while (!prg.getExeStk().isEmpty()) {
            oneStep(prg);
            if (this.printFlag) {
                //observer.onStepExecuted(prg);
                this.repo.logPrgStateExec();
                prg.getHeapTable().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getContent().values()),prg.getHeapTable().getContent()));
                this.repo.logPrgStateExec();
            }
        }
    }
    private Map<Integer,Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap) {
        return heap.entrySet()
                .stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    private Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap) {
        Set<Integer> reachableAddresses = new HashSet<>(symTableAddr);

        boolean newAddressFound = true;
        while (newAddressFound) {
            newAddressFound = false;
            Set<Integer> indirectlyReachable = heap.entrySet()
                    .stream()
                    .filter(entry -> reachableAddresses.contains(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .filter(value -> value instanceof RefValue)
                    .map(value -> ((RefValue) value).getAddr())
                    .collect(Collectors.toSet());

            if (reachableAddresses.addAll(indirectlyReachable)) {
                newAddressFound = true;
            }
        }
        final Set<Integer> finalReachable = reachableAddresses;
        return heap.entrySet().stream()
                .filter(entry -> finalReachable.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));    
    }
    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
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
    @Override
    public void logPrgStateExec() throws InvalidFilePathException, NoFilePathException {
        this.repo.logPrgStateExec();
    }
}
