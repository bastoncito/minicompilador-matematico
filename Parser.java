import java.util.LinkedList;
public class Parser{
    public Nodo parse(LinkedList<Token> tokens) throws SintaxisInvalidaException {
        LinkedList<Token> copia = new LinkedList<>(tokens);
        Nodo AST;
        if(!copia.isEmpty()){
            AST = parseEcuacion(copia);
            if(copia.isEmpty()){
                return AST;
            }
        }
        throw new SintaxisInvalidaException("Parseo incompleto");
    }

    private Nodo parseEcuacion(LinkedList<Token> tokens) throws SintaxisInvalidaException {
        if (tokens.size() >= 3 && tokens.get(0).getTipo() == Tipo.VARIABLE && tokens.get(1).getTipo() == Tipo.SIGNO_IGUAL) {
            String variable = tokens.poll().getLexema();
            tokens.poll();
            return new NodoAsignacion(variable, parseSumaResta(tokens));
        } else {
            return parseSumaResta(tokens);
        }
    }

    private Nodo parseSumaResta(LinkedList<Token> tokens) throws SintaxisInvalidaException {
        Nodo retorno = parseMultiDiv(tokens);
        while (!tokens.isEmpty() && tokens.peek().getTipo() == Tipo.OPERADOR) {
            String op = tokens.peek().getLexema();
            if (op.equals("+") || op.equals("-")) {
                tokens.poll();
                Nodo aux = parseMultiDiv(tokens);
                retorno = new NodoOperacion(retorno, op, aux);
            }else{
                break;
            }
        }
        return retorno;
    }

    private Nodo parseMultiDiv(LinkedList<Token> tokens) throws SintaxisInvalidaException {
        Nodo retorno = parseExponencial(tokens);
        while (!tokens.isEmpty() && tokens.peek().getTipo() == Tipo.OPERADOR) {
            String op = tokens.peek().getLexema();
            if (op.equals("*") || op.equals("/")) {
                tokens.poll();
                Nodo factor2 = parseExponencial(tokens);
                retorno = new NodoOperacion(retorno, op, factor2);
            }else{
                break;
            }
        }
        return retorno;
    }

    private Nodo parseExponencial(LinkedList<Token> tokens) throws SintaxisInvalidaException {
        Nodo base = parseOperando(tokens);
        if(!tokens.isEmpty() && tokens.peek().getTipo() == Tipo.OPERADOR){
            if(tokens.peek().getLexema().equals("^")){
                tokens.poll();
                Nodo exponente = parseExponencial(tokens);
                return new NodoOperacion(base, "^", exponente);
            }
        }
        return base;
    }

    private Nodo parseOperando(LinkedList<Token> tokens) throws SintaxisInvalidaException {
        if(tokens.isEmpty()) {
            throw new SintaxisInvalidaException("Operación incompleta");
        }
        Token token = tokens.peek();
        if(token.getTipo() == Tipo.OPERADOR && token.getLexema().equals("-")){
            tokens.poll();
            return new NodoNegativo(parseOperando(tokens));
        }
        if(token.getTipo() == Tipo.PARENTESIS_IZQ){
            tokens.poll();
            Nodo expresion = parseSumaResta(tokens);
            if(!tokens.isEmpty()){
                Token aux = tokens.poll();
                if(aux.getTipo() == Tipo.PARENTESIS_DER){
                    return expresion;
                }
            }
            throw new SintaxisInvalidaException("Paréntesis incompleto");
        }
        Token aux = tokens.poll();
        if(aux.getTipo() == Tipo.VARIABLE) return new NodoVariable(aux.getLexema());
        if(aux.getTipo() == Tipo.NUMERO) return new NodoNumero(Integer.parseInt(aux.getLexema()));
        throw new SintaxisInvalidaException("Operando inválido");
    }
}
