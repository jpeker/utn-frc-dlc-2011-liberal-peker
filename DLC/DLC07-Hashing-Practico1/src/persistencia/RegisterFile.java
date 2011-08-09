package persistencia;
/**
 * Una clase para representar un archivo binario de registros de longitud fija, con posibilidad de acceso directo. 
 * Siempre se supone que el contenido de un RegisterFile son registros uniformes (del mismo tipo) y de la misma 
 * longitud, lo cual favorece el seeking. El archivo no permite grabar objetos cuyo tipo (y tamaño) no coincida
 * con los que ya se grabaron en el archivo. Para eso, almacena al principio del archivo los datos de la clase a 
 * la cual pertenecen los objetos cuyos datos se almacenaron en él (esto es, la "clase base" del RegisterFile).
 * @author Liberal - Peker
 * @version May de 2011.
 */

import java.io.*;
import javax.swing.*;
public abstract class RegisterFile
{
    //****************************************************************************
    //******************************************************* Atributos Protegidos
    //****************************************************************************
    
    // Descriptor del archivo para acceder a sus propiedades externas
    protected File fd;                   
    
    // Manejador del archivo
    protected RandomAccessFile maestro;  
    
    // Usado para registrar la "clase base" de los objetos que se graban en el archivo.
    // Si luego de invocar a un constructor, este atributo queda null, es que el archivo 
    // estaba vacío y no había una clase asociada a su contenido.
    protected Grabable clase;
    
    // Para marcar el lugar donde comienzan a grabarse los datos, luego del nombre de la clase base.
    // Si luego de invocar a un constructor, este atributo queda en cero, es que el archivo estaba 
     // vacío y no había una clase asociada a su contenido.
    protected long marca;    
    
    // Para registrar en qué modo está abierto el archivo.
    protected String apertura;
    
    
    //**************************************************************************
    //************************************************************ Constructores
    //**************************************************************************
   
    /**
     * Crea un manejador para el archivo, asociando al mismo el nombre "newfile.dat " a modo 
     * de file descriptor. Abre el archivo en disco asociado con ese file descriptor, en el 
     * directorio default, y en modo "rw" (tal como se se usa en la clase RandomAccessFile). 
     * Si ocurre un problema al intentar abrir el archivo, la aplicación finalizará.
     * @param obj un objeto de la clase base para el archivo. 
     */
    public RegisterFile ( )
    {        
        this ( "newfile.dat", "rw" );
    }  
    
    /**
     * Crea un manejador para el RegisterFile, asociando al mismo un nombre a modo de file 
     * descriptor. Abre el archivo en disco asociado con ese file descriptor, en el modo 
     * indicado por el segundo parámetro. El modo de apertura es tal y como se usa en la clase
     * RandomAccessFile: "r" para sólo lectura y "rw" para lectura y grabación. Si el modo de
     * apertura enviado es null, o no es "r" ni "rw", se asumirá "r". Si la referencia "nombre"
     * es null, o si la cadena "nombre" es una cadena vacía o un blanco, el nombre del archivo
     * se asumirá como "newfile.dat". Si ocurre un problema al intentar abrir el archivo, la
     * aplicación finalizará.
     * @param nombre es el nombre físico y ruta del archivo a crear / abrir.
     * @param modo es el modo de apertura del archivo. 
     */
    public RegisterFile ( String nombre, String modo )
    {   
        if( nombre == null || nombre.equals("") || nombre.equals(" ") ) nombre = "newfile.dat";
        init ( new File( nombre ), modo );
    }  
    
    /**
     * Crea un manejador para el RegisterFile, asociando al mismo un file descriptor. Abre el 
     * archivo en disco asociado con ese file descriptor, en el modo indicado por el segundo 
     * parámetro. El modo de apertura es tal y como se usa en la clase RandomAccessFile: "r"
     * para sólo lectura y "rw" para lectura y grabación. Si el modo de apertura enviado es null,
     * o no es "r" ni "rw", se asumirá "r". Si la referencia "file" es null, el nombre del archivo
     * se asumirá como "newfile.dat". Si ocurre un problema al intentar abrir el archivo, la
     * aplicación finalizará.
     * @param file es el pathname del archivo a crear.
     * @param modo es el modo de apertura del archivo.
     */
    public RegisterFile ( File file, String modo )
    {        
        init( file, modo );
    }  
      
    
    //*****************************************************************************************
    //******************************************************** Métodos públicos de "bajo nivel"
    //*****************************************************************************************
    
    /**
     *  Accede al descriptor del archivo.
     *  @return un objeto de tipo File representando el pathname abstracto del archivo.
     */
    public File getFileDescriptor()
    {
        return fd;   
    }   
    
