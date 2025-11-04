package toyLanguage.domain.values;

import toyLanguage.domain.types.IntType;
import toyLanguage.domain.types.Type;


public class IntValue implements Value {
    private int val;

    public IntValue(int i) {
        this.val = i;
    }
    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
    @Override
    public Type getType() {
        return new IntType();
    }
    public int getValue() {
        return val;
    }
    @Override
    public Value deepCopy() {
        return new IntValue(this.val);
    }
}
