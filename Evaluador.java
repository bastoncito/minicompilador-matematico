import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Evaluador {
    Map<String, Integer> variables = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public int evaluar(Nodo nodo) throws SemanticaInvalidaException{
        switch (nodo) {
            case null -> throw new SemanticaInvalidaException("Error al evaluar");
            case NodoAsignacion nodoAsignacion -> {
                String variable = nodoAsignacion.variable;
                int valor = evaluar(nodoAsignacion.expresion);
                this.variables.put(variable, valor);
                System.out.println("Asignacion: " + variable + " = " + valor);
                return valor;
            }
            case NodoOperacion op -> {
                int izquierda = evaluar(op.izquierdo);
                int derecha = evaluar(op.derecho);
                String operador = op.operador;
                int valor;
                switch (operador) {
                    case "+" -> valor = izquierda + derecha;
                    case "-" -> valor = izquierda - derecha;
                    case "*" -> valor = izquierda * derecha;
                    case "/" -> {
                        if (derecha == 0) throw new SemanticaInvalidaException("División por cero");
                        valor = izquierda / derecha;
                    }
                    case "^" -> valor = (int) Math.pow(izquierda, derecha);
                    default -> throw new SemanticaInvalidaException("Operador invalido");
                }
                System.out.println(izquierda + " " + operador + " " + derecha + " = " + valor);
                return valor;
            }
            case NodoNegativo negativo -> {
                int valor = evaluar(negativo.operando);
                System.out.println("-" + valor + "=" + -1 * valor);
                return -1 * valor;
            }
            case NodoVariable variable -> {
                String nombre = variable.nombre;
                if (variables.containsKey(nombre)) {
                    return variables.get(nombre);
                }
                System.out.println("Introduzca el valor de la variable " + nombre + ": ");
                while (true) {
                    try {
                        int valor = sc.nextInt();
                        sc.nextLine();
                        variables.put(nombre, valor);
                        return valor;
                    } catch (NumberFormatException e) {
                        System.out.println("Inválido, intente de nuevo.");
                    }
                }
            }
            case NodoNumero numero -> {
                return numero.valor;
            }
            default -> {
            }
        }
        throw new SemanticaInvalidaException("Error al evaluar");
    }

    public Map<String, Integer> getVariables(){
        return this.variables;
    }
}
