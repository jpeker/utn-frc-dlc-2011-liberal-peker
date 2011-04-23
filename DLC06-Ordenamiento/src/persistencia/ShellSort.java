/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

/**
 *
 * @author Administrador
 */
public class ShellSort implements DirectAccessFileSorter
{
    private DirectAccessFileDelete rf;
    public ShellSort( DirectAccessFileDelete rf )
    {
        this.rf = rf;
    }

    public void sort() {
        // si algo anda mal, salir sin hacer nada...
        if( rf == null || rf.getBaseClassName() == null || rf.getMode().equals( "r" ) ) {System.out.println("Nada que ordenar");;return;}

        // obtenemos la cantidad de registros del archivo
        long n = rf.count();

        // aplicamos el algoritmo Shell
        shell();
    }
    private void shell()
    {
       long h;
       long n=rf.count();
       if(n==0){
           System.out.println("Nada que ordenar");
       }
       else{
           for(h = 1; h <= n / 9; h = 3*h + 1);
           for ( ; h > 0; h /= 3)
           {
               for (long j = h; j < n; j++)
                 {
                      Grabable y= rf.get(j);

                      //for (k = j - h; k >= 0 && y < v[k]; k-=h)
                      long k;
                      for (k=j-h; k >= 0 && y.compareTo(rf.get(k))< 0; k-=h)
                      {
                            //v[k+h] = v[k];
                            rf.set(k+h, rf.get(k));
                      }
                     //v[k+h] = y;
                     rf.set(k+h, y);
                 }
           }
       }
    }
}
