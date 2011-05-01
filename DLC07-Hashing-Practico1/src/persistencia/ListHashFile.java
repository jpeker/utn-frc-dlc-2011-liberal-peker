package persistencia;

/**
 * Una clase para representar un archivo con estructura hash basada en listas de desborde.  
 * El archivo mantiene en sus primeros bytes una tabla con nodos cabecera de lista. Cada nodo 
 * cabecera (un objeto de la clase HeaderList) mantiene la dirección de disco del primer nodo
 * que haya entrado en esa posición, más el conteo de la cantidad de registros que tiene esa
 * lista. 
 * @author  Ing. Valerio Frittelli.
 * @version Marzo de 2010.
 */

import java.io.*;
import javax.swing.*;

public class ListHashFile extends HashFile
{
    // marca la posición donde comienzan a grabarse los nodos cabecera de listas
    protected long begin_headers;

    // el tamaño actual de la tabla (la cantidad de listas de desborde que contiene)
    protected long capacity;
    
    
    //*********************************************************************
    //******************************************************* Constructores
    //*********************************************************************
   
    /**
     * Crea un manejador para el ListHashFile, asociando al mismo el nombre "newfile.dat" a 
     * modo de file descriptor. Abre el archivo en disco asociado con ese file descriptor, 
     * en el directorio default, y en modo "rw" (tal como se se usa en la clase RandomAccessFile). 
     * Si ocurre un problema al intentar abrir el archivo, la aplicación finalizará. El archivo
     * será creado si no existía.
     */
    public ListHashFile ( )
    {        
        super( "newfile.dat", "rw" );
        init();
    }  
    
    /**
     * Crea un manejador para el HashFile, asociando al mismo un nombre a modo de file 
     * descriptor. Abre el archivo en disco asociado con ese file descriptor, en el modo indicado 
     * por el segundo parámetro. El modo de apertura es tal y como se usa en la clase RandomAccessFile:
     * "r" para sólo lectura y "rw" para lectura y grabación. Si el modo de apertura enviado es null,
     * o no es "r" ni "rw", se asumirá "r". Si la referencia nombre es null, o si la cadena "nombre" es
     * una cadena vacía o un blanco, el nombre del archivo se asumirÁ como "newfile.dat". Si ocurre un
     * problema al intentar abrir el archivo, la aplicaciÓn finalizarÁ. Si el modo de apertura es "rw"
     * el archivo será creado si no existía. Si el archivo ya existía, será preservado su contenido y su
     * estructura. 
     * @param nombre es el nombre físico y ruta del archivo a crear / abrir.
     * @param modo es el modo de apertura del archivo: "r" será sólo lectura y "rw" será lectura y grabación.
     */
    public ListHashFile ( String nombre, String modo )
    {   
        super( nombre, modo );
        init();
    }  
    
    /**
     * Crea un manejador para el HashFile, asociando al mismo un file descriptor. Abre el archivo en 
     * disco asociado con ese file descriptor, en el modo indicado por el segundo parámetro. El modo de
     * apertura es tal y como se usa en la clase RandomAccessFile: "r" para sólo lectura y "rw" para
     * lectura y grabación. Si el modo de apertura enviado es null, o no es "r" ni "rw", se asumirá "r".
     * Si la referencia file es null, el nombre del archivo se asumirá como "newfile.dat". Si ocurre un
     * problema al intentar abrir el archivo, la aplicaci�n finalizará. Si el modo de apertura es "rw"
     * el archivo será creado si no existía. Si el archivo ya existía, será preservado su contenido y su
     * estructura. 
     * @param file es el pathname del archivo a crear.
     * @param modo es el modo de apertura del archivo: "r" será sólo lectura y "rw" será lectura y grabación.
     */
    public ListHashFile ( File file, String modo )
    {        
        super( file, modo );
        init();
    }  
    
    /**
     * Crea y retorna un iterador para este archivo. Retorna null si el archivo no tiene una 
     * clase base asociada.
     * @return una referencia a un iterador para este archivo, o null si no es posible iterar en él.
     */
    public RegisterFileIterator createIterator()
    {
        if( clase == null ) return null;
        return new Iterator();
    }
    
