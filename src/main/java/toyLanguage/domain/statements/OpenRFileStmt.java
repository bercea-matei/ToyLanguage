package toyLanguage.domain.statements;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.fileTable.IFileTable;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.myExceptions.FileAlreadyOpenException;
import toyLanguage.domain.myExceptions.MissmatchValueException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.myExceptions.FileNotFoundException;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.StringType;
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
        IFileTable fileTable = state.getFileTable();

        Value result = this.expression.eval(symTable);

        if (!result.getType().equals(new StringType())) {
            throw new MissmatchValueException(new StringType(), result.getType());
        }
        StringValue filenameValue = (StringValue) result;

        if (fileTable.isOpened(filenameValue)) {
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
}
