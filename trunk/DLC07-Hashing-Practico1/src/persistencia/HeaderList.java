package persistencia;

/**
 * Un nodo de cabecera para una lista con direcciones de disco, que será grabada en
 * un archivo hash con listas de desborde.
 * 
 * @author Liberal - Peker
 * @version Abril de 2010.
 */

import java.io.*;
import javax.swing.*;
public class HeaderList implements Grabable
{    
    // la dirección del primer NodeRegister de esta lista
    private long first;

    // la cantidad de registros que contiene esta lista
    private long size;
    
    /**
     * Crea un nodo de cabecera para una lista de desborde. La lista arranca vacía.
     */
    public HeaderList()
    {
        first = NodeRegister.NIL;
        size = 0;
    }
    
    /**
     * Retorna la dirección de disco del primer Register de esta lista.
     * @return la dirección del primer registro.
     */
    public long getFirst()
    {
        return first;
    }
    
    /**
     * Cambia la dirección del primer registro de esta lista.
     * @param p la dirección del nuevo primer registro.
     */
    public void setFirst( long p )
    {
        if( p < -1 ) p = NodeRegister.NIL;
        first = p;
    }
    
    /**
     * Retorna la cantidad de registros grabados en esta lista.
     * @return la cantidad de registros de esta lista.
     */
    public long length()
    {
        return size;
    }
    
    /**
     * Incrementa en uno el tamaño de la lista representada por este HeaderList.
     */
    public void increase()
    {
        size++;
    }
    
    /**
     * Decrementa en uno el tamaño de la lista representada por este HeaderList.
     */    
    public void decrease()
    {
        size--;
    }


    /**
     *  Calcula el tamaño en bytes del objeto, tal como sera grabado en disco. Pedido por Grabable.
     *  @return el tamaño en bytes del objeto como será grabado.
     */
    public int sizeOf()
    {
        return 16;  // 8 + 8...
    }
    
    /**
      *  Especifica cómo se graba un HeaderList en disco. Pedido por Grabable. Si la operación no
      *  puede hacerse por cualquier motivo, provocará la interrupción de la aplicación.
      *  @param a el manejador del archivo de disco donde se hará la grabación.
      */
    public void grabar( RandomAccessFile a )
    {
        try
        {
            a.writeLong( first );
            a.writeLong( size );
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog(null, "Error al grabar el nodo de cabecera de la lista: " + e.getMessage());
            System.exit(1);       
        }
    }
    
    /**
     *  Especifica cómo se lee un HeaderList desde disco. Pedido por Grabable. Si la operación no
     *  puede hacerse por cualquier motivo, provocará la interrupción de la aplicación.
     *  @param a el manejador del archivo de disco desde donde se hará la lectura.
     */
    public void leer ( RandomAccessFile a )
    {
         try
         {
             first = a.readLong();
             size = a.readLong();
         }
         catch( IOException e )
         {
             JOptionPane.showMessageDialog(null, "Error al leer el nodo cabecera de la lista: " + e.getMessage());
             System.exit(1);
         }
    }
    
     /**
      * Compara dos objetos de la clase HeaderList. La comparaci�n se hace simplemente en base al tamaño
      * de cada lista. Lanzará una excepción ClassCastException si el objeto enviado x
      * no puede convertirse a un HeaderList válido.
      * @return 0 si las listas son del mismo tamaño, 1 si la primera es mas larga, -1 en caso contrario.
      * @param x el objeto contra el cual se compara.
      * @throws ClassCastException si x no puede convertirse en un objeto Register.
      */
     public int compareTo ( Object x )
     {
         HeaderList p = ( HeaderList ) x;
         return ( int )( this.size - p.size );
     }
}
