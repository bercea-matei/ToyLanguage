package toyLanguage.controller;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.repository.*;
import toyLanguage.domain.statements.*;
import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.heapMap.*;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.adts.pair.Pair;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.values.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    public void oneStepForAllPrg() throws ToyLanguageExceptions {
        List<PrgState> prgList = this.repo.getPrgList();
        if (prgList.isEmpty()) {
            throw new EmptyStackException();
        }

        logCurrentStates(prgList);

        List<Callable<PrgState>> callList = prgList.stream()
            .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
            .collect(Collectors.toList());
        
        try {
            List<Future<PrgState>> futures = this.executor.invokeAll(callList);

            List<PrgState> newPrgList = new ArrayList<>();
            
            for (Future<PrgState> future : futures) {
                try {
                    PrgState result = future.get();
                    if (result != null) newPrgList.add(result);
                } catch (ExecutionException e) {
                    if (e.getCause() instanceof ToyLanguageExceptions) {
                        throw (ToyLanguageExceptions) e.getCause();
                    } else {
                        throw new RuntimeException("Unexpected Engine Crash", e.getCause());
                    }
                }
            }

            prgList.addAll(newPrgList);
            
            safeGarbageCollector(prgList, repo.getHeapTable().getContent());
            
            logCurrentStates(prgList);

            repo.setPrgList(removeCompletedPrg(repo.getPrgList()));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ToyLanguageExceptions("Execution interrupted.");
        }
    }

    private void logCurrentStates(List<PrgState>prgList) {
        prgList.forEach(prg -> {
            try {
                    repo.logPrgStateExec(prg);
            }
            catch (ToyLanguageExceptions e) {
                throw new RuntimeException("CRITICAL: Logging failed. Execution halted.", e);
            }});
    }

    
    @Override
    public void allStep() throws ToyLanguageExceptions {
        List<PrgState> prgList=removeCompletedPrg(repo.getPrgList());
        while(!prgList.isEmpty()) {
            oneStepForAllPrg();
            prgList=repo.getPrgList();
        }
        this.executor.shutdownNow();
    }
    
    private void safeGarbageCollector(List<PrgState> allPrgStates, Map<Integer,Value> heap) {
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
        /*
        return heap.entrySet().stream()
                .filter(entry -> finalReachable.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));    
        */
        //here we modify the heap in place so as to not have 
        //to sync it later between all the program states
        heap.keySet().retainAll(reachableAddresses);
    }
    private List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
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

    @Override
    public MyDict<StringValue,BufferedReader> getFileTable() {
        return this.repo.getFileTable();
    }
    @Override
    public MyList<Value> getOutList() {
        return this.repo.getOutList();
    }
    @Override
    public MyHeap<Integer,Value> getHeapTable() {
        return this.repo.getHeapTable();
    }
    @Override
    public MyDict<Integer, Pair<Integer, List<Integer>>> getSemaphoreTable() {
        return this.repo.getSemaphoreTable();
    }
    @Override
    public MyDict<Integer,Integer> getLatchTable() {
        return this.repo.getLatchTable();
    }
    @Override
    public MyDict<Integer, Pair<Integer, List<Integer>>> getBarrierTable() {
        return this.repo.getBarrierTable();
    }
}
