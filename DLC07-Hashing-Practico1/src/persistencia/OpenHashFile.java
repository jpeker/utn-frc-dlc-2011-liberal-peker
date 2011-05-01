/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;


/**
 *
 * @author dlc
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
  //      if( count >= capacity / 2 ) rehash( true ); // si te mando un verdadero cambia el tamaño de la tabla y si no  limpia los borrados

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


   }
  private  boolean insertar(long madre,Grabable obj)// exploracion cuadratica graba o rechaza cuando el registro esta repetido
  {
   long d = madre;

    this.seekByte(begin_table);
    int tamaño=this.read().sizeOf();
    this.seekByte( (d-1)*tamaño+begin_table );
    Register r= this.read();

        while(  r.OPEN != 2 )
        {
            // suponemos que las direcciones son de byte y no de registro relativo...

            r = ( NodeRegister ) this.read();

            // controlar si el registro contiene a obj... en cuyo caso, cortar sin insertar...
            if(
                    r.getState() == Register.CLOSED && obj.equals( r.getData() ) ) return false;

            // ... si no cortó, avanzar al siguiente NodeRegister...
           this.seekByte( (d-1)*tamaño+begin_table );
            r= this.read();
        }
        long direccion=findPos(obj);
        System.out.println("posi"+direccion+"clave"+madre);
        this.seekByte(direccion);
        r.setData(obj);
        this.seekByte( direccion );
        this.write( r );
        // si no existía, crear un NodeRegister para grabarlo...


//        // saltamos al final del archivo...
//        this.goFinal();
//
//        // obtenemos la dirección donde será grabado el nodo...
//        long dir = this.bytePos();
//
//        // ... lo grabamos...
//        this.write ( nuevo );
//
//        // ... y lo enganchamos en la lista...
//        if( ant == NodeRegister.NIL )
//        {
//            // lista vacía... va al principio de la lista... modificar el header list...
//            hl.setFirst( dir );
//        }
//        else
//        {
//            // va al final... cambiar el nodo en la posición ant... o sea: el último r leído!!!
//            r.setNext( dir );
//            this.seekByte( ant );
//            this.write( r );
//        }
//
//        // incrementar en uno el contador en el header list...
//        hl.increase();
//
//        // bye bye...
        return true;
  }
  private long findPos(Grabable obj)
  {
    long offset = 1;
    long currentPos = h(obj);
    this.seekByte(begin_table);
    int tamaño=this.read().sizeOf();
    this.seekByte( (currentPos-1)*tamaño+begin_table );
    Register r= this.read();

    //!array[currentPos].element.equals(x)
    while ( r.OPEN != 2 )
    {
      currentPos += offset;
      offset += 2;
      System.out.println("currentPos: " + currentPos);
      if (currentPos >= capacity)
        currentPos -= capacity;
       this.seekByte( (currentPos-1)*tamaño+begin_table );
        r= this.read();
    }
    return currentPos-1+begin_table;
  }
    private class Iterator implements RegisterFileIterator
    {
           // el número relativo de registro actualmente accedido.
           private long currentIndex;

           // un NodeRegister como auxiliar de operaciones.
           private Register reg =   getRegisterInstance();

           // un Register asociado a un HeaderList como auxiliar de ooperaciones.
           private Register rhl = new Register( new HeaderList() );

           /**
            * Crea un iterador posicionado en el primer registro almacenado en el
            * archivo.
            */
           private Iterator()
           {
              // rehash( false );
               currentIndex = begin_table + capacity * rhl.sizeOf();
           }

           /**
            * Vuelve el iterador al primer registro del archivo.
            */
           public void first()
           {
               currentIndex = begin_table + capacity * rhl.sizeOf();
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

               // retornar el objeto contenido en el registro leído
               return reg.getData();
           }
    }
}
