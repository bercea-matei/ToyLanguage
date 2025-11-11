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
    @Override
    public boolean equals(Object another) {
        if (another instanceof IntValue)
            if (((BoolValue)another).getValue() == this.val)
                return true;
            else
                return false;
        else
            return false;
    } 
}
