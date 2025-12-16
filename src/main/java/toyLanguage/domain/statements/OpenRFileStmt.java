package toyLanguage.domain.statements;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.StringType;
import toyLanguage.domain.types.Type;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements Stmt {
    private final Exp expression;

    public OpenRFileStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Stmt deepCopy() {
        return new OpenRFileStmt(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String, Value> symTable = state.getSymTable();
        MyDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();

        Value result = this.expression.eval(symTable, heapTbl);

        if (!result.getType().equals(new StringType())) {
            throw new MissmatchValueException(new StringType(), result.getType());
        }
        StringValue filenameValue = (StringValue) result;

        if (fileTable.isKeyDef(filenameValue)) {
            throw new FileAlreadyOpenException(filenameValue.getValue());
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filenameValue.getValue()));
            fileTable.add(filenameValue, bufferedReader);

        } catch (IOException e) {
            throw new FileNotFoundException(filenameValue.getValue().toString(), e.getMessage());
        }
        return state;
    }
    @Override
    public MyDict<String, Type> typecheck(MyDict<String, Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type expType = expression.typecheck(typeEnv);

        if (expType.equals(new StringType())) {
            return typeEnv;
        } else {
            throw new MissmatchTypeException(new StringType().toString(), expType.toString());
        }
    }
}
