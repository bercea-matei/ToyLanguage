package toy_language.domain.adts.dictionary;

import java.util.HashMap;
import java.util.Map;

import toy_language.domain.my_exceptions.IdAlreadyExistsException;
import toy_language.domain.my_exceptions.IdNotFoundException;

import toy_language.domain.values.Value;

public class SymbolTable<K,V extends Value> implements MyDict<K,V> {
    private Map<K,V> myDict;

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
    public boolean isVarDef(K id) {
        return this.myDict.containsKey(id);
    }
    @Override
    public String toString() {
        StringBuilder printSymTbl = new StringBuilder();
        printSymTbl.append("{ ");
        for ( Map.Entry<K, V> entry_ : this.myDict.entrySet()) {
            printSymTbl.append(entry_.getKey().toString());
            printSymTbl.append("->");
            printSymTbl.append(entry_.getValue().toString());
            printSymTbl.append(", ");
        }
        if (this.myDict.size() > 0)
            printSymTbl.setLength(printSymTbl.length()-2);
        printSymTbl.append(" }");
        return printSymTbl.toString();
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
                //this should never really occur
                System.out.println(e);
            }
        }
        return copy;
    }

}
