/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import javax.swing.JOptionPane;


/**
 *
 * @author Peker Liberal
 */
public class OpenHashFile extends HashFile{

      protected long begin_table;
      protected long count;//cantidad de registro

      // grabar count en el archivo al lado del tamaño de la tabla
    protected long capacity;
    public OpenHashFile (String nombre,String modo,long t){ // t capacidad de la tabla
        super(nombre,modo);
        capacity=t;
        count=0;
        init();
    }

    private void init()
    {
        // si no hay clase base, salir sin más...
        // begin_headers y capacity quedan en cero en ese caso.
        if( clase == null ) return;
        
        try
        {
            // si hay clase base, también hay una tabla y un tamaño grabado
            // después del nombre de la clase...
             capacity = maestro.readLong();
            count = maestro.readLong();
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al leer el tamaño de la tabla: " + e.getMessage() );
            System.exit(1);
        }
        
        // marcar el lugar donde comienzan los nodos cabecera...
        begin_table = this.bytePos();        
    }

    @Override
    protected long tableSize() {
      return capacity;
    }

    @Override
    public RegisterFileIterator createIterator() {
         if( clase == null ) return null;
        return new Iterator();
    }

    @Override
    public boolean contains(Grabable obj) {
        return ( find( obj ) != null );
    }

         /**
     * Busca un registro en el archivo. Retorna una copia del registro que estaba en el archivo
     * si es que el archivo contenía uno igual al que entra como parámetro. Si no lo encuentra,
     * retorna null. No se garantiza que el file pointer quede apuntando al mismo lugar que
     * apuntaba al empezar.
     * @param obj el objeto a buscar en el archivo.
     * @return una referencia al registro encontrado, o null si no se encontró.
     */
    public Grabable find(Grabable obj) {
         // si algo anduvo mal, salir retornando false...
        if( ! isOk( obj ) ) return null;
        if( clase == null ) return null;

        // acceder al headerlist correspondiente al objeto...
        long y = h( obj );


        // buscar el objeto de esa lista y retornar el resultado...
        return buscar( y, obj );
    }
  // busca la primera ocurrencia de obj en el archivo, retornando una
    // referencia a él si lo encuentra, o null si no. Invocado por find().
    private Grabable buscar( long madre, Grabable obj )
    {
        long offset = 1;
        long currentPos = madre;
        int tam = ( new Register( clase ) ).sizeOf();
        this.seekByte( currentPos*tam+begin_table );
         Register r= this.read();
           // recorrer la lista y verificar si obj existe en ella...
        while( r.getState()!=2)
        {
            if(
                    r.getState() == Register.CLOSED && obj.equals( r.getData() ) ) return r.getData();
           currentPos += offset;
           offset += 2;
          if (currentPos >= capacity)
            currentPos -= capacity;
            this.seekByte( (currentPos)*tam+begin_table );
            r= this.read();
        }
        // ...si no estaba, retornar null...
        return null;
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
            createTable( capacity );
        }

        // controlar si es necesario redispersar...
     if( count >= capacity / 2 ) rehash( true ); // si te mando un verdadero cambia el tamaño de la tabla y si no  limpia los borrados

        // obtener el índice de dispersión del objeto...
        long y = h( obj );
        //System.out.println("capacidad del add :"+capacity);


        // agregar a obj en esa lista, si no estaba repetido...
        boolean ok = insertar( y, obj );

        // ... si la grabación se hizo, volver a grabar la cabecera de la lista...
        if( ok ){ 
            count++;
            //System.out.println("count del inserto"+count);
            setCabecera();
        }

