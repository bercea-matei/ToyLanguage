package toyLanguage.domain.values;

import toyLanguage.domain.types.RefType;
import toyLanguage.domain.types.Type;

public class RefValue implements Value{
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public String toString() {
        String result = "( " + String.valueOf(this.address) +
            "," + locationType.toString() + " )";
        return result;
    }
    @Override
    public Type getType() { 
        return new RefType(locationType);
    }
    public int getAddr() {
        return this.address;
    }
    public Type getLocationType() {
        return this.locationType;
    }
    @Override
    public Value deepCopy() {
        return new RefValue(this.address, this.locationType.deepCopy());
    }
    @Override
    public boolean equals(Object another) {
        if (another instanceof RefValue)
            if (((RefValue)another).getAddr() == this.address)
                return true;
            else
                return false;
        else
            return false;
    } 
}
