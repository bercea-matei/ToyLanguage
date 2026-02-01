package toyLanguage.domain.adts.dictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import toyLanguage.domain.myExceptions.IdAlreadyExistsException;
import toyLanguage.domain.myExceptions.IdNotFoundException;

import toyLanguage.domain.values.Value;

public class SymbolTable<K,V extends Value> implements MyDict<K,V> {
    private Map<K,V> myDict;
    private String dataTypeName = "SymbolTable";

    public SymbolTable() {
        this.myDict = new HashMap<>();
    }
    @Override
    public V lookup(K id) throws IdNotFoundException {
        if (this.myDict.containsKey(id)) {
            return this.myDict.get(id);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public void update(K id, V val) throws IdNotFoundException {
        if (this.myDict.containsKey(id)) {
            this.myDict.replace(id, val);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public void add(K id, V val) throws IdAlreadyExistsException {
        if (! this.myDict.containsKey(id)) {
            this.myDict.putIfAbsent(id, val);
        } else {
            throw new IdAlreadyExistsException(id.toString());
        }
    }
    @Override
    public boolean isKeyDef(K id) {
        return this.myDict.containsKey(id);
    }
    @Override
    public String toString() {
        StringBuilder printSymTbl = new StringBuilder();
        printSymTbl.append("{ ");
        for ( Map.Entry<K, V> entry_ : this.myDict.entrySet()) {
            printSymTbl.append(entry_.getKey().toString());
            printSymTbl.append("-->");
            printSymTbl.append(entry_.getValue().toString());
            printSymTbl.append(", ");
        }
        if (this.myDict.size() > 0)
            printSymTbl.setLength(printSymTbl.length()-2);
        printSymTbl.append(" }");
        return printSymTbl.toString();
    }
    @Override
    public void remove(K id) throws IdNotFoundException{
        if (!myDict.containsKey(id)) {
            throw new IdNotFoundException(id.toString());
        }
        myDict.remove(id);
    }

    @Override
    public MyDict<K,V> deepCopy() {
        MyDict<K,V> copy = new SymbolTable<>();
        for (Map.Entry<K, V> entry_ : this.myDict.entrySet())
        {
            //The key is a string => imutable => we don't need to copy it
            try {
                copy.add(entry_.getKey(), (V) entry_.getValue().deepCopy());
            } catch (IdAlreadyExistsException e) {
                //this should never occur
                throw new AssertionError("An impossible error occurred during deep copy key/value add: " + e.getMessage(), e);
            }
        }
        return copy;
    }
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
            private final Iterator<Map.Entry<K, V>> actualIterator = myDict.entrySet().iterator();
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
    public String getDataTypeAsString() {
        return this.dataTypeName;
    }
    @Override
    public Map<K, V> getContent() {
        return this.myDict;
    }
    @Override
    public boolean isEmpty() {
        return this.myDict.isEmpty();
    }
}
