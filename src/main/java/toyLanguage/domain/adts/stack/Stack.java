package toyLanguage.domain.adts.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.myExceptions.EmptyStackException;

public class Stack<E> implements MyStack<E> {
    private final Deque<E> stk;
    private String dataTypeName = "Stack";

    public Stack() {
        this.stk = new ArrayDeque<>();
    }
    @Override
    public synchronized void push(E elem) {
        this.stk.push(elem);
    }
    @Override
    public synchronized E pop() throws EmptyStackException {
        if (stk.isEmpty())
            throw new EmptyStackException();
        return this.stk.pop();
    }
    @Override
    public synchronized E top() throws EmptyStackException {
        if (this.stk.isEmpty()) {
            throw new EmptyStackException();
        }
        return this.stk.peek();
    }
    @Override
    public synchronized boolean isEmpty() {
        return stk.isEmpty();
    }
    @Override
    public synchronized String toString() {
        StringBuilder strExeStack = new StringBuilder();
        
        strExeStack.append("{ ");
        for (E element : this.stk) {
            strExeStack.append(element.toString());
            strExeStack.append(" | ");
        }
        if (this.stk.size() > 0)
            strExeStack.setLength(strExeStack.length() - 3);
        strExeStack.append(" }");
        
        return strExeStack.toString();
    }
    @Override
    public synchronized MyStack<E> deepCopy() {
        MyStack<E> newStack = new Stack<>();
        List<E> temp = new ArrayList<>(this.stk);
        Collections.reverse(temp); 
        for (E item : temp) {
            if (item instanceof MyDict) {
                newStack.push((E) ((MyDict<?, ?>) item).deepCopy());
            } else {
                newStack.push(item);
            }
        }
        return newStack;
    }
    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<E> actualIterator = stk.iterator();

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
