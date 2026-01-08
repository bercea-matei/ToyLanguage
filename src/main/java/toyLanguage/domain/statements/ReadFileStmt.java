package toyLanguage.domain.statements;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.heapMap.MyHeap;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.StringType;
import toyLanguage.domain.types.Type;
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
        MyDict<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyHeap<Integer,Value> heapTbl= state.getHeapTable();

        if (!symTable.isKeyDef(variableName)) {
            throw new IdNotDefinedException(variableName);
        }
        Value variableValue = symTable.lookup(variableName);
        if (!variableValue.getType().equals(new IntType())) {
            throw new MissmatchValueException(new IntType(), variableValue.getType());
        }

        Value expressionValue = expression.eval(symTable, heapTbl);
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

        return null;
    }
    @Override
    public MyDict<String, Type> typecheck(MyDict<String, Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type expType = this.expression.typecheck(typeEnv);
        if (!expType.equals(new StringType())) {
            throw new MissmatchTypeException(new StringType().toString(), expType.toString());
        }

        Type varType = typeEnv.lookup(this.variableName);
        if (!varType.equals(new IntType())) {
            throw new MissmatchTypeException(new IntType().toString(), varType.toString());
        }

        return typeEnv;
    }
}