        // ... y retornar el flag de resultado...
        return ok;
    }

    @Override
    public boolean append(Grabable obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(Grabable obj) {
        // si algo anduvo mal, salir retornando false...
        if( ! isOk( obj ) ) return false;
        if( clase == null || getMode().equals( "r" ) ) return false;
        long y = h( obj );
        // remover el objeto de esa lista..
        boolean ok = eliminar(y, obj );
        if(ok){
                count--;
                //System.out.println("count"+count);
                setCabecera();
           }
        // ... y retornar el flag de resultado...
        return ok;
    }
    private boolean eliminar(long madre,  Grabable obj )
    {
        boolean ok = false;
        long offset = 1;
        long currentPos = madre;
        int tam = ( new Register( clase ) ).sizeOf();
        this.seekByte( currentPos*tam+begin_table );
         Register r= this.read();
        // recorrer la lista y verificar si obj existe en ella...
        while( r.getState()!=2 )
        {
            // controlar si el registro contiene a obj... en cuyo caso, cortar sin insertar...
            if(r.getState() == Register.CLOSED && obj.equals( r.getData() ) )
            {
              r.setState( Register.TOMBSTONE );
              try{
                this.seekByte(maestro.getFilePointer()-r.sizeOf());}
                catch( IOException e )
                {
                    JOptionPane.showMessageDialog( null, "Error al intentar devolver el número de byte: " + e.getMessage() );
                    System.exit( 1 );
                }
                this.write( r );
                ok=true;
                break;
            }
            currentPos += offset;
            offset += 2;
            if (currentPos >= capacity)
                currentPos -= capacity;
            this.seekByte( (currentPos)*tam+begin_table );
            r= this.read();
        }
        return ok;
    }
     /**
     * Actualiza la capacidad y la cantidad de registro
     * No hace nada  si el archivo
     * no dispone de una clase base asociada
     */
    private void setCabecera()
    {
         if( clase == null || getMode().equals( "r" ) ) return;
         this.seekByte(marca);
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
        begin_table = this.bytePos();

    }
   public boolean update(Grabable obj) {
          // si algo anduvo mal, salir retornando false...
        if( ! isOk( obj ) ) return false;
        if( clase == null || getMode().equals( "r" ) ) return false;

        // acceder al headerlist correspondiente al objeto...
        long y = h( obj );


        // cambiar los objeto que coincidan con obj en esa lista..
        boolean ok = modificar( y, obj );

        // ... y retornar el flag de resultado...
        return ok;
    }
    private boolean modificar( long madre, Grabable obj )
    {
        boolean ok = false;
        long offset = 1;
        long currentPos = madre;
        int tam = ( new Register( clase ) ).sizeOf();
        this.seekByte( currentPos*tam+begin_table );
         Register r= this.read();
        // recorrer la lista y verificar si obj existe en ella...
        while(r.getState()!=2)
        {

            // controlar si el registro contiene a obj... en cuyo caso, cortar sin insertar...
            if(r.getState() == Register.CLOSED && obj.equals( r.getData() ) )
            {
              r.setData(obj);
              try{
                this.seekByte(maestro.getFilePointer()-r.sizeOf());}
                catch( IOException e )
                {
                    JOptionPane.showMessageDialog( null, "Error al intentar devolver el número de byte: " + e.getMessage() );
                    System.exit( 1 );
                }
                this.write( r );
                ok=true;
                break;
            }
           currentPos += offset;
           offset += 2;
           if (currentPos >= capacity)
            currentPos -= capacity;
            this.seekByte( (currentPos)*tam+begin_table );
            r= this.read();
        }
        return ok;
    }


    @Override
    public void clean() {
       if( clase == null || getMode().equals( "r" ) ) return;
        rehash( false );
    }
     private void createTable( long n )
    {
        if( clase == null || getMode().equals( "r" ) ) return;
       
        // ajustar el tamaño de la tabla...
        if( n <= 0 ) {n = 11;}
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
        begin_table = this.bytePos();
        // marcar el lugar de inicio de los nodos cabecera...
        

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
//  si el archivo no tiene una clase base asociada (está vacío
//         físicamente), o está abierto en modo de sólo lectura, salir.
        if( clase == null || getMode().equals( "r" ) ) return;

        // calcular el nuevo tamaño de la tabla.
        long new_capacity = capacity;
        if( change )
        {
            long n = ( long )( new_capacity * 1.5 );
            new_capacity = siguientePrimo( n );
        }

        // lanzar la redispersión.
        try
        {
           // preparar el archivo temporal...
           OpenHashFile temp = new OpenHashFile( "temporal.dat", "rw" ,new_capacity);
           //System.out.println("Contador hash"+count);
           temp.maestro.setLength(0);
           temp.writeClassName( clase );
           temp.createTable( new_capacity );
           this.seekByte( begin_table);
           while ( ! this.eof() )
           {
               Register reg =  this.read();
               // si el registro es válido, INSERTARLO en el archivo temporal
               if ( reg.getState() == Register.CLOSED ) {temp.add( reg.getData() );}
           }
           this.close();
           temp.close();
           this.delete();
           temp.rename( this );
           capacity = new_capacity;
           // reabrir el RandomAcccessFile original, en el mismo modo que tenía
           maestro = new RandomAccessFile( fd, apertura );
        }
           catch( Exception e )
        {
            JOptionPane.showMessageDialog( null, "Error al limpiar el archivo: " + e.getMessage() );
            System.exit( 1 );
        }
   }
  private  boolean insertar(long madre,Grabable obj)// exploracion cuadratica graba o rechaza cuando el registro esta repetido
  {
  
        //System.out.println("direccion de registro"+madre);
        long offset = 1;
        long currentPos = madre;
        int tam = ( new Register( clase ) ).sizeOf();
        this.seekByte( currentPos*tam+begin_table );
         Register r= this.read();
      //System.out.println("direcion del inser buscar"+(currentPos*tam+begin_table));
        while(r.getState()!=2 )
        {
            // controlar si el registro contiene a obj... en cuyo caso, cortar sin insertar...
            if(
                    r.getState() == Register.CLOSED && obj.equals( r.getData() ) ) return false;
            currentPos += offset;
            offset += 2;
            if (currentPos >= capacity)
            currentPos -= capacity;
            this.seekByte( (currentPos)*tam+begin_table );
            r= this.read();
        }
        long direccion=findPos(obj);
        //System.out.println("direcion del find pos "+direccion);
        this.seekByte(direccion*tam+begin_table);
        Register re = new Register(obj);
        r.setData(obj);
        re.setState(Register.CLOSED);
        //this.seekByte( direccion*tam+begin_table);
        this.write( re );
        return true;
  }
  private long findPos(Grabable obj)
  {
    long offset = 1;
    long currentPos = h(obj);
//    this.seekByte(begin_table);
//    int tamaño=this.read().sizeOf();
     int tamaño = ( new Register( clase ) ).sizeOf();
    this.seekByte( (currentPos)*tamaño+begin_table );
    Register r= this.read();
      //System.out.println("capacidad del fin pos"+capacity+"conTador "+count);
    while (true)
    {
        if(r.getState()==2)break;

      currentPos += offset;
      offset += 2;
      //System.out.println("currentPos: " + currentPos);
      if (currentPos >= capacity)
        currentPos -= capacity;
       this.seekByte( (currentPos)*tamaño+begin_table );
        r= this.read();
    }
    return currentPos;
  }
    private class Iterator implements RegisterFileIterator
    {
           // el número relativo de registro actualmente accedido.
           private long currentIndex;

           // un NodeRegister como auxiliar de operaciones.
           private Register reg =   getRegisterInstance();

           // un Register asociado a un HeaderList como auxiliar de operaciones.
           //private Register rhl = new Register( new () );

           /**
            * Crea un iterador posicionado en el primer registro almacenado en el
            * archivo.
            */
           private Iterator()
           {
               //rehash( false );
               currentIndex = begin_table;
           }

           /**
            * Vuelve el iterador al primer registro del archivo.
            */
           public void first()
           {
               currentIndex = begin_table ;
           }

           /**
            * Avanza el iterador al índice de byte del siguiente registro en el
            * archivo, si es que esto es posible. De lo contrario, el iterador
            * se mantiene en la misma posición anterior.
            */
           public void next()
           {
               if ( currentIndex < length() )
               {
                   currentIndex += reg.sizeOf();
               }
           }

           /**
            * Determina si es posible avanzar el iterador al siguiente
            * registro (return true) o no (return false).
            * @return true si es posible avanzar al siguiente registro.
            */
           public boolean hasNext()
           {
               return ( currentIndex < length() );
           }

           /**
            * Retorna el objeto actualmente apuntado por el iterador. Si el archivo está
            * vacío, retorna null. Si ocurre un problema de IO o de instanciación, la
            * aplicación terminará.
            * @return el objeto actual del iterador o null si el archivo está vacío.
            */
           public Grabable current()
           {
               // salir con null si el archivo está vacío.
               if( clase == null ) return null;

               // posicionar en el registro actual
               seekByte( currentIndex );

               // leer el registro en esa posición.
               reg = read();
               if(reg.getState()==1){
               // retornar el objeto contenido en el registro leído
               return reg.getData();}
                else{
                       // reg.setData(null);
                        Grabable x =null;
                       // return reg.getData();
                        return x;
                }

           }
    }
}
