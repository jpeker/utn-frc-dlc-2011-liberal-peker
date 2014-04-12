package compresor;

/**
 * Un compresor de archivos basado en un Arbol de Huffman.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2007
 */
import java.io.*;
public class Compresor
{
    private ArbolHuffman ht;
    private RandomAccessFile fuente;
    private RandomAccessFile comprimido;
    private RandomAccessFile nuevo;
    private String errorCode;
    private int cantSignos;

    /**
     *  Crea un compresor y lo prepara para recibir una tabla de n caracteres
     */
    public Compresor()
    {
       cantSignos = 0;
       errorCode = "Compresor preparado";
    }

    /**
     *  Devuelve el estado del compresor luego de una operacion dada
     *  @return un String con el estado del compresor
     */
    public String getErrorCorde() 
    { 
        return errorCode; 
    }

    /**
     *  Comprime un archivo usando un Arbol de Huffman para determinar el codigo
     *  de bit de cada signo. Genera un archivo comprimido con el mismo nombre
     *  que el original, pero con extension .cmp
     *  @param fileName el nombre del archivo a comprimir
     */
    public void comprimir (String fileName)
    {
        try
        {  
            //obtengo el nombre del archivo, sin la extension
            String nombre = fileName.substring( 0, fileName.indexOf(".") );
            
            // abro los archivos
            File f1 = new File(fileName);
            File f2 = new File(nombre + ".cmp");
            
            fuente     = new RandomAccessFile (f1, "r");
            comprimido = new RandomAccessFile (f2, "rw");
            comprimido.setLength(0);
            
            // cuento cuantas veces aparece cada byte en el archivo
            int i;
            byte car;

            int c[] = new int[256];  // un vector de contadores
            for(i=0; i<256; i++) { c[i] = 0; }
            
            // contamos los signos...
            while(fuente.getFilePointer() < fuente.length())
            {
                 car = fuente.readByte();            // leo un byte del archivo...
                 short sc = (short) (car & 0x00FF);  // ... lo convierto a short para evitar problemas de desborde 
                 c[ sc ]++;   // cuento ese byte!!!
            }
                       
            // cuento cuantos signos diferentes hay...
            cantSignos = 0;
            for(i = 0; i < 256; i++) 
            { 
                if( c[i] != 0 ) { cantSignos++; }
            }

            // creamos el Arbol con lugar para esa cantidad de signos
            ht = new ArbolHuffman(cantSignos); 
            
            // inicializamos el arbol de Huffman con los signos y sus frecuencias
            int ind = 0;
            for(i = 0;  i < 256;  i++)
            {
                  if( c[i] != 0 )
                  {
                      ht.setNodo((byte)i , c[i], ind);
                      ind++;
                  }
            }
            
            // armamos el arbol y obtenenos el codigo Huffman de cada signo
            ht.codificar();

            // cantidad de bytes del archivo fuente
            long tArch = fuente.length();  
            
            // guardo en el archivo comprimido informacion para el descompresor...
            
            // ...empiezo guardando el nombre y la extension del original...
            comprimido.writeUTF(fileName);
            
            // ... luego guardo la longitud en bytes del archivo original...
            comprimido.writeLong(tArch);
            
            // ... la cantidad de simbolos (o sea, la cantidad de hojas del arbol)...
            comprimido.writeInt(cantSignos);
            
            // ... ahora la tabla de simbolos tal como esta en el arbol...
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = ht.getSigno(i);
                comprimido.writeByte(signo);
            }

            // ... ahora el vector que representa al arbol...
            NodoHuffman a[] = ht.getArbol();
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del arbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, guardar todos sus datos...
                comprimido.writeInt( a[i].getFrecuencia() );
                comprimido.writeInt( a[i].getPadre() );
                comprimido.writeBoolean( a[i].isLeft() );
                comprimido.writeInt( a[i].getIzquierdo());
                comprimido.writeInt( a[i].getDerecho());
            }
            
            // comienza fase de compresion (por fin...)
            short mascara = 0x0080;  // el valor 0000 0000 1000 0000
            short salida  = 0x0000;  // el valor 0000 0000 0000 0000
            int bit = 0;             // en que bit vamos?
            
            fuente.seek(0);   // vuelvo el fp al principio
            while(fuente.getFilePointer() < fuente.length())
            {
                car = fuente.readByte();
                
                // codigo Huffman del caracter tomado
                CodigoHuffman hc = ht.getCodigo(car);
                byte []v = hc.getCodigo();
                int  ini = hc.getStartPos();

                for(i = ini; i < CodigoHuffman.MAXBITS; i++)
                {
                    if(v[i] == 1)
                    {
                        // si era 1, lo bajamos al byte de salida (si era cero, ni modo...)
                        salida = (short)(salida | mascara);
                    }   
                    mascara = (short) (mascara >>> 1);  // corremos el uno a la derecha, rellenando con ceros a la izquierda...
                    bit++;
                    if (bit == 8)
                    {
                        //se llenó el byte de salida...
                        comprimido.writeByte( (byte)salida ); // graba el menos significativo!!! 
                        bit = 0;
                        mascara = 0x0080;
                        salida  = 0x0000;
                    }
                }
            }

