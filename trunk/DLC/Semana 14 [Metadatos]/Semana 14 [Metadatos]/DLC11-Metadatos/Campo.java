/**
 * Cada objeto de esta clase representa en forma básica un campo de un registro en un archivo o en una base 
 * de datos. 
 */
import java.io.*;
public class Campo
{
       private String   nombre;     // se asumiran no mas de 20 caracteres por nombre (40 bytes en total) 
       private int      tipo;       // 0: integer - 1: real - 2: cadena
       private int      cantidad;   // solo para cadenas!!!
       private boolean  esclave;
    
       /**
        *  Crea un objeto con valores por default
        */
       public Campo ()
       {
       }
       
       /**
        *  Crea el objeto tomando sus datos como parámetro
        */
       public Campo (String nom, int tip, int cant, boolean es)
       {
         nombre = nom;
         tipo = tip;
         cantidad = cant;
         esclave = es;
       }
    
       /**
        *  Accede al nombre
        *  @return el valor del nombre
        */
       public String getNombre()
       {
            return nombre;   
       }
    
       /**
        *  Accede al tipo
        *  @return el valor del tipo
        */
       public int getTipo()
       {
            return tipo;   
       }
    
       /**
        *  Accede a la cantidad
        *  @return el valor de la cantidad
        */
       public int getCantidad()
       {
            return cantidad;   
       }
    
       /**
        *  Accede al indicador de clave
        *  @return el valor del indicador de clave
        */
       public boolean isClave()
       {
            return esclave;   
       }

       /**
        *  Retorna el tamaño en bytes de un Campo según lo que ocupará en disco
        *  @return el tamaño en bytes del objeto
        */
       public static int sizeOf()
       {
         return 40 + 4 + 4 + 1;
       }
    
       /**
        *  Copia en sí mismo al objeto tomado como parámetro
        *  @param x el objeto a copiar
        */ 
       public void copiar (Campo x)
       {
          nombre    = x.nombre;
          tipo      = x.tipo;
          cantidad  = x.cantidad;
          esclave   = x.esclave;
       }
          
       
       /**
        *  Lee de disco un objeto
        *  @param arch el archivo desde el cual lee
        */
       public void fRead (RandomAccessFile arch) throws IOException
       {
          nombre    = Archivo.readString(arch, 20).trim();
          tipo      = arch.readInt();
          cantidad  = arch.readInt();
          esclave   = arch.readBoolean();
       }
    
       /**
        *  Graba en disco un objeto
        *  @param arch el archivo en donde se graba
        */
       public void fWrite (RandomAccessFile arch) throws IOException
       {
          Archivo.writeString (arch, nombre, 20);
          arch.writeInt(tipo);
          arch.writeInt(cantidad);
          arch.writeBoolean(esclave);
       }
}
