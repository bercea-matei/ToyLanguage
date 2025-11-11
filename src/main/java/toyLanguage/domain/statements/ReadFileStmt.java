package toyLanguage.domain.statements;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.fileTable.IFileTable;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.myExceptions.IOFileException;
import toyLanguage.domain.myExceptions.IdNotDefinedException;
import toyLanguage.domain.myExceptions.MissEvaluationException;
import toyLanguage.domain.myExceptions.MissmatchValueException;
import toyLanguage.domain.myExceptions.InvalidFormatException;
import toyLanguage.domain.myExceptions.ToyLanguageExceptions;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.StringType;
import toyLanguage.domain.values.IntValue;
import toyLanguage.domain.values.StringValue;
import toyLanguage.domain.values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements Stmt {

    private final Exp expression;
    private final String variableName;

    public ReadFileStmt(Exp expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public Stmt deepCopy() {
        return new ReadFileStmt(expression.deepCopy(), variableName);
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString() + ", " + variableName + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String, Value> symTable = state.getSymTable();
        IFileTable fileTable = state.getFileTable();

        if (!symTable.isKeyDef(variableName)) {
            throw new IdNotDefinedException(variableName);
        }
        Value variableValue = symTable.lookup(variableName);
        if (!variableValue.getType().equals(new IntType())) {
            throw new MissmatchValueException(new IntType(), variableValue.getType());
        }

        Value expressionValue = expression.eval(symTable);
        if (!expressionValue.getType().equals(new StringType())) {
            throw new MissEvaluationException(new StringType(), expressionValue.getType());
        }
        StringValue filenameValue = (StringValue) expressionValue;

        BufferedReader bufferedReader = fileTable.lookup(filenameValue);

        int readValue;
        try {
            String line = bufferedReader.readLine();
            if (line == null) {
                readValue = 0;
            } else if (line.trim().isEmpty()) {
                readValue = 0;
            } else {
                readValue = Integer.parseInt(line.trim());
            }
        } catch (IOException e) {
            throw new IOFileException(filenameValue.getValue(), e.getMessage());
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(filenameValue.getValue());
        }
        symTable.update(variableName, new IntValue(readValue));

        return state;
    }
}
