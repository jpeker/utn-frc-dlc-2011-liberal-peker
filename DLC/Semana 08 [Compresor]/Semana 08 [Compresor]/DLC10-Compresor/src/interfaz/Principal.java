package interfaz;

/**
 * Contiene el main para testear el arbol de Huffman.
 *
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2007
 */
import compresor.Compresor;
public class Principal
{
    public static void main (String args[])
    {
             Compresor compresor = new Compresor();
             int op;
      
             System.out.println("Prueba de Compresion y Descompresion");
             
             do
             {
                 System.out.println("1. Fase de compresion...");
                 System.out.println("2. Fase de descompresion...");
                 System.out.println("3. Salir");
                 System.out.print("\nIngrese opcion: ");
                 op = Consola.readInt();
                 
                 switch(op)
                 {
                     case 1: compresor.comprimir("pacman.c");
                             System.out.println("Hecho...");
                             break;
                             
                     case 2: compresor.descomprimir("pacman.cmp");
                             System.out.println("Hecho...");
                             break;
                             
                     case 3: ;
                 }
             }
             while(op != 3);
    }
}
