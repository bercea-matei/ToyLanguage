package toyLanguage.controller;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.repository.*;
import toyLanguage.domain.statements.*;
import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.heapMap.*;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.values.*;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller implements MyController {
    private MyRepository repo;
    private boolean printFlag = true; 
    private ExecutorService executor;
    private int noThreads = 2;

    public Controller(MyRepository repo) { 
        this.repo = repo;
    }

    @Override
    public void initializePrgState(PrgState state) throws UnfinishedProgramException {
        this.repo.initializePrgState(state);
        this.executor = Executors.newFixedThreadPool(noThreads);
    }
    
    @Override
    //public void oneStepForAllPrg(List<PrgState> prgList) throws ToyLanguageExceptions {
    public void oneStepForAllPrg() throws ToyLanguageExceptions {
        List<PrgState> prgList = this.repo.getPrgList();
         //before the execution, print the PrgState List into the log file
         prgList.forEach(
            prg -> {try {repo.logPrgStateExec(prg);}
            catch (ToyLanguageExceptions e) {
                return; 
            }});
         //RUN concurrently one step for each of the existing PrgStates
         //-----------------------------------------------------------------------
         //prepare the list of callables
         List<Callable<PrgState>> callList = prgList.stream()
            .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
            .collect(Collectors.toList());
            //start the execution of the callables
            //it returns the list of new created PrgStates (namely threads)
        
        try {
            List<PrgState> newPrgList = this.executor.invokeAll(callList).stream()
                .map(future -> {
                    try { return future.get();}
                    catch(InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Task was interrupted and is shutting down.");
                    return null;
                    } catch (ExecutionException e) {
                        Throwable cause = e.getCause();
                        System.err.println("An error occurred during task execution: " + cause.getMessage());
                    return null;
                    }})
                .filter(p -> p!=null)
                .collect(Collectors.toList());
                //add the new created threads to the list of existing threads
            prgList.addAll(newPrgList);
            //------------------------------------------------------------------------------
            //after the execution, print the PrgState List into the log file
            prgList.forEach(prg -> {
                try {repo.logPrgStateExec(prg);}
                catch (ToyLanguageExceptions e) {
                    return;
                }});
            //Save the current programs in the repository
            prgList=removeCompletedPrg(repo.getPrgList()); //move this here
            repo.setPrgList(prgList);

        } catch (InterruptedException e) {
            System.err.println("Executor service was interrupted while waiting for tasks to complete.");
            Thread.currentThread().interrupt();
            return;
        }
    }
    
    @Override
    public void allStep(ExecutionObserver observer) throws ToyLanguageExceptions {
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        //List<PrgState> prgList=repo.getPrgList();
        while(prgList.size() > 0) {
            //conservativeGarbageCollector
            //MyHeap<Integer, Value> sharedHeap = prgList.get(0).getHeapTable();
            //Map<Integer, Value> newHeapContent = safeGarbageCollector(prgList, sharedHeap.getContent());
            //sharedHeap.setContent(newHeapContent);
            //oneStepForAllPrg(prgList);
            oneStepForAllPrg();
            prgList=repo.getPrgList();
            //remove the completed programs
            //---moved inside oneStepForAllPrg for more uniform behaviour
            //prgList=removeCompletedPrg(repo.getPrgList());
            //repo.setPrgList(prgList);
        }
        this.executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository
        // update the repository state
        repo.setPrgList(prgList);
    }
    /*
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
    */
    private Map<Integer,Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap) {
        return heap.entrySet()
                .stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    //TODo-remove heap param
    private Map<Integer,Value> safeGarbageCollector(List<PrgState> allPrgStates, Map<Integer,Value> heap) {
        List<Integer> allRootAddresses = allPrgStates.stream()
            .flatMap(prg -> getAddrFromSymTable(prg.getSymTable().getContent().values()).stream())
            .collect(Collectors.toList());

        Set<Integer> reachableAddresses = new HashSet<>(allRootAddresses);

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

    /*@Override
    public PrgState getCurrentState() throws NoProgramToRunException {
        PrgState prg = this.repo.getCrtPrg();
        if (prg == null)
            throw new NoProgramToRunException();
        return prg;
    }
    */
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
        Stmt orig_stmt = this.repo.getOriginalState();
        if (orig_stmt == null)
            throw new NoProgramToRunException();
        return orig_stmt;
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
        for (PrgState prg : this.repo.getPrgList())
            this.repo.logPrgStateExec(prg);
    }
    
    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
            .filter(p -> p.isNotCompleted())
            .collect(Collectors.toList());
    }
    @Override
    public List<PrgState> getPrgList() {
        return repo.getPrgList();
    }
    @Override
    public void shutdown() {
        if (this.executor != null) {
            this.executor.shutdownNow();
        }
    }
    @Override
    public MyStack<Stmt> getExeStkById(int id) {
        return this.repo.getExeStkById(id);
    }
    @Override
    public MyDict<String,Value> getSymTableById(int id) {
        return this.repo.getSymTableById(id);
    }
    @Override
    public MyDict<StringValue,BufferedReader> getFileTableById(int id) {
        return this.repo.getFileTableById(id);
    }
    @Override
    public MyList<Value> getOutListById(int id) {
        return this.repo.getOutListById(id);
    }
    @Override
    public MyHeap<Integer,Value> getHeapTableById(int id) {
        return this.repo.getHeapTableById(id);
    }
}
