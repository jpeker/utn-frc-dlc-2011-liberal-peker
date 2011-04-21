package persistencia;

/**
 * Esta clase de interface especifica el comportamiento que debería
 * tener un iterador para un DirectAccessFile, cuando se pretenda recorrer
 * ese archivo en forma secuencial.
 * 
 * @author Ing. Valerio Frittelli
 * @version Marzo de 2010
 */

public interface RegisterFileIterator
{
   public void first();
   public void next();
   public boolean hasNext();
   public Grabable current();
}


