package persistencia;

/**
 * Esta clase de interface especifica el comportamiento que debería
 * tener una clase que implemente un método de ordenamiento para un
 * DirectAccessFile. Hacemos uso del patrón Strategy.
 * 
 * @author Ing. Valerio Frittelli
 * @version Abril de 2010
 */

public interface DirectAccessFileSorter
{
    void sort();
}
