package toyLanguage.domain.statements;

import toyLanguage.domain.myExceptions.*;
import toyLanguage.domain.expressions.Exp;
import toyLanguage.domain.expressions.RelExp;
import toyLanguage.domain.prg_state.PrgState;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.adts.stack.MyStack;
import toyLanguage.domain.types.BoolType;
import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.Type;


public class SwitchStmt implements Stmt{
    Exp exp;
    Exp exp1;
    Exp exp2;
    Stmt stmt1;
    Stmt stmt2;
    Stmt stmt3;

    public SwitchStmt(Exp exp,Exp exp1,Exp exp2, Stmt stmt1, Stmt stmt2, Stmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }
    @Override
    public String toString() {
        return "switch("+ exp.toString()+") (case (" +exp1.toString() + "):" + stmt1.toString()+") (case (" +exp2.toString() + "):" + stmt2.toString()+")"+"(default"  + ":" + stmt3.toString()+")";
    }
    public PrgState execute(PrgState state) throws ToyLanguageExceptions{
        MyStack<Stmt> exeStk = state.getExeStk();

        Stmt ifStmt = new IfStmt(
            new RelExp("==", exp, exp1), 
            stmt1, 
            new IfStmt(
                new RelExp("==", exp, exp2), 
                stmt2, stmt3));

        exeStk.push(ifStmt);
        return null;


    }
    @Override
    public Stmt deepCopy() {
        return new SwitchStmt(this.exp.deepCopy(), this.exp1.deepCopy(), this.exp2.deepCopy(), this.stmt1.deepCopy(), this.stmt2.deepCopy(), this.stmt3.deepCopy());
    }
    @Override
    public MyDict<String,Type> typecheck(MyDict <String,Type> typeEnv) throws IdNotFoundException, IdNotDefinedException, MissmatchTypeException, WhichOperandExceptionExtend, IdAlreadyExistsException {
        Type typexp=exp.typecheck(typeEnv);
        if (!typexp.equals(new IntType()))
            throw new MissmatchTypeException(this.whatAmI(), new BoolType().toString(), typexp.toString());
        Type typexp1=exp1.typecheck(typeEnv);
        if (!typexp.equals(new IntType()))
            throw new MissmatchTypeException(this.whatAmI(), new BoolType().toString(), typexp1.toString());
        Type typexp2=exp2.typecheck(typeEnv);
        if (!typexp.equals(new IntType()))
            throw new MissmatchTypeException(this.whatAmI(), new BoolType().toString(), typexp2.toString());

        stmt1.typecheck(typeEnv.deepCopy());
        stmt2.typecheck(typeEnv.deepCopy());
        stmt3.typecheck(typeEnv.deepCopy());
        return typeEnv;
    } 
    private String whatAmI() {
        return "SwitchStmt";
    }

}
