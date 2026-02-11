package toyLanguage.domain.adts.dictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import toyLanguage.domain.myExceptions.IdAlreadyExistsException;
import toyLanguage.domain.myExceptions.OperationNotSupportedException;
import toyLanguage.domain.myExceptions.IdNotFoundException;

public class LockTable<K,V> implements MyDict<K,V> {
    private Map<K,V> myLock;
    private String dataTypeName = "LockTable";

    public LockTable() {
        this.myLock = new HashMap<>();
    }
    @Override
    public synchronized V lookup(K id) throws IdNotFoundException {
        if (this.myLock.containsKey(id)) {
            return this.myLock.get(id);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public synchronized void update(K id, V val) throws IdNotFoundException, OperationNotSupportedException {
        if (this.myLock.containsKey(id)) {
            this.myLock.replace(id, val);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public synchronized void add(K id, V val) throws IdAlreadyExistsException {
        if (! this.myLock.containsKey(id)) {
            this.myLock.putIfAbsent(id, val);
        } else {
            throw new IdAlreadyExistsException(id.toString());
        }
    }
    @Override
    public synchronized boolean isKeyDef(K id) {
        return this.myLock.containsKey(id);
    }
    @Override
    public synchronized String toString() {
        StringBuilder printSymTbl = new StringBuilder();
        printSymTbl.append("{ ");
        for ( Map.Entry<K, V> entry_ : this.myLock.entrySet()) {
            printSymTbl.append(entry_.getKey().toString());
            printSymTbl.append(" --> ");
            printSymTbl.append(entry_.getValue().toString());
            printSymTbl.append(", ");
        }
        if (this.myLock.size() > 0)
            printSymTbl.setLength(printSymTbl.length()-2);
        printSymTbl.append(" }");
        return printSymTbl.toString();
    }
    @Override
    public synchronized void remove(K id) throws IdNotFoundException{
        if (!myLock.containsKey(id)) {
            throw new IdNotFoundException(id.toString());
        }
        myLock.remove(id);
    }

    @Override
    public synchronized MyDict<K,V> deepCopy() {
        MyDict<K,V> copy = new LockTable<>();
        for (Map.Entry<K, V> entry_ : this.myLock.entrySet())
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
        return new HashMap<>(this.myLock).entrySet().iterator();
    }
    @Override
    public synchronized String getDataTypeAsString() {
        return this.dataTypeName;
    }
    @Override
    public synchronized Map<K, V> getContent() {
        // Return a copy so synchronized is not bypassed
        return new HashMap<>(this.myLock); 
    }
    @Override
    public synchronized boolean isEmpty() {
        return this.myLock.isEmpty();
    }
}
