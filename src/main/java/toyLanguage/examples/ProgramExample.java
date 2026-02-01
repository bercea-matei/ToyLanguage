package toyLanguage.examples;

import java.io.BufferedReader;

import toyLanguage.controller.MyController;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.statements.Stmt;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.*;
import toyLanguage.domain.adts.dictionary.*;
import toyLanguage.domain.adts.heapMap.*;
import toyLanguage.domain.adts.heapMap.HeapTable;
import toyLanguage.domain.adts.list.*;
import toyLanguage.domain.adts.stack.*;
import toyLanguage.domain.values.*;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.TypeCheckFailedException;

public class ProgramExample {
    private Stmt stmt;
    
    public ProgramExample(Stmt s) {
        this.stmt = s;
    }

    public void loadIntoController(MyController ctrl) throws ToyLanguageExceptions{
        try {
            MyDict<String, Type> typeEnv = new TypeEnv<String, Type>();
            this.stmt.typecheck(typeEnv);
            
            MyStack<Stmt> exeStk = new ExeStk<>();
            exeStk.push(this.stmt);
            MyDict<String, Value> symTable = new SymbolTable<>();
            MyList<Value> outList = new OutList<>();
            MyDict<StringValue, BufferedReader> fileTable = new FileTable<>();
            MyHeap<Integer, Value> heapTable = new HeapTable<>();
            PrgState state = new PrgState(this.stmt, exeStk, symTable, outList, fileTable, heapTable);
            ctrl.initializePrgState(state);

        } catch (ToyLanguageExceptions e) {
            throw new TypeCheckFailedException(e);
        }
    }

    @Override
    public String toString() {
        return this.stmt.toString();
    }

}
