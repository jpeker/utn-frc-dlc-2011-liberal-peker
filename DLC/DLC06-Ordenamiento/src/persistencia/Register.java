package persistencia;

/**
 * Una clase para describir en forma genérica un registro capaz de ser almacenado en
 * un RegisterFile. Contiene una referencia de tipo Grabable al objeto cuyos datos
 * serán almacenados en el archivo, y una propiedad "estado" que indica si el registro
 * es valido o no dentro del archivo, o bien su estado general frente a diversas técnicas
 * de gestión de contenidos (como Hashing). Se  usa para indicar si un registro fue
 * marcado como borrado o no, permitiendo de esta forma implementar borrados por marcado
 * lógico dentro de un RegisterFile. Si un registro es válido (o sea, no fue marcado como
 * borrado), entonces estado = ACTIVO. Si el registro no es válido (está marcado como
 * borrado), entonces estado = BORRADO. Otras constantes de la clase sirven para marcar
 * estados válidos en contextos de Hashing, a criterio del programador.
 * 
 * @author Ing. Valerio Frittelli
 * @version Abril de 2010
 */

import java.io.*;
import javax.swing.*;
public class Register implements Grabable
{
     // Constantes públicas para el modelo de DirectAccessFile
     public static final int BORRADO = 0;  // el Register está marcado como borrado.
     public static final int ACTIVO = 1;   // el Register está marcado como activo.
     
     // Constantes públicas para el modelo de Hashing por Direccionamiento Abierto.
     public static final int TOMBSTONE = 0;  // el Register fue borrado / en la casilla hay una tumba.
     public static final int CLOSED = 1;     // el Register es válido   / la casilla está cerrada.
     public static final int OPEN = 2;       // el Register está vacío  / la casilla nunca estuvo ocupada

     // marca de estado (toma los valores de las constantes públicas). Cuatro bytes en disco.
     private int estado;         
     
     // los datos puros que seran grabados
     private Grabable datos;    
     
     
     /**
      *  Crea un Registro con datos, marcándolo como activo. Note que el estado ACTIVO equivale al
      *  estado CLOSED (ambas constantes en la clase valen 1) Este constructor NO CONTROLA que el 
      *  objeto referido por d sea efectivamente distinto de null.
      *  @param d los datos a almacenar en el nuevo registro.
      */
     public Register ( Grabable  d )
     {
         estado = ACTIVO;  // equivale a: estado = CLOSED;
         datos = d;
     }
     
     
     /**
      *  Retorna el estado del Register.
      *  @return un int acorde a los valores de las constantes públicas de la clase, que indica
      *  el estado del Register.
      */
     public int getState()
     {
         return estado;
     }
     
     /**
      *  Cambia el estado del registro, en memoria.
      *  @param x el nuevo estado.
      */
     public void setState( int x )
     {
         estado = x;    
     }
     
     /**
      *  Cambia los datos del registro en memoria. No controla si la referencia d es 
      *  efectivamente diferente de null.
      *  @param d los nuevos datos.
      */
     public void setData( Grabable d )
     {
         datos = d;    
     }
     
     /**
      *  Accede a los datos del registro en memoria.
      *  @return una referencia a los datos del registro.
      */
     public Grabable getData()
     {
         return datos;
     }
     
     /**
      *  Calcula el tamaño en bytes del objeto, tal como sera grabado en disco. Pedido por Grabable.
      *  @return el tamaño en bytes del objeto como será grabado.
      */
     public int sizeOf()
     {
        return datos.sizeOf() + 4;   // suma 4 por el atributo "estado", que es int
     }
     
     /**
      *  Especifica cómo se graba un Register en disco. Pedido por Grabable. Si la operación no
      *  puede hacerse por cualquier motivo, provocará la interrupción de la aplicación.
      *  @param a el manejador del archivo de disco donde se hará la grabación.
      */
     public void grabar ( RandomAccessFile a )
     {
         try
         {
             a.writeInt( estado );
             datos.grabar( a );
         }
         catch( IOException e )
         {
             JOptionPane.showMessageDialog(null, "Error al grabar el estado del registro: " + e.getMessage());
             System.exit(1);
         }
     }

     /**
      *  Especifica cómo se lee un Register desde disco. Pedido por Grabable. Si la operación no
      *  puede hacerse por cualquier motivo, provocará la interrupción de la aplicación.
      *  @param a el manejador del archivo de disco desde donde se hará la lectura.
      */
     public void leer ( RandomAccessFile a )
     {
         try
         {
             estado = a.readInt();
             datos.leer( a );
         }
         catch( IOException e )
         {
             JOptionPane.showMessageDialog(null, "Error al leer el estado del registro: " + e.getMessage());
             System.exit(1);
         }
     }    
     
     /**
      * Redefine el método heredado desde Object. Retorna una cadena con el contenido del objeto, incluida
      * la marca de estado.
      * @return una cadena con el estado del objeto.
      */
    @Override
     public String toString()
     {
        return getData().toString() + "\tEstado: " + getState();    
     }
     
     /**
      * Compara dos objetos de la clase Register. Devuelve el resultado de comparar los
      * datos contenidos en el registro. Lanzará una excepción ClassCastException si el objeto enviado x
      * no puede convertirse a un Register válido.
      * @return 0 si los objetos eran iguales, 1 si el primero era mayor, -1 en caso contrario.
      * @param x el objeto contra el cual se compara.
      * @throws ClassCastException si x no puede convertirse en un objeto Register.
      */
     public int compareTo ( Object x )
     {
         Register r = ( Register ) x;
         return datos.compareTo( r.datos );
     }
}
