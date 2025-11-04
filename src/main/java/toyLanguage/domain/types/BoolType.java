package toy_language.domain.types;

import toy_language.domain.values.BoolValue;
import toy_language.domain.values.Value;

public class BoolType implements Type {
    private boolean defaultVal = false;

    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolType)
            return true;
        else
            return false;
    }
    @Override
    public String toString() {
        return "bool";
    }
    @Override
    public Type deepCopy() {
        return new BoolType();
    }
    @Override
    public Value defaultValue() {
        return new BoolValue(this.defaultVal);
    }
}
