package toyLanguage.domain.expressions.operators;

public enum RelOp {
    LT("<"),
    LTE("<="),
    EQ("=="),
    NEQ("!="),
    GT(">"),
    GTE(">=");

    private final String symbol;

    RelOp(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