    /**
     * Retorna el nombre de la clase base asociada a este archivo. Si el archivo no tiene asociada 
     * una clase base (por estar físicamente vacío), entonces el método retorna null.
     * @return el nombre de la clase base del archivo.
     */
    public String getBaseClassName()
    {
        if( clase == null ) return null;
        return clase.getClass().getName();
    }
    
    /**
     *  Devuelve el modo en que fue abierto el archivo (una cadena de la forma "r" o "rw").
     *  @return un String con el modo de apertura original del archivo.
     */
    public String getMode()
    {
        return apertura;   
    }
    
    /**
     * Borra el archivo del disco.
     * @return true si se pudo borrar, o false en caso contrario.
     */
    public boolean delete()
    {
        return fd.delete();
    }
    
    /**
     * Cambia el nombre del archivo.
     * @param nuevo otro RegisterFile, cuyo nombre (o file descriptor) será dado al actual.
     * @return true si el cambio de nombre pudo hacerse, false en caso contrario.
     */
    public boolean rename( RegisterFile nuevo )
    {
        return fd.renameTo( nuevo.fd ); 
    }
       
    /**
     * Cierra el archivo asociado con el RegisterFile. Los atributos del objeto siguen con 
     * sus valores actuales.
     */
    public void close()
    {
        try
        {
            maestro.close();
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al cerrar el archivo " + fd.getName() + ": " + e.getMessage() );
            System.exit( 1 );
        }
    }
    
    /**
     * Ubica el file pointer en la posición del byte número b. Esa posición podría no coincidir con el
     * inicio de un registro. El número b podría ser mayor que el tamaño del archivo, sin problemas.
     * Si hay algún inconveniente al hacer el posicionamiento (por ejemplo, b negativo), la aplicación
     * terminará.
     * @param b número del byte que se quiere acceder, contando desde el principio del archivo.
     */
    public void seekByte ( long b )
    {
        try
        {
            maestro.seek( b );
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al posicionar en el byte número " + b + ": " + e.getMessage() );
            System.exit(1);
        }
    }
    
    /**
     * Rebobina el archivo: ubica el file pointer en la posición donde comienzan los datos puros del archivo.
     * Note que esta posición no es necesariamente la posición cero del archivo (salvo que el archivo esté
     * vacío físicamente)
     */
    public void rewind()
    {
        try
        {
            maestro.seek( marca );
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al rebobinar el archivo: " + e.getMessage() );            
            System.exit(1);
        }
    }
    
    /**
     * Devuelve el número de byte en el cual está posicionado el archivo en este momento.
     * @return el número de byte de posicionamiento actual.
     */
    public long bytePos () 
    {
        try
        {
            return maestro.getFilePointer();
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al intentar devolver el número de byte: " + e.getMessage() );
            System.exit( 1 );
        }
        
        return -1;
    }
    
    /**
     * Posiciona el file pointer al final del archivo.
     */
    public void goFinal() 
    {
        try
        {
            maestro.seek( maestro.length() );
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al posicionar al final: " + e.getMessage() );            
            System.exit( 1 );
        }
    }
    
    /**
     * Devuelve la longitud del archivo, en bytes.
     * @return el total de bytes del archivo.
     */
    public long length()
    {
        try
        {
            return maestro.length();
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al determinar el tamaño del archivo: " + e.getMessage() );
            System.exit(1);
        }
        
        return 0;
    }    
    
    /**
     * Determina si se ha llegado al final del archivo o no.
     * @return true si se llegó al final - false en caso contrario.
     */
    public boolean eof()
    {
        try
        {
            return ! ( maestro.getFilePointer() < maestro.length() );
        }
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Error al determinar el fin de archivo: " + e.getMessage() );  
            System.exit( 1 );
        }
        
