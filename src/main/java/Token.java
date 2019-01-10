public class Token {

    public enum Type {
        OPERATOR,
        SYMBOLE,
        NUMBER
    }

    private final String symbole;
    private final Type type;

    public Token(String symbol, Type type) {
        this.symbole = symbol;
        this.type = type;
    }

    public String getSymbole() {
        return symbole;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "symbol='" + symbole + '\'' +
                ", type=" + type +
                '}';
    }
}