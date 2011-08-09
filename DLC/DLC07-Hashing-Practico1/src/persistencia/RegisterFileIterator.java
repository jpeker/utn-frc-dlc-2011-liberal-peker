package persistencia;

/**
 * Esta clase de interface especifica el comportamiento que debería
 * tener un iterador para un DirectAccessFile, cuando se pretenda recorrer
 * ese archivo en forma secuencial.
 * 
 * @author Liberal - Peker
 * @version May de 2011
 */

public interface RegisterFileIterator
{
   public void first();
   public void next();
   public boolean hasNext();
   public Grabable current();
}