        return true;
    }  
    
    
    //******************************************************************************************
    //******************************************************* Métodos abstractos de "alto nivel"
    //******************************************************************************************
    
    /**
     * Crea y retorna un iterador para este archivo.
     */
    public abstract RegisterFileIterator createIterator();
    
    /**
     * Busca un registro en el archivo. Retorna true si el registro se encuentra, o false si no. 
     * El file pointer debería salir apuntando al mismo byte donde estaba cuando empezó la operación.
     * @param obj el objeto a buscar en el archivo.
     * @return true si el archivo contiene un objeto igual a obj.
     */
    public abstract boolean contains ( Grabable obj );
    
    /**
     * Busca un registro en el archivo. Retorna una copia del registro que estaba en el archivo
     * si es que el archivo contenía uno igual al que entra como parámetro. Si no lo encuentra,
     * retorna null. El file pointer debería salir apuntando al mismo byte donde estaba cuando
     * empezó la operación.
     * @param obj el objeto a buscar en el archivo.
     * @return una referencia al registro encontrado, o null si no se encontró.
     */
    public abstract Grabable find ( Grabable obj );
       
    /**
     * Agrega un registro en el archivo. La convención asumida para add() es impedir repeticiones de datos.
     * @param obj el objeto a agregar.
     * @return true si fue posible agregar el registro - false si no fue posible.
     */
    public abstract boolean add ( Grabable obj );

    /**
     * Agrega un registro en el archivo. La convención asumida para append() es no controlar repeticiones eventuales.
     * @param obj el registro a agregar. 
     * @return true si fue posible agregar el registro - false si no fue posible.
     */
    public abstract boolean append ( Grabable obj );
    
    /**
     * Borra un registro del archivo. La técnica de borrado se deja para las clases derivadas.
     * @param obj el registro a buscar y borrar.
     * @return true si fue posible borrar el registro - false si no fue posible.
     */
    public abstract boolean remove ( Grabable obj );
     
    /**
     * Modifica un registro en el archivo. La convención asumida para update() es reemplazar
     * el registro en una posición dada, cambiando sus datos por otros que vienen en el objeto
     * tomado como parámetro.
     * @param obj el objeto con los nuevos datos.
     * @return true si fue posible modificar el registro - false si no fue posible
     */
    public abstract boolean update ( Grabable obj );
      
    
     /**
      * Depura el contenido del archivo. El significado de esta operación depende fuertemente
      * del concepto de "registro inválido" que manejen las clases derivadas. La implementación
      * podría incluso quedar vacía en alguna derivada si el concepto de depuración no fuera
      * aplicable.
      */
     public abstract void clean();

     
     //************************************************************************************
     //******************************************************* Métodos públicos y estáticos
     //************************************************************************************

     /**
      * Lee desde un archivo un String de "tam" caracteres. Se declara static para que pueda 
      * ser usado en forma global por cualquier clase que requiera leer una cadena de longitud 
      * fija desde un archivo manejado a través de un objeto RandomAcessFile. Se supone que la
      * cadena está grabada a partir de la posición actual dentro del RandomAccessFile, y que
      * fue grabada tal como indica el método writeString().
      * @param  arch el manejador del archivo desde el cual se lee la cadena. Se supone abierto
      * y posicionado en el lugar correcto.
      * @param  tam la cantidad de caracteres a leer.
      * @return el String leido, removiendo los blancos que pudiera tener al final.
      */
     public static final String readString ( RandomAccessFile arch, int tam )
     { 
         String cad = "";
         
         try
         {
             char vector[] = new char[ tam ];
             for( int i = 0; i<tam; i++ )
             {
                vector[i] = arch.readChar();
             }
             cad = new String( vector, 0, tam );
         }
         catch( IOException e )
         {
            JOptionPane.showMessageDialog( null, "Error al leer una cadena: " + e.getMessage() );
            System.exit( 1 );
         }
         
         return cad.trim();
     }
    
     /**
      * Graba en un archivo un String de "tam" caracteres. Se declara static para que pueda ser 
      * usado forma en global por cualquier clase que requiera grabar una cadena de longitud fija 
      * en un archivo. La cadena se graba de tal forma que si no llegaba a tener tam caracteres, se 
      * agrega la cantidad necesaria de blancos al final de la cadena para llegar a la cantidad tam. 
      * @param  arch el manejador del archivo en el cual se graba, que se supone abierto en modo "rw" 
      * y posicionado en el lugar correcto.
      * @param  cad la cadena a grabar.
      * @param  tam la cantidad de caracteres a grabar.
      */
     public static final void writeString ( RandomAccessFile arch, String cad, int tam )
     {
         try
         {
             int i;
             char vector[] = new char[tam];
             for( i=0; i<tam; i++ )
             {
                vector[i]= ' ';
             }
             cad.getChars( 0, cad.length(), vector, 0 );
             for ( i=0; i<tam; i++ )
             {
                arch.writeChar( vector[i] );
             }
         }
         catch( IOException e )
         {
             JOptionPane.showMessageDialog(null, "Error al grabar una cadena: " + e.getMessage());
             System.exit(1);
         }
     } 
     
     
     //*************************************************************************
     //****************************************************** Métodos Protegidos
     //*************************************************************************
     
     /**
      *  Acceso al manejador del archivo binario. Use con cuidado...
      *  @return un objeto de tipo RandomAccessFile que permite acceder al bloque físico
      *  de datos en disco, en forma directa.
      */
     protected RandomAccessFile getMasterFile()
     {
        return maestro;   
     }
    
     /**
      *  Cambia el manejador del archivo binario. Si el parámetro "raf" es null, el método
      *  no hace nada. Use con cuidado...
      *  @param raf un objeto de tipo RandomAccessFile para asignar como archivo maestro 
      *  del RegisterFile. 
      */
     protected void setMasterFile( RandomAccessFile raf )
     {
         if( raf == null ) return;
         maestro = raf;
     }
     
     /**
      *  Intenta una apertura del archivo en "bajo nivel", en el modo pedido. Si se presenta 
      *  algún problema en el momento de intentar abrir el archivo, la aplicación terminará.
      *  @param modo el modo en que se intentará abrir el archivo.
      */ 
     protected void open( String modo )
     {
        try
        {
            // se intenta abrir el archivo...
            maestro = new RandomAccessFile( fd, modo );
        }            
        catch( IOException e )
        {
            JOptionPane.showMessageDialog( null, "Problema al abrir el archivo: " + e.getMessage() );
            System.exit(1);
        }           
     }
 
     /**
      *  Crea una instancia de la clase Register, asociada a un objeto vacío de la clase base
      *  del archivo. Si no se puede crear esta instancia, la aplicación terminará.
      */ 
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
        return new Register( aux );          
     }
     
     /**
      * Controla si objeto obj es válido para ser grabado en este archivo. Es válido si no
      * es null y su clase coincide con la clase base del archivo.
      * @param obj el objeto cuyos datos se desea grabar en el archivo.
      * @return true si el objeto es compatible con el contenido del archivo.
      */ 
     protected boolean isOk( Grabable obj )
     {
        if( obj == null ) return false;
        if( clase != null && clase.getClass() != obj.getClass() ) return false;
        return true;
     }
     
     /**
      * Intenta leer el nombre de la clase base. Retorna ese nombre si lo logra, o null si no pudo.
      * @return una cadena con el nombre de la clase base tal como lo lee desde la cabecera del 
      * archivo, o null si no puede leer esa cadena.
      */ 
     protected String readClassName()
     {
        String cn = null;
        try
        {
            cn = maestro.readUTF();
        }
        catch( IOException e )
        {
        }
        return cn;
     }
 
     /**
      * Intenta grabar el nombre de la clase base, tomándolo desde el objeto que entra como
      * parámetro. Si no lo logra, la aplicación termina lanzando un mensaje de error.
      * @param obj un objeto del cual tomará el nombre de la clase a grabar en el archivo.
      */ 
     protected void writeClassName( Grabable obj )
     {
        try 
        {
            clase = obj;
            maestro.writeUTF( clase.getClass().getName() );
            marca = maestro.getFilePointer();
        }
        catch( IOException e )
        {
             JOptionPane.showMessageDialog( null, "Error al grabar la clase base: " + e.getMessage() );
             System.exit( 1 );
        }
     }
     
     /**
      * Graba un registro en el archivo, a partir de la posición dada por el file pointer en ese
      * momento. El archivo se supone abierto en modo de grabación.
      * @param r el Register a grabar.
      * @return true si la grabación pudo hacerse.
      */
     protected boolean write ( Register r )
     {
        if( r != null )
        {
            try 
            {
                r.grabar( maestro );
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog( null, "Error al grabar el registro: " + e.getMessage() );
                System.exit( 1 );
            }
            return true;
        }
        return false;
     }
    
     /**
      * Lee un registro del archivo, a partir de la posición del file pointer en ese momento.
      * El archivo se supone abierto y posicionado en el lugar correcto. Si la lectura no puede
      * hacerse por cualquier motivo, la aplicaci�n terminará.
      * @return una referencia al registro leído.
      */
     protected Register read ( )
     {
        Register r = null;
        try
        {
            r = getRegisterInstance();
            r.leer( maestro );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( null, "Error al leer el registro: " + e.getMessage() );
            System.exit(1);
        }
        return r;
     }
     
     //*************************************************************************
     //******************************************************** Métodos Privados
     //*************************************************************************

     // auxiliar para los constructores.
     private void init( File file, String modo )
     {
        // controlamos y fijamos el nombre del archivo.
        if( file == null ) file = new File( "newfile.dat" );
        fd = file;
        
        // controlamos el modo de apertura, e intentamos abrir el archivo.
        if ( modo == null || ( ! modo.equals( "r" ) && ! modo.equals( "rw" ) ) ) modo = "r";   
        open( modo );
        
        // guardamos el modo de apertura en un atributo de la clase.
        apertura = modo;
        
        // el archivo existe y est� abierto: intentamos leer el nombre de la clase base.
        clase = null;
        String cb = readClassName();
        if( cb != null )
        {
            try
            {
                // si fue posible, creamos el objeto Class para representar la clase base...
                clase = ( Grabable ) Class.forName( cb ).newInstance();
            }
            catch( Exception e )
            {
                JOptionPane.showMessageDialog(null, "Clase base no existe (posible formato incorrecto de archivo): " + e.getMessage());
                System.exit(1);
            }
        }
        
        // y marcamos el lugar donde quedó el file pointer, para saber donde comienzan los datos puros...
        // ...si quedó en cero, es que el archivo estaba vacío (y no hay un nombre aún para la clase base)
        marca = bytePos();
     }
}
