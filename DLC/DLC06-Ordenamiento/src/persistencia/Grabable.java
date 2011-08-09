package persistencia;

/**
 * Indica el comportamiento que debe ser capaz de mostrar un objeto que vaya a ser grabado en 
 * un RegisterFile. Observar que un Grabable ES UN Comparable que se sabe almacenar en un 
 * RandomAccessFile, se sabe leer desde un RandomAccessFile, y sabe indicar su tamaño en bytes
 * cuando se almacena en un RandomAccessFile. 
 * El método compareTo() se deja para ser implementado por las clases que implementen
 * Grabable, con lo cual se obliga a todas ellas a contar con ese método.
 * 
 * @author Ing. Valerio Frittelli
 * @version Marzo de 2008
 */

import java.io.*;
public interface Grabable extends Comparable
{
    /**
     *  Calcula el tamaño en bytes del objeto, tal como será grabado en un RegisterFile.
     *  @return el tamaño en bytes del objeto.
     */
    int sizeOf();
    
    /**
     *  Indica cómo grabar un objeto en un RandomAccessFile. Este método será invocado
     *  por los métodos de la clase RegisterFile para almacenar el objeto en disco.
     *  @param el RandomAccessFile donde será grabado el objeto
     */
    void grabar( RandomAccessFile a );
    
    /**
     *  Indica cómo leer un objeto desde un RandomAccessFile. Este método será invocado
     *  por los métodos de la clase RegisterFile para leer el objeto desde disco.
     *  @param a el archivo donde se hará la lectura
     */
    void leer( RandomAccessFile a );
}