    /**
     * Busca un registro en el archivo. Retorna true si el registro se encuentra, o false si no. 
     * No se garantiza que el file pointer quede apuntando al mismo lugar que apuntaba al empezar.
     * @return true si el archivo contiene un objeto igual a obj.
     */
    public boolean contains ( Grabable obj )
    {
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
    public Grabable find ( Grabable obj )
    {
        // si algo anduvo mal, salir retornando false...
        if( ! isOk( obj ) ) return null;
        if( clase == null ) return null;
        
        // acceder al headerlist correspondiente al objeto...
        long y = h( obj );
        HeaderList hl = this.getHeaderList( y );
        
        // buscar el objeto de esa lista y retornar el resultado...
        return buscar( hl, obj );
    }

    
    /**
     * Agrega un registro en el archivo, sin permitir repeticiones de datos. 
     * @param obj el objeto a agregar.
     * @return true si fue posible agregar el registro - false si no fue posible.
     */
    public boolean add ( Grabable obj )
    {
        // si algo anduvo mal, salir retornando false...
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
        if( averageLength() > 8 ) rehash( true );
        
        // obtener el índice de dispersión del objeto...
        long y = h( obj );
        
        // recuperar la cabecera de la lista en la posición "y" de la tabla...
        HeaderList hl = getHeaderList( y );
        
        // agregar a obj en esa lista, si no estaba repetido...
        boolean ok = insertar( hl, obj );
        
        // ... si la grabación se hizo, volver a grabar la cabecera de la lista...
        if( ok ) setHeaderList( y, hl );
        
        // ... y retornar el flag de resultado...
        return ok;
    }
    
    /**
     * Agrega un registro en el archivo, sin controlar si el mismo estuviera repetido. 
     * @param obj el registro a agregar. 
     * @return true si fue posible agregar el registro - false si no fue posible.
     */
    public boolean append ( Grabable obj )
    {
        // ...si algo anduvo mal, salir retornando false...
        if( getMode().equals( "r" ) ) return false;
        if( ! isOk( obj ) ) return false;
        
        // ...si estaba vacío, grabar clase base y crear tabla de listas...
        if( clase == null ) 
        {
            writeClassName( obj );
            createTable( 11 );
        }
        
        if( averageLength() > 8 ) rehash( true );
        
        // ...acceder al headerlist correspondiente al objeto...
        long y = h( obj );
        HeaderList hl = this.getHeaderList( y );
        
        // ...agregar a obj en esa lista... lo hacemos al frente de la misma...
        agregar( hl, obj );
        
        // ...regrabar el header list y retornar true...
        this.setHeaderList( y, hl ); 
        return true;  
    }
    
    /**
     * Borra un objeto del archivo, usando marcado lógico.
     * @param obj el registro a buscar y borrar.
     * @return true si fue posible borrar el registro - false si no fue posible.
     */
    public boolean remove ( Grabable obj )
    {
        // si algo anduvo mal, salir retornando false...
        if( ! isOk( obj ) ) return false;
        if( clase == null || getMode().equals( "r" ) ) return false;
        
        // acceder al headerlist correspondiente al objeto...
        long y = h( obj );
        HeaderList hl = this.getHeaderList( y );
        
        // remover el objeto de esa lista..
        boolean ok = eliminar( hl, obj );
        
        // ... si se hizo al menos una eliminación, volver a grabar la cabecera de la lista...
        if( ok ) setHeaderList( y, hl );
        
        // ... y retornar el flag de resultado...
        return ok;        
    }

    /**
     * Modifica un registro en el archivo. Reemplaza TODAS las ocurrencias de registros en el
     * archivo que sean dadas como iguales al objeto obj por el método equals(), cambiando sus
     * datos por otros que vienen en obj. 
     * @param obj el objeto con los nuevos datos.
     * @return true si fue posible modificar hacer la modicación - false si no fue posible.
     */
    public boolean update ( Grabable obj )
    {
        // si algo anduvo mal, salir retornando false...
        if( ! isOk( obj ) ) return false;
        if( clase == null || getMode().equals( "r" ) ) return false;
        
        // acceder al headerlist correspondiente al objeto...
        long y = h( obj );
        HeaderList hl = this.getHeaderList( y );
        
        // cambiar los objeto que coincidan con obj en esa lista..
        boolean ok = modificar( hl, obj );
        
        // ... si se hizo al menos una eliminación, volver a grabar la cabecera de la lista...
        if( ok ) setHeaderList( y, hl );
        
        // ... y retornar el flag de resultado...
        return ok;        
    }
    
    /**
     * Depura el contenido del archivo, eliminando los NodeRegister que estuvieran marcados 
     * como borrados. 
     */
    public void clean()
    { 
        if( clase == null || getMode().equals( "r" ) ) return;
        rehash( false );
    }

    //******************************************************************
    //************************************************Métodos protegidos
    //******************************************************************    
    
    /**
     * Retorna el tamaño de la tabla hash representada por este archivo. Ese número indica la cantidad de
     * listas de desborde representadas en la tabla.
     * @return la cantidad de listas de desborde representadas en este archivo.
     */
    protected long tableSize()
    {
        return capacity;
    }
    
    /**
     * Graba en la posición número "y" de la tabla, el nodo de cabecera de lista "hl".
     * No hace nada si hl es null, o si el valor de "y" es inválido, o si el archivo
     * no dispone de una clase base asociada.
     * @param y la posición de la tabla donde será almacenado el header list.
     * @param hl el HeaderList cuyos datos serán grabados.
     */
    protected void setHeaderList( long y, HeaderList hl )
    {
        // si algo anduvo mal, salir sin hacer nada...
        if( hl == null ) return;
        if( y < 0 || y >= capacity ) return;
        if( clase == null || getMode().equals( "r" ) ) return;
        
        // calcular la posición de inicio del HeaderList número "y"...
        Register r = new Register( hl );
        long pos = begin_headers + y * r.sizeOf();
        
        // grabar el Register en esa posición...
        this.seekByte( pos );
        r.grabar( maestro );        
    }
    
    /**
     * Recupera desde el disco el nodo de cabecera de la lista ubicada en la posición "y"
     * de la tabla. Retorna null si el archivo estaba vacío o el valor de "y" es inválido.
     * @param y el índice de la lista que se quiere acceder.
     * @return una referencia al objeto HeaderList que estaba almacenado en la posición "y"
     * o null si la lectura no pudo hacerse.
     */
    protected HeaderList getHeaderList( long y )
    {
        // si algo anduvo mal, retornar null...
        if( y < 0 || y >= capacity || clase == null ) return null;
        
        // calcular la posición de inicio del HeaderList número "y"...
        HeaderList x = new HeaderList();
        Register r = new Register( x );
        long pos = begin_headers + y * r.sizeOf();
        
        // leer el Register con el HeaderFile en esa posición...
        this.seekByte( pos );
        r.leer( maestro ); 
        
        // retornar el HeaderList contenido en ese Register...
        return ( HeaderList ) r.getData();
    }
    
    /**
     *  Crea una instancia de la clase NodeRegister, asociada a un objeto vacío de la clase base
     *  del archivo. Si no se puede crear esta instancia, la aplicación terminará. Redefine al
     *  método heredado desde la super clase.
     */ 
    @Override
    protected Register getRegisterInstance()
    {
        Grabable aux = null;
        try
        {
            aux = clase.getClass().newInstance();
        }
        catch( Exception e )
        {
             JOptionPane.showMessageDialog( null, "Error al instanciar un registro: " + e.getMessage() );
             System.exit( 1 );
        }
        return new NodeRegister( aux, NodeRegister.NIL );          
    }
        
    //******************************************************************
    //****************Métodos privados para gestionar la tabla de listas
    //******************************************************************
    
    // Auxiliar de los constructores. Intenta recuperar el tamaño de la tabla.
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
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al leer el tamaño de la tabla: " + e.getMessage() );
            System.exit(1);
        }
        
