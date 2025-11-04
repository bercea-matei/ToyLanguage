package toy_language.domain.expressions.operators;

public enum LogicOp {
    AND("&&"),
    OR("||");

    private final String symbol;

    private LogicOp(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
