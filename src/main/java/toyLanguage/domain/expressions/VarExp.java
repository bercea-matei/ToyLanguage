package toyLanguage.domain.expressions;

import toyLanguage.domain.myExceptions.IdNotFoundException;
import toyLanguage.domain.adts.dictionary.MyDict;
import toyLanguage.domain.values.*;

public class VarExp implements Exp{
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyDict<String,Value> tbl) throws IdNotFoundException{
        return tbl.lookup(this.id);
    }
    @Override
    public String toString() {
        return id;
    }
    public Exp deepCopy() {
        return new VarExp(this.id);
    }
}
