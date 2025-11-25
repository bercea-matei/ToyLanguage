package toyLanguage.domain.adts.heapMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import toyLanguage.domain.myExceptions.IdNotFoundException;
import toyLanguage.domain.values.Value;
import toyLanguage.domain.values.RefValue;

public class HeapTable<V extends Value> implements MyHeap<Integer,V> {
    private Map<Integer,V> myDict;
    private String dataTypeName;
    private int freeAddrCounter;

    public HeapTable() {
        this.myDict = new HashMap<>();
        this.dataTypeName = "HeapTable";
        this.freeAddrCounter = 1;
    }
    @Override
    public V lookup(Integer id) throws IdNotFoundException {
        if (this.isKeyDef(id)) {
            return this.myDict.get(id);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public void update(Integer id, V val) throws IdNotFoundException {
        if (this.isKeyDef(id)) {
            this.myDict.replace(id, val);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public int allocate(V val) {
        this.myDict.putIfAbsent(this.freeAddrCounter, val);
        this.freeAddrCounter += 1;
        return freeAddrCounter - 1;
    }
    @Override
    public boolean isKeyDef(Integer id){
        if (id < 1)
            return false;
        return this.myDict.containsKey(id);
    }
    @Override
    public String toString() {
        StringBuilder printSymTbl = new StringBuilder();
        printSymTbl.append("{ ");
        for ( Map.Entry<Integer, V> entry_ : this.myDict.entrySet()) {
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
    public void remove(Integer id) throws IdNotFoundException{
        if (! isKeyDef(id)) {
            throw new IdNotFoundException(id.toString());
        }
        myDict.remove(id);
    }

    @Override
    public MyHeap<Integer,V> deepCopy() {
        MyHeap<Integer,V> copy = new HeapTable<>();
        for (Map.Entry<Integer, V> entry_ : this.myDict.entrySet())
        {
            //The key is a string => imutable => we don't need to copy it
            copy.allocate((V) entry_.getValue().deepCopy());
        }
        return copy;
    }
    @Override
    public Iterator<Map.Entry<Integer, V>> iterator() {
        return new Iterator<Map.Entry<Integer, V>>() {
            private final Iterator<Map.Entry<Integer, V>> actualIterator = myDict.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return actualIterator.hasNext();
            }
            @Override
            public Map.Entry<Integer, V> next() {
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
    public void setContent(Map<Integer, V> newContent) {
        this.myDict = newContent;
    }
    @Override
    public Map<Integer, V> getContent() {
        return this.myDict;
    }
}
