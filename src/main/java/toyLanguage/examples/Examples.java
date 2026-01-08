package toyLanguage.examples;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.repository.*;
import toyLanguage.domain.statements.*;
import toyLanguage.domain.types.*;
import toyLanguage.controller.MyController;
import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.heapMap.*;
import toyLanguage.domain.adts.heapMap.HeapTable;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.expressions.*;

import java.io.BufferedReader;

public class Examples {

    public static void loadOption1(MyController ctrl) throws ToyLanguageExceptions {
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
        try {
            MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
            ex1.typecheck(typeEnv);
            
            MyStack<Stmt> exeStk = new ExeStk<>();
            exeStk.push(ex1);
            MyDict<String, Value> symTable = new SymbolTable<>();
            MyList<Value> outList = new OutList<>();
            MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
            MyHeap<Integer, Value> heapTable = new HeapTable<>();
            PrgState state = new PrgState( ex1, exeStk, symTable, outList, fileTable, heapTable);
            ctrl.initializePrgState(state);

        } catch (ToyLanguageExceptions e) {
            throw new TypeCheckFailedException(e);
        }
    }

    public static void loadOption2(MyController ctrl) throws ToyLanguageExceptions {
        Stmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),new CompStmt(new VarDeclStmt("b",new IntType()),new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
            
        try {
            MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
            ex2.typecheck(typeEnv);
            
            MyStack<Stmt> exeStk = new ExeStk<>();
            exeStk.push(ex2);
            MyDict<String, Value> symTable = new SymbolTable<>();
            MyList<Value> outList = new OutList<>();
            MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
            MyHeap<Integer, Value> heapTable = new HeapTable<>();
            PrgState state = new PrgState(ex2, exeStk, symTable, outList, fileTable, heapTable);
            ctrl.initializePrgState(state);

        } catch (ToyLanguageExceptions e) {
            throw new TypeCheckFailedException(e);
        }
    }
    public static void loadOption3(MyController ctrl) throws ToyLanguageExceptions {
        Stmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        try {
            MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
            ex3.typecheck(typeEnv);
            
            MyStack<Stmt> exeStk = new ExeStk<>();
            exeStk.push(ex3);
            MyDict<String, Value> symTable = new SymbolTable<>();
            MyList<Value> outList = new OutList<>();
            MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
            MyHeap<Integer, Value> heapTable = new HeapTable<>();
            PrgState state = new PrgState( ex3, exeStk, symTable, outList, fileTable, heapTable);
            ctrl.initializePrgState(state);

        } catch (ToyLanguageExceptions e) {
            throw new TypeCheckFailedException(e);
        }

    }
    public static void loadOption4(MyController ctrl) throws ToyLanguageExceptions {
        Stmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),new CompStmt(new OpenRFileStmt(new VarExp("varf")),new CompStmt(new VarDeclStmt("varc", new IntType()),new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")),new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")),new CloseRFileStmt(new VarExp("varf"))))))))));
        try {
            MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
            ex4.typecheck(typeEnv);
            
            MyStack<Stmt> exeStk = new ExeStk<>();
            exeStk.push(ex4);
            MyDict<String, Value> symTable = new SymbolTable<>();
            MyList<Value> outList = new OutList<>();
            MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
            MyHeap<Integer, Value> heapTable = new HeapTable<>();
            PrgState state = new PrgState( ex4, exeStk, symTable, outList, fileTable, heapTable);
            ctrl.initializePrgState(state);

        } catch (ToyLanguageExceptions e) {
            throw new TypeCheckFailedException(e);
        }
    }
    public static void loadOption5(MyController ctrl) throws ToyLanguageExceptions {
        try {
            Stmt ex5= new CompStmt(
                //TYPECHECK_TEST
                new VarDeclStmt("v",new BoolType()),
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
            try {
                MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
                ex5.typecheck(typeEnv);
                
                MyStack<Stmt> exeStk = new ExeStk<>();
                exeStk.push(ex5);
                MyDict<String, Value> symTable = new SymbolTable<>();
                MyList<Value> outList = new OutList<>();
                MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
                MyHeap<Integer, Value> heapTable = new HeapTable<>();
                PrgState state = new PrgState( ex5, exeStk, symTable, outList, fileTable, heapTable);
                ctrl.initializePrgState(state);

            } catch (ToyLanguageExceptions e) {
                throw new TypeCheckFailedException(e);
            }
        } catch (UnknownOperatorException e) {
            System.out.println("Something went wrong while loading model 5");
        }
    }
    public static void loadOption6(MyController ctrl) throws ToyLanguageExceptions {
            Stmt ex6= new CompStmt(
                new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(
                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        new CompStmt(
                            new NewStmt("a", new VarExp("v")),
                            new CompStmt(
                                new PrintStmt(new VarExp("v")),
                                new PrintStmt(new VarExp("a"))
                            )
                        )
                    )
                )
            );
            try {
                MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
                ex6.typecheck(typeEnv);
                
                MyStack<Stmt> exeStk = new ExeStk<>();
                exeStk.push(ex6);
                MyDict<String, Value> symTable = new SymbolTable<>();
                MyList<Value> outList = new OutList<>();
                MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
                MyHeap<Integer, Value> heapTable = new HeapTable<>();
                PrgState state = new PrgState( ex6, exeStk, symTable, outList, fileTable, heapTable);
                ctrl.initializePrgState(state);

            } catch (ToyLanguageExceptions e) {
                throw new TypeCheckFailedException(e);
            }
    }
    public static void loadOption7(MyController ctrl) throws ToyLanguageExceptions {
            Stmt ex7= new CompStmt(
                new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(
                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        new CompStmt(
                            new NewStmt("a", new VarExp("v")),
                            new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new PrintStmt(
                                    new ArithExp('+', 
                                        new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), 
                                        new ValueExp(new IntValue(5))
                                    )
                                )
                            )
                        )
                    )
                )
            );
            try {
                MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
                ex7.typecheck(typeEnv);
                
                MyStack<Stmt> exeStk = new ExeStk<>();
                exeStk.push(ex7);
                MyDict<String, Value> symTable = new SymbolTable<>();
                MyList<Value> outList = new OutList<>();
                MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
                MyHeap<Integer, Value> heapTable = new HeapTable<>();
                PrgState state = new PrgState( ex7, exeStk, symTable, outList, fileTable, heapTable);
                ctrl.initializePrgState(state);

            } catch (ToyLanguageExceptions e) {
                throw new TypeCheckFailedException(e);
            }
    }
    public static void loadOption8(MyController ctrl)throws ToyLanguageExceptions {
            Stmt ex8 = new CompStmt(
                new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(
                        new PrintStmt(
                            new ReadHeapExp( new VarExp("v"))
                        ),
                        new CompStmt(
                            new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                            new PrintStmt(
                                new ArithExp('+', 
                                    new ReadHeapExp(new VarExp("v")), 
                                    new ValueExp(new IntValue(5)))
                            )
                        )
                    )
                )
            );
            try {
                MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
                ex8.typecheck(typeEnv);
                
                MyStack<Stmt> exeStk = new ExeStk<>();
                exeStk.push(ex8);
                MyDict<String, Value> symTable = new SymbolTable<>();
                MyList<Value> outList = new OutList<>();
                MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
                MyHeap<Integer, Value> heapTable = new HeapTable<>();
                PrgState state = new PrgState( ex8, exeStk, symTable, outList, fileTable, heapTable);
                ctrl.initializePrgState(state);

            } catch (ToyLanguageExceptions e) {
                throw new TypeCheckFailedException(e);
            }
    }
    public static void loadOption9(MyController ctrl) throws ToyLanguageExceptions {
            Stmt ex9 = new CompStmt(
                new VarDeclStmt("v",new RefType(new IntType())),
                new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(
                        new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                        new CompStmt(
                            new NewStmt("a", new VarExp("v")),
                            new CompStmt(
                                new NewStmt("v", new ValueExp(new IntValue(30))),
                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                            )
                        )
                    )
                )
            );
            try {
                MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
                ex9.typecheck(typeEnv);
                
                MyStack<Stmt> exeStk = new ExeStk<>();
                exeStk.push(ex9);
                MyDict<String, Value> symTable = new SymbolTable<>();
                MyList<Value> outList = new OutList<>();
                MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
                MyHeap<Integer, Value> heapTable = new HeapTable<>();
                PrgState state = new PrgState( ex9, exeStk, symTable, outList, fileTable, heapTable);
                ctrl.initializePrgState(state);

            } catch (ToyLanguageExceptions e) {
                throw new TypeCheckFailedException(e);
            }

    }
    public static void loadOption10(MyController ctrl) throws ToyLanguageExceptions {
            Stmt ex10 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                    new VarDeclStmt("a", new RefType(new IntType())),
                    new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(10))),
                        new CompStmt(
                            new NewStmt("a", new ValueExp(new IntValue(22))),
                            new CompStmt(
                                new ForkStmt(
                                    new CompStmt(
                                        new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                        new CompStmt(
                                            new AssignStmt("v", new ValueExp(new IntValue(32))),
                                            new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                                )
                                            )
                                        )
                                    ),
                            new CompStmt(
                                new PrintStmt(new VarExp("v")), 
                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))
                            )
                        )
                    )
                )
            );
            try {
                MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
                ex10.typecheck(typeEnv);
                
                MyStack<Stmt> exeStk = new ExeStk<>();
                exeStk.push(ex10);
                MyDict<String, Value> symTable = new SymbolTable<>();
                MyList<Value> outList = new OutList<>();
                MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
                MyHeap<Integer, Value> heapTable = new HeapTable<>();
                PrgState state = new PrgState( ex10, exeStk, symTable, outList, fileTable, heapTable);
                ctrl.initializePrgState(state);

            } catch (ToyLanguageExceptions e) {
                throw new TypeCheckFailedException(e);
            }
    }
}
