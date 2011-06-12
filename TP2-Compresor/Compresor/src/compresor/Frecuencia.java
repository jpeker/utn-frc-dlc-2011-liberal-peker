/*
 * Frecuencia.java
 *
 */

package compresor;
/**
 * Clase para representar el info de la Cola de Prioridad.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2004
 */
public class Frecuencia implements Comparable
{
    private int indice;      // indice del vector que representa al rbol donde est este valor
    private int frecuencia;  // frecuencia que tiene este signo

    /**
     *  Constructor por defecto. Coloca el ndice en -1 y deja frecuencia en cero
     */
    public Frecuencia()
    {
       indice = -1;
    }
    
    /**
     *  Constructor. Coloca el ndice en i y deja frecuencia en f
     */
    public Frecuencia (int f, int i)
    {
       indice = i;
       frecuencia = f;
    }

    /**
     *  Acceso al ndice
     *  @return el valor del ndice
     */
    public int getIndice() 
    {
        return indice;
    }
    
    /**
     *  Modifica el indice
     *  @param i el nuevo indice
     */
    public void setIndice(int i) 
    {
        indice = i;
    }

    /**
     *  Acceso a la frecuencia
     *  @return el valor de la frecuencia
     */
    public int  getFrecuencia() 
    { 
        return frecuencia; 
    }
    
    /**
     *  Modifica la frecuencia
     *  @param x la nueva frecuencia
     */
    public void setFrecuencia(int x) 
    { 
        frecuencia = x; 
    }

    /**
     *  Definicin del mtodo pedido por Comparable 
     *  @return Compara las frecuencias: retorna 0 si eran iguales, >0 si la primera era mayor, o <0 en caso contrario
     */
    public int compareTo(Object x)
    {
        Frecuencia p = (Frecuencia)x;
        return frecuencia - p.frecuencia;
    }
    
    /**
     *  Obtiene la representacin como String del objeto
     *  @return Un string con la representacin del objeto
     */
    public String toString()
    {
        return "Frecuencia: " + frecuencia + " Indice: " + indice;
    }
}
