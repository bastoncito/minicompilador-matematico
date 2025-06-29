import java.util.LinkedList;
public class Tokenizador {
    private Tipo identificarTipo(char caracter) throws LexicoInvalidoException {
        if(Character.isLetter(caracter)) {
            return Tipo.VARIABLE;
        }else if("+/-*^".contains(Character.toString(caracter))) {
            return Tipo.OPERADOR;
        }else if(caracter == '(') {
            return Tipo.PARENTESIS_IZQ;
        }else if(caracter == ')') {
            return Tipo.PARENTESIS_DER;
        }else if(caracter == '='){
            return Tipo.SIGNO_IGUAL;
        }
        throw new LexicoInvalidoException("No se permite el carácter " + caracter);
    }

    public LinkedList<Token> tokenizar(String cadena) throws LexicoInvalidoException {
        if(cadena.isEmpty()) {
            throw new LexicoInvalidoException("Cadena vacía");
        }
        LinkedList<Token> tokens = new LinkedList<>();
        char aux;
        int i = 0;
        cadena = cadena.trim(); //remover espacios en blanco al principo y final, si es que hay
        while(i < cadena.length()){
            aux = cadena.charAt(i);
            if(Character.isWhitespace(aux)){
                i++;
            }else if(Character.isDigit(aux)){
                String num = "";
                while(i < cadena.length()){
                    aux = cadena.charAt(i);
                    if(!Character.isDigit(aux)){
                        break;
                    }
                    num += String.valueOf(aux);
                    i++;
                }
                tokens.add(new Token(Tipo.NUMERO, num));
            }else{
                tokens.add(new Token(identificarTipo(aux), String.valueOf(aux)));
                i++;
            }
        }
        return tokens;
    }
}

