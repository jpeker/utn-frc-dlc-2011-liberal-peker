package persistencia;

/**
 * Implementa el método de Selección Directa para ordenar un archivo
 * representado por un DirectAccessFile. Seguimos el patrón Strategy.
 * 
 * @author Ing. Valerio Frittelli.
 * @version Abril de 2010.
 */

public class SelectionSort implements DirectAccessFileSorter
{
    private DirectAccessFile rf;

    /**
     * Crea un ordenador para un DirectAccessFile, asociado al algoritmo
     * Selection Sort. 
     */
    public SelectionSort( DirectAccessFile rf )
    {
        this.rf = rf;
    }

    /**
     * Implementación del m�todo de Selección Directa para ordenar un
     * archivo representado por un DirectAccessFile. El método no hace nada
     * si no hay un archivo asociado al ordenador, o si ese archivo no tiene
     * una clase base asociada.
     */
    public void sort( )
    {
        // si algo anda mal, salir sin hacer nada...
        if( rf == null || rf.getBaseClassName() == null || rf.getMode().equals( "r" ) ) return;
        
        // obtenemos la cantidad de registros del archivo
        long n = rf.count();
        
        // aplicamos el algoritmo Selection Sort
        for ( long i = 0; i < n-1; i++ )
        {
              Grabable ri = rf.get( i );
              for ( long j = i+1; j < n; j++ )
              {
                    Grabable rj = rf.get( j );
                    if ( ri.compareTo( rj ) > 0 )
                    {
                           rf.set( j , ri );
                           
                           // probar simplemente ri = rj;
                           Grabable aux = ri;
                           ri  = rj;
                           rj = aux;
                    }
              }
              rf.set( i, ri );
        }
    }
}
