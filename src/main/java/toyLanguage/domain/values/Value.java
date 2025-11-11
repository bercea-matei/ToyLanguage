package toyLanguage.domain.values;

import toyLanguage.domain.types.Type;

public interface Value{
    public String toString();
    public Type getType();
    public Value deepCopy();
    public boolean equals(Object another);
}
