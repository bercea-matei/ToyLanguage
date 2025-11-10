package toyLanguage.domain.adts.stack;

import toyLanguage.domain.myExceptions.EmptyStackException;

public interface MyStack<E> extends Iterable<E> {

    public void push(E elem);
    public E pop() throws EmptyStackException;
    public E top() throws EmptyStackException;
    public boolean isEmpty();
    public MyStack<E> deepCopy();
}
