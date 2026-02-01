package toyLanguage.domain.adts.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import toyLanguage.domain.myExceptions.EmptyStackException;
import toyLanguage.domain.statements.Stmt;

public class ExeStk<E extends Stmt> implements MyStack<E> {
    private final Deque<E> exeStk;
    private String dataTypeName = "ExeStk";

    public ExeStk() {
        this.exeStk = new ArrayDeque<>();
    }
    @Override
    public synchronized void push(E elem) {
        this.exeStk.push(elem);
    }
    @Override
    public synchronized E pop() throws EmptyStackException {
        if (exeStk.isEmpty())
            throw new EmptyStackException();
        return this.exeStk.pop();
    }
    @Override
    public synchronized E top() throws EmptyStackException {
        if (this.exeStk.isEmpty()) {
            throw new EmptyStackException();
        }
        return this.exeStk.peek();
    }
    @Override
    public synchronized boolean isEmpty() {
        return exeStk.isEmpty();
    }
    @Override
    public synchronized String toString() {
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
    public synchronized MyStack<E> deepCopy() {
        MyStack<E> copyStk = new ExeStk<>();
        for (E element : this.exeStk) {
            copyStk.push((E)(element.deepCopy()));
        }
        return copyStk;
    }
    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<E> actualIterator = exeStk.iterator();

            @Override
            public boolean hasNext() {
                return actualIterator.hasNext();
            }
            @Override
            public E next() {
                return actualIterator.next();
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Cannot remove from ExeStack during iteration.");
            }
        };
    }
    @Override
    public synchronized String getDataTypeAsString() {
        return this.dataTypeName;
    }
}
