public class Main {
    public static void main(String[] args){
        Compilador comp = new Compilador();
        comp.compilar("x = 3 +");
        comp.compilar("x = 3 ..");
        comp.compilar("x = 3 + 5");
        comp.compilar("y = 3 / 0");
        comp.compilar("y = x * 2 / 8");
        comp.compilar("z = x / y");
    }
}
