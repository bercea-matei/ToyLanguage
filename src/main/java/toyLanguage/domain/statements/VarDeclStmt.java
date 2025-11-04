package toy_language.domain.statements;

import toy_language.domain.prg_state.PrgState;
import toy_language.domain.types.*;

import toy_language.domain.adts.dictionary.MyDict;
import toy_language.domain.values.*;
import toy_language.domain.my_exceptions.*;

public class VarDeclStmt implements Stmt{
    private String name;
    private Type typ;

    public VarDeclStmt (String name, Type typ) {
        this.name = name;
        this.typ = typ;
    }
    @Override
    public String toString() {
        return this.typ.toString() + " " + name;
    }
    @Override
    public PrgState execute(PrgState state) throws IdAlreadyExistsException {
        MyDict<String, Value> symTbl = state.getSymTable();
        symTbl.add(this.name, this.typ.defaultValue());
        return state;
    }
    @Override
    public Stmt deepCopy() {
        // name is a string => immutable
        return new VarDeclStmt(this.name, this.typ.deepCopy());
    }
}
