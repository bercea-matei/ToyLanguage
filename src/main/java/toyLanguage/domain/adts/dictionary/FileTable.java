package toyLanguage.domain.adts.dictionary;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import toyLanguage.domain.myExceptions.IdAlreadyExistsException;
import toyLanguage.domain.myExceptions.OperationNotSupportedException;
import toyLanguage.domain.myExceptions.IdNotFoundException;

public class FileTable<K,V> implements MyDict<K,V> {
    private Map<K,V> myFiles;
    private String dataTypeName = "FileTable";

    public FileTable() {
        this.myFiles = new HashMap<>();
    }
    @Override
    public V lookup(K id) throws IdNotFoundException {
        if (this.myFiles.containsKey(id)) {
            return this.myFiles.get(id);
        } else {
            throw new IdNotFoundException(id.toString());
        }
    }
    @Override
    public void update(K id, V val) throws IdNotFoundException, OperationNotSupportedException {
        throw new OperationNotSupportedException("update", this.getDataTypeAsString());
        /*if (this.myFiles.containsKey(id)) {
            this.myFiles.replace(id, val);
        } else {
            throw new IdNotFoundException(id.toString());
        }*/
    }
    @Override
    public void add(K id, V val) throws IdAlreadyExistsException {
        if (! this.myFiles.containsKey(id)) {
            this.myFiles.putIfAbsent(id, val);
        } else {
            throw new IdAlreadyExistsException(id.toString());
        }
    }
    @Override
    public boolean isKeyDef(K id) {
        return this.myFiles.containsKey(id);
    }
    @Override
    public String toString() {
        StringBuilder printSymTbl = new StringBuilder();
        printSymTbl.append("{ ");
        for ( Map.Entry<K, V> entry_ : this.myFiles.entrySet()) {
            printSymTbl.append(entry_.getKey().toString());
            printSymTbl.append("-->");
            printSymTbl.append(entry_.getValue().toString());
            printSymTbl.append(", ");
        }
        if (this.myFiles.size() > 0)
            printSymTbl.setLength(printSymTbl.length()-2);
        printSymTbl.append(" }");
        return printSymTbl.toString();
    }
    @Override
    public void remove(K id) throws IdNotFoundException{
        if (!myFiles.containsKey(id)) {
            throw new IdNotFoundException(id.toString());
        }
        myFiles.remove(id);
    }

    @Override
    public MyDict<K,V> deepCopy() {
        MyDict<K,V> copy = new FileTable<>();
        for (Map.Entry<K, V> entry_ : this.myFiles.entrySet())
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
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<Map.Entry<K, V>>() {
            private final Iterator<Map.Entry<K, V>> actualIterator = myFiles.entrySet().iterator();
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
}
