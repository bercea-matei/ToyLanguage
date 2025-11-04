package toy_language.domain.adts.stack;

import java.util.ArrayDeque;
import java.util.Deque;

import toy_language.domain.my_exceptions.EmptyStackException;
import toy_language.domain.statements.Stmt;

public class ExeStk<E extends Stmt> implements MyStack<E> {
    private final Deque<E> exeStk;

    public ExeStk() {
        this.exeStk = new ArrayDeque<>();
    }
    @Override
    public void push(E elem) {
        this.exeStk.push(elem);
    }
    @Override
    public E pop() throws EmptyStackException {
        if (exeStk.isEmpty())
            throw new EmptyStackException();
        return this.exeStk.pop();
    }
    @Override
    public E top() throws EmptyStackException {
        if (this.exeStk.isEmpty()) {
            throw new EmptyStackException();
        }
        return this.exeStk.peek();
    }
    @Override
    public boolean isEmpty() {
        return exeStk.isEmpty();
    }
    @Override
    public String toString() {
        StringBuilder strExeStack = new StringBuilder();
        
        strExeStack.append("{ ");
        for (E element : this.exeStk) {
            strExeStack.append(element.toString());
            strExeStack.append(" | ");
        }
        if (this.exeStk.size() > 0)
            strExeStack.setLength(strExeStack.length() - 3);
        strExeStack.append(" }");
        
        return strExeStack.toString();
    }
    @Override
    public MyStack<E> deepCopy() {
        MyStack<E> copyStk = new ExeStk<>();
        for (E element : this.exeStk) {
            copyStk.push((E)(element.deepCopy()));
        }
        return copyStk;
    }
}
