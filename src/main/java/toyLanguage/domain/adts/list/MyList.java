package toy_language.domain.adts.list;

import toy_language.domain.my_exceptions.EmptyListException;
import toy_language.domain.my_exceptions.IndexOutOfBoundsException;

public interface MyList<E> {

    public void append(E elem);
    public E get(int index)throws IndexOutOfBoundsException, EmptyListException;
    public E remove(int index) throws IndexOutOfBoundsException, EmptyListException;
    public int size();
    public boolean isEmpty();
    public MyList<E> deepCopy();
}
