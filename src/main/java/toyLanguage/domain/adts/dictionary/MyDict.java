package toyLanguage.domain.adts.dictionary;

import java.util.Map;

import toyLanguage.domain.myExceptions.IdAlreadyExistsException;
import toyLanguage.domain.myExceptions.IdNotFoundException;
import toyLanguage.domain.myExceptions.OperationNotSupportedException;

public interface MyDict<K, V> extends Iterable<Map.Entry<K, V>> {

    public V lookup(K id) throws IdNotFoundException;
    public void update(K id, V val) throws IdNotFoundException, OperationNotSupportedException;
    public void add(K id, V val) throws IdAlreadyExistsException;
    public void remove(K id) throws IdNotFoundException, OperationNotSupportedException;
    public boolean isKeyDef(K id);
    public MyDict<K,V> deepCopy();
    public String getDataTypeAsString();
    public Map<K,V> getContent();
}
