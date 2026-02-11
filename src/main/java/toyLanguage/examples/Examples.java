package toyLanguage.examples;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.statements.*;
import toyLanguage.domain.types.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.expressions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Examples {

    public List<ProgramExample> getAll() {
        List<ProgramExample> list = new ArrayList<>();

        list.add(new ProgramExample(createExample1()));
        try {
            list.add(new ProgramExample(createExample2()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example2 due to error: " + e.getMessage());
        }
        list.add(new ProgramExample(createExample3()));
        list.add(new ProgramExample(createExample4()));
        try {
            list.add(new ProgramExample(createExample5()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example5 due to error: " + e.getMessage());
        }
        list.add(new ProgramExample(createExample6()));
        try {
            list.add(new ProgramExample(createExample7()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example7 due to error: " + e.getMessage());
        }
        try {
            list.add(new ProgramExample(createExample8()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example8 due to error: " + e.getMessage());
        }
        list.add(new ProgramExample(createExample9()));
        list.add(new ProgramExample(createExample10()));
        try {
            list.add(new ProgramExample(createExample11()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example11 due to error: " + e.getMessage());
        }
        try {
            list.add(new ProgramExample(createExample12()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example12 due to error: " + e.getMessage());
        }
        try {
            list.add(new ProgramExample(createExample13()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example13 due to error: " + e.getMessage());
        }try {
            list.add(new ProgramExample(createExample14()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example14 due to error: " + e.getMessage());
        }
        try {
            list.add(new ProgramExample(createExample15()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example15 due to error: " + e.getMessage());
        }
        try {
            list.add(new ProgramExample(createExample16()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example16 due to error: " + e.getMessage());
        }
        try {
            list.add(new ProgramExample(createExample17()));
        } catch (ToyLanguageExceptions e) {
            System.err.println("Skipping example16 due to error: " + e.getMessage());
        }

        return list;
    }

    private static Stmt createExample1() {
        return new CompStmt(
            new VarDeclStmt("v",new IntType()),
            new CompStmt(
                new AssignStmt("v",new ValueExp(new IntValue(2))),
                new CompStmt(
                    new NOP(), 
                    new PrintStmt(new VarExp("v"))
                )
            )
        );
    }
    

    private static Stmt createExample2() throws UnknownOperatorException{
            return new CompStmt( new VarDeclStmt("a",new IntType()),new CompStmt(new VarDeclStmt("b",new IntType()),new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))), new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
    }

    private static Stmt createExample3() {
        return new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))), new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        }

    private static Stmt createExample4() {
            return new CompStmt(new VarDeclStmt("varf", new StringType()),new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),new CompStmt(new OpenRFileStmt(new VarExp("varf")),new CompStmt(new VarDeclStmt("varc", new IntType()),new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")),new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")),new CloseRFileStmt(new VarExp("varf"))))))))));
        }

    private static Stmt createExample5() throws UnknownOperatorException{
        return new CompStmt(
                new VarDeclStmt("v",new IntType()),
                new CompStmt(
                    new AssignStmt("v",new ValueExp(new IntValue(4))),
                    new CompStmt(
                        new WhileStmt(
                            new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
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
        }
    
    private static Stmt createExample6() {
        return new CompStmt(
                new VarDeclStmt("v",new RefType(new BoolType())),
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
        }
    private static Stmt createExample7() throws UnknownOperatorException{
        return new CompStmt(
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
        }
    private static Stmt createExample8() throws UnknownOperatorException {
        return new CompStmt(
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
    }
   private static Stmt createExample9() {
        return new CompStmt(
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
        }
   private static Stmt createExample10() {
       return new CompStmt(
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
                                new CompStmt(
                                    new PrintStmt(new ReadHeapExp(new VarExp("a"))),
                                    new PrintStmt(new ReadHeapExp(new VarExp("a"))))
                                )
                            )
                        )
                    )
                )
            );
    } 

    private static Stmt createExample11() throws ToyLanguageExceptions{
        Stmt varInit = new CompStmt(
            new VarDeclStmt("v1", new RefType(new IntType())), 
            new CompStmt(
                new VarDeclStmt("cnt", new IntType()), 
                new CompStmt(
                    new NewStmt("v1", new ValueExp(new IntValue(1))),
                    new CreateSemaphoreStmt("cnt", new ReadHeapExp(new VarExp("v1")))
                )
            )
        );

        Stmt fork1 = new ForkStmt(
        new CompStmt(
            new AcquireStmt("cnt"), 
            new CompStmt(
                //--
                new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))), 
                new CompStmt(
                    new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                    new ReleaseStmt("cnt"))
            )
        ));

        Stmt fork2 = new ForkStmt(
        new CompStmt(
            new AcquireStmt("cnt"), 
            new CompStmt(
                //--
                new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))), 
                new CompStmt(
                    //--
                    new WriteHeapStmt("v1", 
                        new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(2)))),
                    new CompStmt(
                    new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                    new ReleaseStmt("cnt"))
                )
            )
        ));

        Stmt lastPage = new CompStmt(
            new AcquireStmt("cnt"), 
            new CompStmt(            
                new PrintStmt(
                    new ArithExp('-', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                new ReleaseStmt("cnt")
            )
        );

        return new CompStmt(varInit, 
            new CompStmt(fork1, 
                new CompStmt(fork2, lastPage)));

    }

    private static Stmt createExample12() throws ToyLanguageExceptions {
       
        Stmt varInit = new CompStmt(
            new VarDeclStmt("a", new IntType()), 
            new CompStmt(
                new VarDeclStmt("b", new IntType()), 
                new CompStmt(
                    new VarDeclStmt("c", new IntType()), 
                    new CompStmt(
                        new AssignStmt("a", new ValueExp(new IntValue(1))),
                        new CompStmt(
                            new AssignStmt("b", new ValueExp(new IntValue(2))),
                            new AssignStmt("c", new ValueExp(new IntValue(5)))
                        )
                    )
                )
            )
        );
        Stmt switch_ = new SwitchStmt(
            new ArithExp('*', new VarExp("a"), new ValueExp(new IntValue(10))), 
            new ArithExp('*', new VarExp("b"), new VarExp("c")), 
            new ValueExp(new IntValue(10)), 
            new CompStmt(
                new PrintStmt(new VarExp("a")), 
                new PrintStmt(new VarExp("b"))
            ),
            new CompStmt(
                new PrintStmt(new ValueExp(new IntValue(100))), 
                new PrintStmt(new ValueExp(new IntValue(200))) 
            ), 
                new PrintStmt(new ValueExp(new IntValue(300))) 
        );

        return new CompStmt(
            varInit, 
            new CompStmt(switch_, 
            new PrintStmt(new ValueExp(new IntValue(300)))
            ));

    }
    private static Stmt createExample13() throws ToyLanguageExceptions {
        return new CompStmt(
            new VarDeclStmt("v1", new RefType(new IntType())),
            new CompStmt(
                new VarDeclStmt("v2", new RefType(new IntType())),
                new CompStmt(
                    new VarDeclStmt("v3", new RefType(new IntType())),
                    new CompStmt(
                        new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(
                            new NewStmt("v1", new ValueExp(new IntValue(2))),
                            new CompStmt(
                                new NewStmt("v2", new ValueExp(new IntValue(3))),
                                new CompStmt(
                                    new NewStmt("v3", new ValueExp(new IntValue(4))),
                                    new CompStmt(
                                        // newLatch(cnt, rH(v2)); -> rH(v2) is 3
                                        new NewLatchStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                                        new CompStmt(
                                            // Outer Fork
                                            new ForkStmt(
                                                new CompStmt(
                                                    new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                    new CompStmt(
                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                        new CompStmt(
                                                            new CountDownStmt("cnt"),
                                                            // Middle Fork
                                                            new ForkStmt(
                                                                new CompStmt(
                                                                    new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                    new CompStmt(
                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                        new CompStmt(
                                                                            new CountDownStmt("cnt"),
                                                                            // Inner Fork
                                                                            new ForkStmt(
                                                                                new CompStmt(
                                                                                    new WriteHeapStmt("v3", new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)))),
                                                                                    new CompStmt(
                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                                                                                        new CountDownStmt("cnt")
                                                                                    )
                                                                                )
                                                                            )
                                                                        )
                                                                    )
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            ),
                                            // Main Thread Continuation
                                            new CompStmt(
                                                new LatchAwaitStmt("cnt"),
                                                new CompStmt(
                                                    new PrintStmt(new ValueExp(new IntValue(100))),
                                                    new CompStmt(
                                                        new CountDownStmt("cnt"),
                                                        new PrintStmt(new ValueExp(new IntValue(100)))
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        );
        
    }
    private static Stmt createExample14() throws ToyLanguageExceptions {
    // Each thread: wh(v, rh(v)*10) -> await(cnt) -> print(rh(v))
    
        Stmt thread1Logic = new CompStmt(
            new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
            new CompStmt(new BarrAwaitStmt("cnt"), new PrintStmt(new ReadHeapExp(new VarExp("v1"))))
        );

        Stmt thread2Logic = new CompStmt(
            new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
            new CompStmt(new BarrAwaitStmt("cnt"), new PrintStmt(new ReadHeapExp(new VarExp("v2"))))
        );

        Stmt thread3Logic = new CompStmt(
            new WriteHeapStmt("v3", new ArithExp('*', new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)))),
            new CompStmt(new BarrAwaitStmt("cnt"), new PrintStmt(new ReadHeapExp(new VarExp("v3"))))
        );

        return new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
            new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
            new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
            new CompStmt(new VarDeclStmt("cnt", new IntType()),
            new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
            new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
            new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
            new CompStmt(new NewBarrierStmt("cnt", new ValueExp(new IntValue(3))),
            new CompStmt(new ForkStmt(thread1Logic),
            new CompStmt(new ForkStmt(thread2Logic),
            new CompStmt(new ForkStmt(thread3Logic),
            new CompStmt(new BarrAwaitStmt("cnt"), 
            new PrintStmt(new ValueExp(new StringValue("Rendezvous Complete!")))))))))))))));
    }

    private static Stmt createExample15() throws ToyLanguageExceptions{
        Stmt v1SubChild = new CompStmt(new LockStmt("x"), 
            new CompStmt(new WriteHeapStmt("v1", new ArithExp('-', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)))), 
                new UnlockStmt("x")));

        Stmt v1FirstChild = new CompStmt(new ForkStmt(v1SubChild), 
            new CompStmt(new LockStmt("x"), 
                new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))), 
                    new UnlockStmt("x"))));

        Stmt v2SubChild = new CompStmt(new LockStmt("q"), 
            new CompStmt(new WriteHeapStmt("v2", new ArithExp('+', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)))), 
                new UnlockStmt("q")));

        Stmt v2FirstChild = new CompStmt(new ForkStmt(v2SubChild), 
            new CompStmt(new LockStmt("q"), 
                new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))), 
                    new UnlockStmt("q"))));

        return new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
            new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
            new CompStmt(new VarDeclStmt("x", new IntType()),
            new CompStmt(new VarDeclStmt("q", new IntType()),
            new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
            new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
            new CompStmt(new NewLockStmt("x"),
            new CompStmt(new ForkStmt(v1FirstChild),
            new CompStmt(new NewLockStmt("q"),
            new CompStmt(new ForkStmt(v2FirstChild),
            new CompStmt(new NOP(), new CompStmt(new NOP(), new CompStmt(new NOP(), new CompStmt(new NOP(),
            new CompStmt(new LockStmt("x"),
            new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
            new CompStmt(new UnlockStmt("x"),
            new CompStmt(new LockStmt("q"),
            new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
            new UnlockStmt("q"))))))))))))))))))));
    }
    private Stmt createExample16() throws ToyLanguageExceptions {
               
        Stmt innerFork = new ForkStmt(new CallProcStmt("sum", Arrays.asList(new VarExp("v"), new VarExp("w"))));
        
        Stmt outerFork = new ForkStmt(new CompStmt(
            new CallProcStmt("product", Arrays.asList(new VarExp("v"), new VarExp("w"))),
            innerFork
        ));

        Stmt mainProgram = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new VarDeclStmt("w", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
            new CompStmt(new AssignStmt("w", new ValueExp(new IntValue(5))),
            new CompStmt(new CallProcStmt("sum", Arrays.asList(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10))), new VarExp("w"))),
            new CompStmt(new PrintStmt(new VarExp("v")), 
            outerFork))))));

        return mainProgram;
    }
    private static Stmt createExample17() throws ToyLanguageExceptions {
        return new CompStmt(
            new VarDeclStmt("a", new RefType(new IntType())), 
            new CompStmt(
                new VarDeclStmt("b", new RefType(new IntType())),
                    new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(new NewStmt("a", new ValueExp(new IntValue(0))),
                            new CompStmt(new NewStmt("b", new ValueExp(new IntValue(0))), 
                                new CompStmt(
                new WriteHeapStmt("a",  new ValueExp(new IntValue(1))), 
                new CompStmt(
                    new WriteHeapStmt("b",  new ValueExp(new IntValue(2))),
                    new CompStmt(
                        new CondAssignStmt("v", 
                            new RelExp(
                                "<",
                                new ReadHeapExp(new VarExp("a")),
                                new ReadHeapExp(new VarExp("b"))),
                                new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))), 
                        new CompStmt(
                            new PrintStmt(new VarExp("v")),
                            new CompStmt(
                                new CondAssignStmt("v", 
                                new RelExp(
                                    ">",
                                    new ArithExp('-', new ReadHeapExp(new VarExp("b")), new ValueExp(new IntValue(2))),
                                    new ReadHeapExp(new VarExp("a"))),
                                    new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                new PrintStmt(new VarExp("v"))
                            )
                                        
                        )
                    )
                )
            )
                            )    
                        )
                    )
                )
            );
    }

}
