public class Token{
    private final Tipo tipo;
    private final String lexema;

    Token(Tipo tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    @Override
    public String toString() {
        return tipo.toString() + "[" + lexema + "]";
    }
}