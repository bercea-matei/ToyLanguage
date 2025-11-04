package toy_language.domain.types;

import toy_language.domain.values.Value;

public interface Type {
    public boolean equals(Object another);
    public String toString();
    public Type deepCopy();
    public Value defaultValue();
}

