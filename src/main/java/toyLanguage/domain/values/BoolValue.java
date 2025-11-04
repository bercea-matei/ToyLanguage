package toyLanguage.domain.values;

import toyLanguage.domain.types.BoolType;
import toyLanguage.domain.types.Type;


public class BoolValue implements Value {
    private boolean val;

    public BoolValue(boolean b) {
        this.val = b;
    }
    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
    @Override
    public Type getType() {
        return new BoolType();
    }
    public boolean getValue() {
        return val;
    }
    @Override
    public BoolValue deepCopy(){
        return new BoolValue(this.val);
    }

}
