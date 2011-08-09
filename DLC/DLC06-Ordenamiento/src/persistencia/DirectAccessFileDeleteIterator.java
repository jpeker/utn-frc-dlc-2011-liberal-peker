/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;
/**
 * Esta clase especifica el comportamiento que debería
 * tener un iterador que recorre desde el ultimo elemento al primero de DirectAccessFileDelete, cuando se pretenda recorrer
 * ese archivo en forma secuencial.
 *
 * @author Rodrigo Liberal, Peker Julian
 * @version Abril de 2011
 */
public class DirectAccessFileDeleteIterator implements RegisterFileIterator {
    private long currentIndex;
    private DirectAccessFileDelete rf;
    public DirectAccessFileDeleteIterator(){
    }
   public DirectAccessFileDeleteIterator(DirectAccessFileDelete rfp)
   {
     // Entra un DirectAccessFileDelete como parametro para quitarle el private class que tenia Sequential Iterator
     //clean();
     rf=rfp;
     currentIndex = rf.count()-1;
   }

    public void first() {
               currentIndex = rf.count()-1; // El primero es el ultimo elemento, a pesar de que el nombre del metodo sea first
    }

    public void next() {
               if ( currentIndex >= 0 ) // si mayor o igual a 0 decremento y voy para abajo
               {
                   currentIndex--;
               }
    }

    public boolean hasNext() {
               if(currentIndex < 0 ) return false; // Si es menor que 0 corto
               else return true; // Sino sigo
    }

    public Grabable current() {
               // Identico a lo que el Profesor Programó
               // salir con null si el archivo está vacío.
               if( rf.clase == null ) return null;

               // posicionar en el registro actual
               rf.seekRegister( currentIndex );

               // leer el registro en esa posición.
               Register r = rf.read();

               // retornar el objeto contenido en el registro leído
               return r.getData();
    }

}
