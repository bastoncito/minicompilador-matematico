public abstract class Nodo {
    abstract String toPostfija();
    abstract String toPrefija();
}

class NodoAsignacion extends Nodo {
    String variable;
    Nodo expresion;

    NodoAsignacion(String variable, Nodo expresion) {
        this.variable = variable;
        this.expresion = expresion;
    }

    @Override
    String toPrefija(){
        return "= " + this.variable + " " +this.expresion.toPrefija();
    }

    @Override
    String toPostfija(){
        return this.variable + " " + this.expresion.toPostfija() + " =";
    }
}

class NodoOperacion extends Nodo {
    String operador;
    Nodo izquierdo, derecho;

    NodoOperacion(Nodo izquierdo, String operador, Nodo derecho) {
        this.izquierdo = izquierdo;
        this.operador = operador;
        this.derecho = derecho;
    }

    @Override
    String toPrefija(){
        return this.operador + " " + this.izquierdo.toPrefija() + " " + this.derecho.toPrefija();
    }

    @Override
    String toPostfija(){
        return this.izquierdo.toPostfija() + " " + this.derecho.toPostfija() + " " + this.operador;
    }
}

class NodoNegativo extends Nodo {
    Nodo operando;

    NodoNegativo(Nodo operando){
        this.operando = operando;
    }

    @Override
    String toPrefija(){
        return "- " + this.operando.toPrefija();
    }

    @Override
    String toPostfija(){
        return this.operando.toPostfija() + " -";
    }

}

class NodoNumero extends Nodo{
    int valor;

    NodoNumero(int valor){
        this.valor = valor;
    }

    @Override
    String toPrefija(){
        return "" + this.valor;
    }

    @Override
    String toPostfija(){
        return "" + this.valor;
    }

}

class NodoVariable extends Nodo{
    String nombre;

    NodoVariable(String nombre){
        this.nombre = nombre;
    }

    @Override
    String toPrefija(){
        return this.nombre;
    }

    @Override
    String toPostfija(){
        return this.nombre;
    }
}