        // marcar el lugar donde comienzan los nodos cabecera...
        begin_headers = this.bytePos();        
    }
    
    // Crea la tabla con capacidad inicial n. Será invocado por los métodos que agregan registros
    // al archivo, pero sólo si al hacerlo no había una clase base asociada al mismo.
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
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al grabar el tamaño de la tabla: " + e.getMessage() );
            System.exit(1);
        }
        
        // marcar el lugar de inicio de los nodos cabecera...
        begin_headers = this.bytePos();
        
        // crear la tabla de nodos cabecera, inicialmente con listas vacías...
        Register r = new Register( new HeaderList() );
        for( int i = 0; i < capacity; i++ )
        {
            this.write( r );
        }
    }
    
    // calcula el largo promedio entre todas las listas de la tabla.
    private long averageLength()
    {
        if( clase == null ) return 0;
        long c = 0;
        for( long i = 0; i < capacity; i++ )
        {
            HeaderList hl = getHeaderList( i );
            c += hl.length();
        }
        return c / capacity;
    }
    
    /* Redispersa la tabla. Si el parámetro "change" es true, el método
     * aumenta el tamaño de la tabla. Si es false, deja el mismo tamaño
     * ya tenía, y en la práctica sólo hace una limpieza de registros
     * marcados como borrados.
     */
    private void rehash( boolean change )
    {
        // si el archivo no tiene una clase base asociada (está vacío
        // físicamente), o está abierto en modo de sólo lectura, salir.
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
           ListHashFile temp = new ListHashFile( "temporal.dat", "rw" );
           temp.maestro.setLength( 0 );
           temp.writeClassName( clase );
           temp.createTable( new_capacity );
           
           // ubicar el file pointer en el primer NodeRegister
           HeaderList hl = new HeaderList();
           Register r = new Register( hl );
           this.seekByte( this.begin_headers + this.capacity * r.sizeOf() );
           while ( ! this.eof() )
           {
               NodeRegister reg = ( NodeRegister ) this.read();
               
               // si el registro es válido, INSERTARLO en el archivo temporal
               if ( reg.getState() == Register.ACTIVO ) temp.add( reg.getData() ); 
           }

           this.close();
           temp.close();
           
           this.delete(); 
           temp.rename( this ); 
           
           // reabrir el RandomAcccessFile original, en el mismo modo que tenía
           maestro = new RandomAccessFile( fd, apertura );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( null, "Error al limpiar el archivo: " + e.getMessage() );   
            System.exit( 1 );
        }
    }
    
    //******************************************************************
    //***********Métodos privados para operar en una lista en particular
    //******************************************************************

    // busca la primera ocurrencia de obj en la lista hl, retornando una 
    // referencia a él si lo encuentra, o null si no. Invocado por find().
    private Grabable buscar( HeaderList hl, Grabable obj )
    {
        // recorrer la lista y verificar si obj existe en ella...
        long d = hl.getFirst();
        while( d != NodeRegister.NIL )
        {
            this.seekByte( d );
            NodeRegister r = ( NodeRegister ) this.read();
            
            // si lo encuentro, retorno su contenido y corto...
            if( r.getState() == Register.ACTIVO && obj.equals( r.getData() ) ) 
            {
                return r.getData( );
            }
            
            // ... si no lo encontró, avanzar al próximo...
            d = r.getNext();
        }

        // ...si no estaba, retornar null...
        return null;
    }
    
    // agrega el objeto obj en la lista hl, sólo si obj no estaba ya en
    // esa lista. Invocado por add().
    private boolean insertar( HeaderList hl, Grabable obj )
    {        
        // obtener la dirección del primer nodo de la lista...
        long d = hl.getFirst();
        
        // un persecutor para el recorrido...
        long ant = NodeRegister.NIL;  
        
        // recorrer la lista y verificar si obj ya existe en ella...
        NodeRegister r = null;
        while( d != NodeRegister.NIL )
        {
            // suponemos que las direcciones son de byte y no de registro relativo...
            this.seekByte( d );
            r = ( NodeRegister ) this.read();
            
            // controlar si el registro contiene a obj... en cuyo caso, cortar sin insertar...
            if(
                    r.getState() == Register.ACTIVO && obj.equals( r.getData() ) ) return false;
            
            // ... si no cortó, avanzar al siguiente NodeRegister...
            ant = d;
            d = r.getNext();
        }
        
        // si no existía, crear un NodeRegister para grabarlo...
        NodeRegister nuevo = new NodeRegister( obj, NodeRegister.NIL );
        
        // saltamos al final del archivo...
        this.goFinal();
        
        // obtenemos la dirección donde será grabado el nodo...
        long dir = this.bytePos();
        
        // ... lo grabamos...
        this.write ( nuevo );
        
        // ... y lo enganchamos en la lista...
        if( ant == NodeRegister.NIL )
        {
            // lista vacía... va al principio de la lista... modificar el header list...
            hl.setFirst( dir );
        }
        else
        {
            // va al final... cambiar el nodo en la posición ant... o sea: el último r leído!!!
            r.setNext( dir );
            this.seekByte( ant );
            this.write( r );
        }
        
        // incrementar en uno el contador en el header list...
        hl.increase();
        
        // bye bye...
        return true;
    }   

    // agrega un registro al principio de la lista h1, sin verificar 
    // repetición. Invocado por append().
    private void agregar( HeaderList hl, Grabable obj )
    {
        NodeRegister nuevo = new NodeRegister( obj, hl.getFirst() );        
        this.goFinal();
        long dir = this.bytePos();
        this.write ( nuevo );
        hl.setFirst( dir );
        hl.increase();
    }

    // elimina por marcado lógico TODAS las ocurrencias del objeto obj en
    // la lista hl. Invocado por remove().
    private boolean eliminar( HeaderList hl, Grabable obj )
    {
        boolean ok = false;
        
        // recorrer la lista y verificar si obj existe en ella...
        NodeRegister p = null;
        long d = hl.getFirst(), ant = NodeRegister.NIL;
        while( d != NodeRegister.NIL )
        {
            this.seekByte( d );
            NodeRegister r = ( NodeRegister ) this.read();
            
            // si lo encuentro, lo marco, lo regrabo, y SIGO (puede haber repetidos)
            if( r.getState() == Register.ACTIVO && obj.equals( r.getData() ) ) 
            {
                // marcarlo como borrado y volver a grabarlo...
                r.setState( Register.BORRADO );
                this.seekByte( d );
                this.write( r );
                
                // desengancharlo de la lista...
                long prox = r.getNext();
                if( ant == NodeRegister.NIL ) hl.setFirst( prox );
                else 
                {
                    p.setNext( prox );
                    this.seekByte( ant );
                    this.write( p );
                }
                
                hl.decrease();
                ok = true;
            }
            
            // guardar el último registro leído...
            p = r;
            
            // ... y avanzar
            ant = d; 
            d = r.getNext();
        }

        return ok;
    }
    
    // modifica TODAS las ocurrencias del objeto obj en la lista hl.
    // Invocado por update().
    private boolean modificar( HeaderList hl, Grabable obj )
    {
        boolean ok = false;
        
        // recorrer la lista y verificar si obj existe en ella...
        NodeRegister p = null;
        long d = hl.getFirst();
        while( d != NodeRegister.NIL )
        {
            this.seekByte( d );
            NodeRegister r = ( NodeRegister ) this.read();
            
            // si lo encuentro, lo cambio, lo regrabo, y SIGO (puede haber repetidos)
            if( r.getState() == Register.ACTIVO && obj.equals( r.getData() ) ) 
            {
                // cambiar el contenido del registro y volver a grabarlo...
                r.setData( obj );
                this.seekByte( d );
                this.write( r );                
                ok = true;
            }
            
            // ... y avanzar
            d = r.getNext();
        }

        return ok;
    }
    
    //********************************************************************************
    //******************************************************* Clases Internas Privadas
    //********************************************************************************
     
    /*
     * Un iterador concreto para la clase ListHashFile. Los iteradores de la clase 
     * Iterator permiten recorrer todos los objetos almacenados en todas las listas, 
     * sin garantizar ningún orden especial en el recorrido. Eventualmente puede volver
     * al primer objeto sin tener que cerrar y reabrir el archivo.
     *  
     * @author Ing. Valerio Frittelli.
     * @version Abril de 2010.
     */
    private class Iterator implements RegisterFileIterator
    {
           // el número relativo de registro actualmente accedido.
           private long currentIndex;
                     
           // un NodeRegister como auxiliar de operaciones.
           private NodeRegister reg = ( NodeRegister ) getRegisterInstance();

           // un Register asociado a un HeaderList como auxiliar de ooperaciones.
           private Register rhl = new Register( new HeaderList() );
    
           /**
            * Crea un iterador posicionado en el primer registro almacenado en el
            * archivo.
            */
           private Iterator() 
           {
               rehash( false );               
               currentIndex = begin_headers + capacity * rhl.sizeOf();
           }
    
           /**
            * Vuelve el iterador al primer registro del archivo.
            */
           public void first() 
           {
               currentIndex = begin_headers + capacity * rhl.sizeOf();
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
               reg = ( NodeRegister ) read();
               
               // retornar el objeto contenido en el registro leído
               return reg.getData();
           }
    }
}
