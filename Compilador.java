import java.util.LinkedList;
public class Compilador {
    private final Tokenizador tokenizador = new Tokenizador();
    private final Parser parser = new Parser();
    private final Evaluador evaluador = new Evaluador();

    public void compilar(String cadena){
        try{
            LinkedList<Token> tokens = tokenizador.tokenizar(cadena);
            Nodo arbol = parser.parse(tokens);
            System.out.println("-------Entrada-------\n" + cadena + "\n-------Salida--------");
            System.out.println("La expresión se resuelve a: " + evaluador.evaluar(arbol));
            System.out.println("Variables: " + evaluador.getVariables());
            System.out.println("-------Conversión-------\nPrefija: " + arbol.toPrefija() + "\nPostfija: " + arbol.toPostfija());
        }catch(LexicoInvalidoException e){
            System.out.println("Error léxico: " + e.getMessage());
        }catch(SintaxisInvalidaException e){
            System.out.println("Error sintáctico: " + e.getMessage());
        }catch(SemanticaInvalidaException e){
            System.out.println("Error semántico: " + e.getMessage());
        }
        System.out.println("\n\n");
    }
}

