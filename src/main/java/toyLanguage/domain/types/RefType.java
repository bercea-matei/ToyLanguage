package toyLanguage.domain.types;

import toyLanguage.domain.values.RefValue;
import toyLanguage.domain.values.Value;

public class RefType implements Type{
    private Type inner;
    private int defaultValue = 0;

    public RefType(Type inner) {
        this.inner=inner;
    }
    @Override
    public boolean equals(Object another) {
        if (another instanceof RefType)
            return inner.equals(((RefType)another).getInner());
        else
        return false;
    }
    @Override
    public String toString() {
        return "Ref(" +inner.toString()+")";
    }

    @Override
    public Value defaultValue() { 
        return new RefValue(this.defaultValue,inner);
    }
    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
    public Type getInner() {
        return inner;
    }
}
