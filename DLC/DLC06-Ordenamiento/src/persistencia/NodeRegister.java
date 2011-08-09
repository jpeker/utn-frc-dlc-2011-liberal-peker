package persistencia;

/**
 * Un NodeRegister es un Register que contiene la dirección del siguiente NodeRegister
 * del archivo. Usado en la implementación de archivos hash con listas de desbrode.
 * 
 * @author Ing. Valerio Frittelli
 * @version Abril de 2010
 */
import java.io.*;
import javax.swing.*;
public class NodeRegister extends Register
{
    // un valor para indicar una dirección incorrecta, a modo de null
    public static final long NIL = -1;

    // la dirección de disco donde está grabado el siguiente registro
    private long next;
    
    /**
     * Crea un NodeRegister asociado al objeto d. La dirección del siguiente
     * NodeRegister se toma igual al valor tomado en dir. Si ese valor es inválido,
     * se asume que la dirección será igual a -1 (Nodegister.NIL).
     * @param d el objeto a almacenar en este register.
     * @param dir la dirección de disco del siguiente register.
     */
    public NodeRegister( Grabable d, long dir )
    {
        super( d );
        if( dir < -1 ) dir = NIL;
        next = dir;
    }
    
    /**
     * Retorna la dirección de disco del siguiente register.
     * @return la dirección del siguiente register en la lista actual.
     */
    public long getNext()
    {
        return next;
    }
    
    /**
     * Cambia la dirección del siguiente register, por el valor tomado en dir.
     * Si ese valor es inválido, se asume que la dirección será igual a -1
     * (Nodegister.NIL).
     * @param dir la nueva dirección de disco del siguiente register.
     */
    public void setNext( long dir )
    {
        if( dir < -1 ) dir = NIL;
        next = dir;
    }
    
    /**
      *  Calcula el tamaño en bytes del objeto, tal como será grabado en disco. Pedido por Grabable.
      *  @return el tamaño en bytes del objeto como será grabado.
      */
    public int sizeOf()
    {
        return super.sizeOf() + 8;
    }
    
    /**
      *  Especifica cómo se graba un NodeRegister en disco. Pedido por Grabable. Si la operación no
      *  puede hacerse por cualquier motivo, provocará la interrupción de la aplicación.
      *  @param a el manejador del archivo de disco donde se hará la grabación.
      */
    @Override
    public void grabar( RandomAccessFile a )
    {
        super.grabar( a );
        try
        {
            a.writeLong( next );
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog(null, "Error al grabar el nodo en la lista: " + e.getMessage());
            System.exit(1);       
        }
    }
    
    /**
     *  Especifica cómo se lee un NodeRegister desde disco. Pedido por Grabable. Si la operación no
     *  puede hacerse por cualquier motivo, provocará la interrupción de la aplicación.
     *  @param a el manejador del archivo de disco desde donde se hará la lectura.
     */
    @Override
    public void leer ( RandomAccessFile a )
    {
         super.leer( a );
         try
         {
             next = a.readLong();
         }
         catch( IOException e )
         {
             JOptionPane.showMessageDialog(null, "Error al leer el nodo de la lista: " + e.getMessage());
             System.exit(1);
         }
    }    
}
