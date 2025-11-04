package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.prg_state.PrgState;
import toyLanguage.domain.adts.stack.MyStack;

public class CompStmt implements Stmt{
    private Stmt first;
    private Stmt snd;
    
    public CompStmt(Stmt first, Stmt snd) {
        this.first = first;
        this.snd = snd;
    }

    @Override
    public String toString() {
        return "("+first.toString() + " ; " + snd.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws ToyLanguageExceptions {
        MyStack<Stmt> stk=state.getExeStk();
        stk.push(snd);
        stk.push(first);
        return state;
    }
    public Stmt deepCopy() {
        return new CompStmt(this.first.deepCopy(), this.snd.deepCopy());
    }
}
