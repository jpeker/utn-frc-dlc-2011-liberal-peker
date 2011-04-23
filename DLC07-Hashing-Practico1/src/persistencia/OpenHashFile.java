/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author dlc
 */
public class OpenHashFile extends HashFile{

      protected long begin_table;
      protected long count;//cantidad de registro
    // el tamaño actual de la tabla (la cantidad de listas de desborde que contiene)
      // grabar count en el archivo al lado del tamaño de la tabla
    protected long capacity;
    public OpenHashFile (String nombre,String modo,long t){ // t capacidad de la tabla
        super(nombre,modo);

    }

    @Override
    protected long tableSize() {
      return capacity;
    }

    @Override
    public RegisterFileIterator createIterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean contains(Grabable obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Grabable find(Grabable obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(Grabable obj) {
       if( getMode().equals( "r" ) ) return false;
        if( ! isOk( obj ) ) return false;

        // si el archivo estaba vac�o...
        if( clase == null )
        {
            // ...grabar el nombre de la clase base al principio...
            writeClassName( obj );

            // ... y crear la tabla (con 11 cabeceras de listas en este caso)...
            createTable( 11 );
        }

        // controlar si es necesario redispersar...
        if( count >= capacity / 2 ) rehash( true ); // si te mando un verdadero cambia el tamaño de la tabla y si no  limpia los borrados

        // obtener el índice de dispersión del objeto...
        long y = h( obj );



        // agregar a obj en esa lista, si no estaba repetido...
        boolean ok = insertar( y, obj );

        // ... si la grabación se hizo, volver a grabar la cabecera de la lista...
        if( ok ) count++;

        // ... y retornar el flag de resultado...
        return ok;
    }

    @Override
    public boolean append(Grabable obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Grabable obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean update(Grabable obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     private void createTable( long n )
    {
        if( clase == null || getMode().equals( "r" ) ) return;

        // ajustar el tamaño de la tabla...
        if( n <= 0 ) n = 11;
        capacity = n;

        // grabar ese tamaño (el file pointer está ubicado detrás del nombre de la clase base)...
        try
        {
            maestro.writeLong( capacity );
            maestro.writeLong( count );
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al grabar el tamaño de la tabla: " + e.getMessage() );
            System.exit(1);
        }

        // marcar el lugar de inicio de los nodos cabecera...
        begin_table = this.bytePos();

        // crea un  objeto register para grabar
        Register r =this.getRegisterInstance() ;
        r.setState( Register.OPEN );
        for( long i = 0; i < capacity; i++ )
        {
            this.write( r );
        }
    }
   private void rehash(boolean change)
   {


   }
  private  boolean insertar(long madre,Grabable obj)// exploracion cuadratica graba o rechaza cuando el registro esta repetido
  {
  return true;
  }
}
