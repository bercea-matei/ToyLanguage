package toy_language.domain.adts.dictionary;

import toy_language.domain.my_exceptions.IdAlreadyExistsException;
import toy_language.domain.my_exceptions.IdNotFoundException;

public interface MyDict<K, V> {

    public V lookup(K id) throws IdNotFoundException;
    public void update(K id, V val) throws IdNotFoundException;
    public void add(K id, V val) throws IdAlreadyExistsException;
    public boolean isVarDef(K id);
    public MyDict<K,V> deepCopy();
}
