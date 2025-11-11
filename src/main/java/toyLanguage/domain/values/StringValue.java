package toyLanguage.domain.values;

import toyLanguage.domain.types.StringType;
import toyLanguage.domain.types.Type;


public class StringValue implements Value {
    private String val;

    public StringValue(String s) {
        this.val = s;
    }
    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
    @Override
    public Type getType() {
        return new StringType();
    }
    public String getValue() {
        return val;
    }
    @Override
    public StringValue deepCopy(){
        return new StringValue(this.val);
    }
    @Override
    public boolean equals(Object another) {
        if (another instanceof IntValue)
            if (((StringValue)another).getValue() == this.val)
                return true;
            else
                return false;
        else
            return false;
    }
}
