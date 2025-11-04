package toy_language.domain.adts.stack;

import toy_language.domain.my_exceptions.EmptyStackException;

public interface MyStack<E> {

    public void push(E elem);
    public E pop() throws EmptyStackException;
    public E top() throws EmptyStackException;
    public boolean isEmpty();
    public MyStack<E> deepCopy();
}
