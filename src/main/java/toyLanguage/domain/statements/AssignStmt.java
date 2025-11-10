package toyLanguage.domain.statements;

import toyLanguage.domain.values.Value;
import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.types.Type;

public class AssignStmt implements Stmt{
    private String id;
    private Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }
    @Override
    public String toString() {
        return this.id+" = "+ this.exp.toString();
    }
    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyDict<String,Value> symTbl= state.getSymTable();
        if (symTbl.isVarDef(this.id)) {
            Value val = this.exp.eval(symTbl);
            Type typId= (symTbl.lookup(this.id)).getType();
            if ((val.getType()).equals(typId)) {
                symTbl.update(this.id, val);
            } else {
                throw new MissmatchValueException(typId, val.getType());
            }
        } else { 
            throw new IdNotDefinedException(this.id);
        }
        return state;
    }
    public Stmt deepCopy() {
        return new AssignStmt(this.id, this.exp.deepCopy());
    }
}
