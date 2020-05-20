package versiones_anteriores;
import java.util.Scanner;
public class Listado {
    public void getListado(int op){
        switch(op){
            case 0:
            System.out.println("Bienvenido a la base de datos\nA continuacion se enlistan algunas operaciones realizables\n");
            System.out.println("Consultar - ingrese la letra c");
            System.out.println("Borrar - ingrese la letra b");
            System.out.println("Insertar - ingrese la letra i");
            System.out.println("Modificar - ingrese la letra m");
            System.out.println("Salir - ingrese la letra s");
            System.out.print("");
            break;
            case 1:
            System.out.print("\nSeleccione la siguiente operacion\n");
            System.out.println("Consultar - ingrese la letra c");
            System.out.println("Borrar - ingrese la letra b");
            System.out.println("Insertar - ingrese la letra i");
            System.out.println("Modificar - ingrese la letra m");
            System.out.println("Salir - ingrese la letra s");
            System.out.print("");
            break;
        }
    }
}
