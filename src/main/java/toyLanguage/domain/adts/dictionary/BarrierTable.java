package toyLanguage.domain.adts.dictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import toyLanguage.domain.myExceptions.IdAlreadyExistsException;
import toyLanguage.domain.myExceptions.OperationNotSupportedException;
import toyLanguage.domain.myExceptions.IdNotFoundException;

public class BarrierTable<K,V> implements MyDict<K,V> {
    private Map<K,V> myBarriers;
    private String dataTypeName = "BarrierTable";

    public BarrierTable() {
        this.myBarriers = new HashMap<>();
    }
    @Override
    public synchronized V lookup(K id) throws IdNotFoundException {
        if (this.myBarriers.containsKey(id)) {
            return this.myBarriers.get(id);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public synchronized void update(K id, V val) throws IdNotFoundException, OperationNotSupportedException {
        if (this.myBarriers.containsKey(id)) {
            this.myBarriers.replace(id, val);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public synchronized void add(K id, V val) throws IdAlreadyExistsException {
        if (! this.myBarriers.containsKey(id)) {
            this.myBarriers.putIfAbsent(id, val);
        } else {
            throw new IdAlreadyExistsException(id.toString());
        }
    }
    @Override
    public synchronized boolean isKeyDef(K id) {
        return this.myBarriers.containsKey(id);
    }
    @Override
    public synchronized String toString() {
        StringBuilder printSymTbl = new StringBuilder();
        printSymTbl.append("{ ");
        for ( Map.Entry<K, V> entry_ : this.myBarriers.entrySet()) {
            printSymTbl.append(entry_.getKey().toString());
            printSymTbl.append("-->");
            printSymTbl.append(entry_.getValue().toString());
            printSymTbl.append(", ");
        }
        if (this.myBarriers.size() > 0)
            printSymTbl.setLength(printSymTbl.length()-2);
        printSymTbl.append(" }");
        return printSymTbl.toString();
    }
    @Override
    public synchronized void remove(K id) throws IdNotFoundException{
        if (!myBarriers.containsKey(id)) {
            throw new IdNotFoundException(id.toString());
        }
        myBarriers.remove(id);
    }

    @Override
    public synchronized MyDict<K,V> deepCopy() {
        MyDict<K,V> copy = new BarrierTable<>();
        for (Map.Entry<K, V> entry_ : this.myBarriers.entrySet())
        {
            try {
                copy.add(entry_.getKey(), (V) entry_.getValue());
            } catch (IdAlreadyExistsException e) {
                //this should never occur
                throw new AssertionError("An impossible error occurred during deep copy key/value add: " + e.getMessage(), e);
            }
        }
        return copy;
    }
    @Override
    public synchronized Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
            private final Iterator<Map.Entry<K, V>> actualIterator = myBarriers.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return actualIterator.hasNext();
            }
            @Override
            public Map.Entry<K, V> next() {
                return actualIterator.next();
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException("You cannot remove entries from the Symbol Table while iterating.");
            }
        };
    }
    @Override
    public synchronized String getDataTypeAsString() {
        return this.dataTypeName;
    }
    @Override
    public synchronized Map<K, V> getContent() {
        return new HashMap<>(this.myBarriers); 
    }
    @Override
    public synchronized boolean isEmpty() {
        return this.myBarriers.isEmpty();
    }
}
