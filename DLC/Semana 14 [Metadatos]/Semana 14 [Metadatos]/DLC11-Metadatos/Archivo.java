/**
 *  Un ejemplo simple de un archivo de datos con descripción de metadatos asociada en la cabecera del mismo archivo 
 *  @author Ing. Valerio Frittelli
 *  @version Octubre de 2004
 */

import java.io.*;
import java.util.*;
public class Archivo
{
       private Campo            campos;
       private String           nombreGeneral;
       private File             descArch;
       private RandomAccessFile maestro;
       private long             iniDatos;    // dirección de inicio del área de datos en el archivo       
       private Campo []fields;               //arreglo para guardar los metadatos 
          
       /**
        * Crea un archivo con el nombre tabla.dat, con la descripción de sus campos en la cabecera a modo de metadatos
        */
       public Archivo()
       {
          // asumo que "nombre" viene sin extension...
          this ("tabla");
       }
    
       /**
        * Crea un descriptor de archivo con el nombre tomado como parámetro + extensión .dat, y 
        * agrega la descripción de sus campos como cabecera en el mismo archivo.
        */
       public Archivo(String nom)
       {
          nombreGeneral = nom;
          descArch = new File(nom + ".dat");
          campos   = new Campo();
       }
    
       /**
        *  Accede al nombre del archivo
        *  @return el nombre del archivo
        */
       public String getNombreGeneral()
       {
          return nombreGeneral;   
       }
    
       public Campo [] getCampos()
       {
          return fields;   
       }
    
       /**
        *  Retorna la dirección de inicio del área de datos en el archivo
        *  @return la dirección de inicio del área de datos
        */
       public long getIniDatos()
       {
          return iniDatos;   
       }
    
       /**
        *  Graba un registro de metadatos en la cabecera del archivo maestro
        *  @param cm el registro de metadatos a grabar
        */
       public void grabarMetaDato (Campo cm) throws IOException
       {
          cm.fWrite(maestro);   
       }
    

       /**
        *  Graba un vector de registros de metadatos en la cabecera del archivo maestro
        *  @param c el vector de registros de metadatos a grabar
        */
       public void grabarMetaDato (Vector c) throws IOException
       {
           // se supone que el archivo está recién creado y abierto
           
           // grabamos la cantidad de campos al inicio del archivo
           maestro.writeInt(c.size());
            
           // ahora grabamos los metadatos propiamente dichos
           for(int i = 0; i<c.size(); i++)
           {
               grabarMetaDato((Campo)c.get(i));
           }
           
           iniDatos = maestro.getFilePointer();
       }
    
    
       /**
        *  Crea el archivo, dejandolo abierto para grabar, y posicionado en el byte cero. Luego de la apertura, debería
        *  grabarse la información de metadatos. Si el archivo ya existía, lo pisa y lo vuelve a crear.
        */ 
       public void crear () throws IOException
       { 
           if(descArch.exists()) descArch.delete();
           maestro = new RandomAccessFile(descArch, "rw");
       }
    
    
       /**
        *  Abre el archivo de datos, que se supone que ya existía. Si no existía, se lanza una excepción declarada por el programador
        *  del tipo ArchivoInexistenteException. Crea un vector en memoria con la información de cabecera, y deja el file pointer del 
        *  archivo posicionado al final del archivo si la forma de apertura es "rw", o al principio del área de datos si es "r".
        *  @param forma el modo de apertura
        */
       public void abrir(String forma) throws IOException
       {
           if(!descArch.exists()) { throw new FileNotFoundException("No existe el archivo " + nombreGeneral + ".dat"); }
           maestro = new RandomAccessFile(descArch, forma);
           
           // crea el vector fields de metadatos
           crearVector();
           
           // el área de datos empieza después del indicador de la cantidad de campos (4 bytes) y la descripción de los campos 
           // (o sea, la suma de bytes que ocupa el vector grabado en disco)
           iniDatos = 4 + fields.length * Campo.sizeOf();
           
           // si la forma de apertura era "rw", llevamos el file pointer al final. Si no, lo dejamos donde quedó después de crear el
           // vector de campos (que es justo el lugar donde empieza el area de datos = iniDatos).
           if(forma.compareTo("rw") == 0) maestro.seek(maestro.length());
       }
    
