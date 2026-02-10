package toyLanguage.domain.adts.pair;

public class Pair<K, V> {
    private K first;
    private V second;

    public Pair (K f, V s) {
        this.first = f;
        this.second = s;
    }

    public K getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }

    public void setFirst(K f) {
        this.first = f;
    }

    public void setSecond(V s) {
        this.second = s;
    }

    @Override
    public String toString() {
        return "<" + first.toString() + "," + second.toString() + ">";
    }

}
