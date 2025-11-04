package toy_language.domain.expressions;

import toy_language.domain.my_exceptions.IdNotFoundException;
import toy_language.domain.adts.dictionary.MyDict;
import toy_language.domain.values.*;

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