       /**
        *  Cierra el archivo
        */
       public void cerrar() throws IOException
       {
           maestro.close();   
       }
       
        /**
         * Determina si se ha llegado al final del archivo o no
         * @return true si se llegó al final - false en caso contrario
         * @throws IOException si hubo problema en la operación
         */
        public boolean eof ( ) throws IOException
        {
          if (maestro.getFilePointer() < maestro.length()) return false;
          else return true;
        }
    
       /**
        *  Graba un int en el archivo de datos
        *  @param x el valor a grabar
        */
        public void grabarInt(int x) throws IOException
        {
            maestro.writeInt(x);   
        }

       /**
        *  Graba un float en el archivo de datos
        *  @param x el valor a grabar
        */
        public void grabarFloat(float x) throws IOException
        {
            maestro.writeFloat(x);   
        }

       /**
        *  Graba un String en el archivo de datos
        *  @param x el valor a grabar
        *  @param cant la cantidad de caracteres a grabar
        */
        public void grabarString(String x, int cant) throws IOException
        {
            writeString(maestro, x, cant);
        }

       /**
        *  Lee un int del archivo de datos
        *  @return el valor leido
        */
        public int leerInt() throws IOException
        {
            return maestro.readInt();   
        }

       /**
        *  Lee un float del archivo de datos
        *  @return el valor leido
        */
        public float leerFloat() throws IOException
        {
            return maestro.readFloat();   
        }

       /**
        *  Lee un String del archivo de datos
        *  @param cant la cantidad de caracteres a leer
        *  @return el valor leido
        */
        public String leerString(int cant) throws IOException
        {
            return readString(maestro, cant).trim();   
        }

       /**
        * Lee desde un archivo un String de "tam" caracteres
        * @param  arch el archivo desde el cual se lee
        * @param  tam la cantidad de caracteres a leer
        * @return el String leido
        * @throws IOException si hubo algún problema de lectura
        */
       public static final String readString (RandomAccessFile arch, int tam)throws IOException
       { 
         char vector[] = new char[tam];
         for(int i = 0; i<tam; i++)
         {
           vector[i] = arch.readChar();
         }
         String cad = new String(vector,0,tam);
         return cad;
       }

       /**
        * Graba en un archivo un String de "tam" caracteres
        * @param  arch el archivo en el cual se graba
        * @param  cad la cadena a a grabar 
        * @param  tam la cantidad de caracteres a grabar
        * @throws IOException si hubo algún problema de grabacion
        */
       public static final void writeString (RandomAccessFile arch, String cad, int tam) throws IOException
       {
         int i;
         char vector[] = new char[tam];
         for(i=0; i<tam; i++)
         {
            vector[i]= ' ';
         }
         cad.getChars(0, cad.length(), vector, 0);
         for (i=0; i<tam; i++)
         {
            arch.writeChar(vector[i]);
         }
       } 


       /**
        *  Crea un vector (miembro de la clase) con la información de todos los campos
        */
       private void crearVector()throws IOException
       {
            // se supone que el archivo ya está abierto
            int i;
            int tam = Campo.sizeOf();
            
            // los primeros 4 bytes del archivo, informan la cantidad de campos que vienen en la cabecera
            maestro.seek(0);
            int cantcampos = maestro.readInt();

            fields = new Campo[cantcampos];
            for (i = 0; i < cantcampos; i++) fields[i]= new Campo();

            for (i = 0; i < cantcampos; i++)
            {
               campos.fRead(maestro);
               fields[i].copiar(campos);
            }
       }
}
