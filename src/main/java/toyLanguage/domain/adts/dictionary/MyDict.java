package toyLanguage.domain.adts.dictionary;

import java.util.Map;

import toyLanguage.domain.myExceptions.IdAlreadyExistsException;
import toyLanguage.domain.myExceptions.IdNotFoundException;

public interface MyDict<K, V> extends Iterable<Map.Entry<K, V>> {

    public V lookup(K id) throws IdNotFoundException;
    public void update(K id, V val) throws IdNotFoundException;
    public void add(K id, V val) throws IdAlreadyExistsException;
    public boolean isVarDef(K id);
    public MyDict<K,V> deepCopy();
}
