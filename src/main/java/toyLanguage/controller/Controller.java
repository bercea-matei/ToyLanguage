package toyLanguage.controller;

import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.UnknownOperatorException;
import toyLanguage.domain.myExceptions.EmptyStackException;
import toyLanguage.domain.myExceptions.FinishUnexistentStateException;
import toyLanguage.domain.myExceptions.InvalidFilePathException;
import toyLanguage.domain.myExceptions.NoFilePathException;
import toyLanguage.domain.myExceptions.NoProgramToRunException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.repository.*;
import toyLanguage.domain.statements.*;
import toyLanguage.domain.types.*;

import java.io.BufferedReader;

import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.expressions.*;

public class Controller implements MyController {
    private MyRepository repo;
    private boolean printFlag = true; 

    public Controller(MyRepository repo) { 
        this.repo = repo;
    }

    @Override
    public void addPrgState(PrgState state) {
        this.repo.addPrgState(state);
    }
    
    //TODO - Move Options In Separate Files
    @Override
    public void loadOption1() {
        Stmt ex1= new CompStmt(
            new VarDeclStmt("v",new IntType()),
            new CompStmt(
                new AssignStmt("v",new ValueExp(new IntValue(2))),
                new CompStmt(
                    new NOP(), 
                    new PrintStmt(new VarExp("v"))
                )
            )
        );
        MyStack<Stmt> exeStk = new ExeStk<>();
        exeStk.push(ex1);
        MyDict<String, Value> symTable = new SymbolTable<>();
        MyList<Value> outList = new OutList<>();
        MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
        MyDict<Integer, Value> heapTable = new HeapTable<>();
        PrgState state = new PrgState( ex1, exeStk, symTable, outList, fileTable, heapTable);
        this.repo.addPrgState(state);
    }
    @Override
    public void loadOption2() throws UnknownOperatorException{
            Stmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),new CompStmt(new VarDeclStmt("b",new IntType()),new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
            MyStack<Stmt> exeStk = new ExeStk<>();
            exeStk.push(ex2);
            MyDict<String, Value> symTable = new SymbolTable<>();
            MyList<Value> outList = new OutList<>();
            MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
            MyDict<Integer, Value> heapTable = new HeapTable<>();
            PrgState state = new PrgState(ex2, exeStk, symTable, outList, fileTable, heapTable);
            this.repo.addPrgState(state);
    }
    @Override
    public void loadOption3() {
        Stmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        MyStack<Stmt> exeStk = new ExeStk<>();
        exeStk.push(ex3);
        MyDict<String, Value> symTable = new SymbolTable<>();
        MyList<Value> outList = new OutList<>();
        MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
        MyDict<Integer, Value> heapTable = new HeapTable<>();
        PrgState state = new PrgState(ex3, exeStk, symTable, outList, fileTable, heapTable);
        this.repo.addPrgState(state);
    }
    @Override
    public void loadOption4() {
        Stmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),new CompStmt(new OpenRFileStmt(new VarExp("varf")),new CompStmt(new VarDeclStmt("varc", new IntType()),new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")),new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")),new CloseRFileStmt(new VarExp("varf"))))))))));
        MyStack<Stmt> exeStk = new ExeStk<>();
        exeStk.push(ex4);
        MyDict<String, Value> symTable = new SymbolTable<>();
        MyList<Value> outList = new OutList<>();
        MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
        MyDict<Integer, Value> heapTable = new HeapTable<>();
        PrgState state = new PrgState(ex4,exeStk, symTable, outList, fileTable, heapTable);
        this.repo.addPrgState(state);
    }
    @Override
    public void loadOption5() {
        try {
            Stmt ex5= new CompStmt(
                new VarDeclStmt("v",new IntType()),
                new CompStmt(
                    new AssignStmt("v",new ValueExp(new IntValue(4))),
                    new CompStmt(
                        new WhileStmt(
                            new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0)) ),
                            new CompStmt(
                                new PrintStmt(new VarExp("v")),
                                new AssignStmt(
                                    "v", 
                                    new ArithExp(
                                        '-',
                                        new VarExp("v"),
                                        new ValueExp(new IntValue(1))
                                    )
                                )
                            )
                        ),
                        new PrintStmt(new VarExp("v"))
                    )
                )
            );
            MyStack<Stmt> exeStk = new ExeStk<>();
                exeStk.push(ex5);
                MyDict<String, Value> symTable = new SymbolTable<>();
                MyList<Value> outList = new OutList<>();
                MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
                MyDict<Integer, Value> heapTable = new HeapTable<>();
                PrgState state = new PrgState( ex5,exeStk, symTable, outList, fileTable,heapTable);
                this.repo.addPrgState(state);
        } catch (UnknownOperatorException e) {
            System.out.println("Something went wrong while loading model 5");
        }
        
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
            }
        }
        //if (this.printFlag) {
            //observer.onExecutionFinish(prg);
            //this.repo.logPrgStateExec();
        //}
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
    @Override
    public void logPrgStateExec() throws InvalidFilePathException, NoFilePathException {
        this.repo.logPrgStateExec();
    }
}
