package persistencia;

/**
 * Una clase para representar un archivo con estructura hash, de registros de longitud fija. 
 * La clase provee una versión simple de la función de dispersión. Las clases derivadas deben
 * decidir la forma final de implementación de la técnica hash que usará el archivo para resolver
 * colisiones.
 * @author Liberal - Peker
 * @version Marzo de 2010.
 */

import java.io.*;
public abstract class HashFile extends RegisterFile
{              
    //*********************************************************************
    //******************************************************* Constructores
    //*********************************************************************
   
    /**
     * Crea un manejador para el HashFile, asociando al mismo el nombre "newfile.dat " a 
     * modo de file descriptor. Abre el archivo en disco asociado con ese file descriptor, 
     * en el directorio default, y en modo "rw" (tal como se se usa en la clase RandomAccessFile). 
     * Si ocurre un problema al intentar abrir el archivo, la aplicación finalizará.
     * @param obj un objeto de la clase base para el archivo.
     */
    public HashFile ( )
    {        
        super( "newfile.dat", "rw" );
    }  
    
    /**
     * Crea un manejador para el HashFile, asociando al mismo un nombre a modo de file 
     * descriptor. Abre el archivo en disco asociado con ese file descriptor, en el modo indicado 
     * por el segundo par�metro. El modo de apertura es tal y como se usa en la clase RandomAccessFile: 
     * "r" para s�lo lectura y "rw" para lectura y grabación. Si el modo de apertura enviado es null,
     * o no es "r" ni "rw", se asumirá "r". Si la referencia nombre es null, o si la cadena nombre es
     * una cadena vacía o un blanco, el nombre del archivo se asumirá como "newfile.dat". Si ocurre un
     * problema al intentar abrir el archivo, la aplicación finalizará.
     * @param nombre es el nombre físico y ruta del archivo a crear / abrir.
     * @param modo es el modo de apertura del archivo.
     */
    public HashFile( String nombre, String modo )
    {   
        super( nombre, modo );
    }  
    
    /**
     * Crea un manejador para el HashFile, asociando al mismo un file descriptor. Abre el archivo en 
     * disco asociado con ese file descriptor, en el modo indicado por el segundo par�metro. El modo de 
     * apertura es tal y como se usa en la clase RandomAccessFile: "r" para sólo lectura y "rw" para
     * lectura y grabación. Si el modo de apertura enviado es null, o no es "r" ni "rw", se asumirá "r".
     * Si la referencia file es null, el nombre del archivo se asumirá como "newfile.dat". Si ocurre un
     * problema al intentar abrir el archivo, la aplicación finalizará.
     * @param file es el pathname del archivo a crear.
     * @param modo es el modo de apertura del archivo.
     */
    public HashFile( File file, String modo )
    {        
        super( file, modo );
    }  
    
    /**
     * Calcula el siguiente número primo distinto de 2, comenzando el cálculo
     * desde el valor n.
     * @param n el número a partir del cual se busca el siguiente número primo.
     * @return el siguiente número primo a partir de n.
     */ 
    public static final long siguientePrimo( long  n )
    {
        if( n <= 1 ) return 3;
    	if( n % 2  == 0)   n++;
    	for( ; ! esPrimo( n ); n += 2 ) ;
    	return n;
    }
    
    /**
     * Determina si n es primo, sabiendo que n es impar.
     * @param n el número a analizar.
     * @return true si el número es primo.
     */ 
    public static final boolean esPrimo( long n )
    {
        if( n == 2 ) return true;
        if( n % 2 == 0 ) return false;
        long raiz = ( long ) Math.sqrt( n );
        boolean ok = true;
        for( long div = 3; div <= raiz; div += 2 )
        {
            if( n % div == 0 ) return false;
        }
        return true;
    }

    /**
     * Retorna un índice válido para entrar en la tabla representada por este archivo y ubicar al
     * objeto dado por obj. El método supone que obj está ya validado.
     */
    protected long h( Grabable obj )
    {
        int k = Math.abs( obj.hashCode() );
        return k % this.tableSize();

    }
    
    /**
     * Retorna el tamaño de la tabla hash representada por este archivo. El verdadero significado de
     * "tamaño de la tabla" dependerá de cómo cada clase derivada implemente esa tabla: en archivos
     * hash en base a listas de desborde, el tamaño indicará la cantidad de listas representadas en
     * la tabla, pero en archivos hash con direccionamiento abierto, representará la cantidad máxima
     * de registros que se prevé almacenar en ella.
     */
    protected abstract long tableSize();
}
