package toyLanguage.domain.adts.heapMap;

import java.util.Map;

import toyLanguage.domain.myExceptions.IdNotFoundException;
import toyLanguage.domain.myExceptions.OperationNotSupportedException;

public interface MyHeap<K, V> extends Iterable<Map.Entry<K, V>> {

    public V lookup(K id) throws IdNotFoundException;
    public void update(K id, V val) throws IdNotFoundException;
    public int allocate(V val);
    public void remove(K id) throws IdNotFoundException, OperationNotSupportedException;
    public boolean isKeyDef(K id);
    public MyHeap<K,V> deepCopy();
    public String getDataTypeAsString();
    //public Map<K,V> unsafeGarbageCollector(List<K> symTableAddr, Map<K,V> heap);
    //List<Integer> getAddrFromSymTable(Collection<V> symTableValues);
    public void setContent(Map<K, V> newContent);
    public Map<K, V> getContent();
    public boolean isEmpty();
}