            if (bit != 0) 
            {
                // grabar el ultimo byte que estaba incompleto
                comprimido.writeByte( (byte)salida );  // graba el menos significativo!!!
            }
            comprimido.close();
            fuente.close();
    
        }
        
        catch(IOException e)
        {
            System.out.println("Error de IO: " + e.getMessage());   
        }
        
        catch(Exception e)
        {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    
    public void descomprimir(String fileName)
    {
        try
        {
            int pos = fileName.indexOf(".");
            if(pos == -1) { throw new Exception ("El archivo no parece un archivo comprimido..."); }
            
            String ext = fileName.substring( pos + 1 );
            if( ext.compareTo("cmp") != 0 ) { throw new Exception ("El archivo no tiene la extension cmp..."); }
            
            // abro el archivo comprimido...
            File f1 = new File( fileName );
            comprimido = new RandomAccessFile(f1, "r");    
            
            // ... y recupero el nombre del archivo original
            String original = comprimido.readUTF();
            
            // creo el archivo con el nombre del original
            File f2 = new File(original);
            if( f2.exists() ) { f2.delete(); }
            nuevo = new RandomAccessFile(f2, "rw");
            
            // y ahora, recupero todos los datos que el compresor dejó adelante...
            
            // ... empezando por el tamaño del archivo original...
            long tArch = comprimido.readLong();
            
            // ... la cantidad de signos de la tabla (o sea, la cantidad de hojas)...
            cantSignos = comprimido.readInt();
            
            // ...creo de nuevo el arbol en memoria...
            ht = new ArbolHuffman(cantSignos);
            
            // ... y recupero uno a uno los signos originales, guardandolos de nuevo en el arbol...
            int i;
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = comprimido.readByte();
                ht.setSigno(signo, i);
            }
            
            // ...ahora le toca al vector del arbol...
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del arbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, recuperar todos sus datos y volver a armar el arbol...
                int f  = comprimido.readInt();           // frecuencia
                int padre = comprimido.readInt();        // padre
                boolean left = comprimido.readBoolean(); // es izquierdo?
                int hi = comprimido.readInt();           // hijo izquierdo
                int hd = comprimido.readInt();           // hijo derecho
                NodoHuffman nh = new NodoHuffman( f, padre, left, hi, hd );
                ht.setNodo( nh, i );
            }
            
            // y habiendo llegado acá, el descompresor vuelve a pedir que se creen los codigos de Huffman
            ht.obtenerCodigos();
                       
            // de aca saco el vector que representa al arbol y el indice de la raiz...
            NodoHuffman []v2 = ht.getArbol();
            int raiz =  v2.length - 1;  // la raiz esta en la ultima casilla del vector!!!!
            
            // comienza la fase de descompresion
            short aux;                     // auxiliar para desenmascarar
            short mascara;
            int bit, nodo = raiz;          // comenzamos desde la raiz y vamos bajando
            long cantBytes = 0;            // cuantos bytes llevo grabados??
            
            // leo byte por byte el archivo comprimido
            while(comprimido.getFilePointer() < comprimido.length())
            {
                byte  car = comprimido.readByte();
                short sCar = (short) (car & 0x00FF);  // guardo el byte en un short, pero con todo el primer byte en cero 
                mascara = 0x0080;
                for(bit = 0; bit < 8 && cantBytes != tArch; bit++)
                {
                    aux = (short)(sCar & mascara);
                    if(aux == mascara)
                    {
                        // el bit en la posicion "bit" era un uno...
                        nodo = v2[nodo].getDerecho();    
                    }
                    else 
                    {
                        // era un cero...
                        nodo = v2[nodo].getIzquierdo();
                    }
                    mascara = (short)(mascara >>> 1);  // corremos el 1 a la derecha y rellenamos con ceros a la izquierda...

                    if (v2[nodo].getIzquierdo() == -1 && v2[nodo].getDerecho() == -1)
                    {
                        // llegamos a una hoja... grabar el signo que está en ella
                        byte sal = ht.getSigno(nodo);
                        nuevo.writeByte(sal);
                        cantBytes++;

                        // volver a la raiz
                        nodo = raiz;
                    }
                }
            }
            nuevo.close();
            comprimido.close();
        }
        
        catch(IOException e)
        {
            System.out.println("Error de IO: " + e.getMessage());   
        }
        
        catch(Exception e)
        {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}
