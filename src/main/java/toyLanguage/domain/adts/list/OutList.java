package toyLanguage.domain.adts.list;

import toyLanguage.domain.myExceptions.EmptyListException;
import toyLanguage.domain.myExceptions.IndexOutOfBoundsException;
import toyLanguage.domain.values.Value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OutList<E extends Value> implements MyList<E> {
    private List<E> outList;
    private String dataTypeName = "OutList";

    public OutList() {
        outList = new ArrayList<>();
    }

    @Override
    public void append(E elem) {
        this.outList.add(elem);
    }
    @Override
    public E get(int index) throws IndexOutOfBoundsException, EmptyListException {
        if (this.outList.isEmpty())
            throw new EmptyListException();
        if (index < 0 || index >= this.outList.size())
            throw new IndexOutOfBoundsException(index);
        return this.outList.get(index);
    }
    @Override
    public E remove(int index) throws IndexOutOfBoundsException, EmptyListException {
        if (this.outList.isEmpty())
            throw new EmptyListException();
        if (index < 0 || index >= this.outList.size())
            throw new IndexOutOfBoundsException(index);
        return this.outList.remove(index);
    }
    @Override
    public int size() {
        return this.outList.size();
    }
    @Override
    public boolean isEmpty() {
        return this.outList.isEmpty();
    }
    @Override
    public String toString() {
        String contents = this.outList.stream()
                                      .map(element -> element.toString())
                                      .collect(Collectors.joining(", "));
        return "{ " + contents + " }";
    }
    @Override
    public MyList<E> deepCopy() {
        MyList<E> copyList = new OutList<>();
        for (E elem : this.outList) {
            copyList.append((E)elem.deepCopy());
        }
        return copyList;
    }
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<E> actualIterator = outList.iterator();

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
                throw new UnsupportedOperationException("You cannot remove elements from the OutList while iterating.");
            }
        };
    }
    @Override
    public String getDataTypeAsString() {
        return this.dataTypeName;
    }
}
