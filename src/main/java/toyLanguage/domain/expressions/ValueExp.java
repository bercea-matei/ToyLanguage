package toyLanguage.domain.expressions;

import toyLanguage.domain.myExceptions.IdNotFoundException;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.values.*;

public class ValueExp implements Exp{
    Value val;
    public ValueExp(Value val) {
        this.val = val;
    }

    @Override
    public Value eval(MyDict<String,Value> tbl) throws IdNotFoundException{
        return val;
    }
    @Override
    public String toString() {
        return this.val.toString();
    }
    public Exp deepCopy() {
        return new ValueExp(this.val.deepCopy());
    }
}
