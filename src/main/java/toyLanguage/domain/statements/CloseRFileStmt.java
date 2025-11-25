package toyLanguage.domain.statements;

import java.io.BufferedReader;
import java.io.IOException;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.myExceptions.ClosingFileException;
import toyLanguage.domain.myExceptions.MissEvaluationException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.StringType;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;

public class CloseRFileStmt implements Stmt {

    private final Exp expression;

    public CloseRFileStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Stmt deepCopy() {
        return new CloseRFileStmt(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }
    
    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String, Value> symTable = state.getSymTable();
        MyDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();

        Value result = expression.eval(symTable, heapTbl);
        if (!result.getType().equals(new StringType())) {
            throw new MissEvaluationException(new StringType(), result.getType());
        }

        StringValue filenameValue = (StringValue) result;
        BufferedReader descriptor = fileTable.lookup(filenameValue);

        try {
            descriptor.close();
        } catch (IOException e) {
            throw new ClosingFileException(filenameValue.getValue(), e.getMessage());
        }

        fileTable.remove(filenameValue);

        return state;
    }
}
