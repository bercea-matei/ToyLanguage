package toyLanguage.domain.types;

import toyLanguage.domain.values.Value;
import toyLanguage.domain.values.StringValue;

public class StringType implements Type {
    private String defaultVal = "";

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringType)
            return true;
        else
            return false;
    }
    @Override
    public String toString() {
        return "String";
    }
    @Override
    public Type deepCopy() {
        return new StringType();
    }
    @Override
    public Value defaultValue() {
        return new StringValue(this.defaultVal);
    }
}
