package toyLanguage.domain.adts.list;

import toyLanguage.domain.myExceptions.EmptyListException;
import toyLanguage.domain.myExceptions.IndexOutOfBoundsException;

public interface MyList<E> extends Iterable<E>{

    public void append(E elem);
    public E get(int index)throws IndexOutOfBoundsException, EmptyListException;
    public E remove(int index) throws IndexOutOfBoundsException, EmptyListException;
    public int size();
    public boolean isEmpty();
    public MyList<E> deepCopy();
}
