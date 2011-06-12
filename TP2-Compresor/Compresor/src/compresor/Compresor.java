/*
 * Compresor.java
 *
 */

package compresor;
/**
 * Un compresor de archivos basado en un Arbol de Huffman.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2007
 */
import GUI.GestorVentanaPrincipal;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
public class Compresor
{
    private ArbolHuffman ht;
    private RandomAccessFile fuente;
    private RandomAccessFile comprimido;
    private RandomAccessFile nuevo;
    private String errorCode;
    private int cantSignos;
    private String nombre;
    private String ruta;
    private Long tamanio;
    private int cont;
    private GestorVentanaPrincipal gestor;
    private long tInicio;
    private long tFinal;
    private long tamOriginal;
    
  

    /**
     *  Crea un compresor y lo prepara para recibir una tabla de n signos
     */
    public Compresor()
    {
       cantSignos = 0;
       errorCode = "Compresor preparado";

    }

    /**
     *  Crea un compresor y lo prepara para recibir una tabla de n signos
     */
    public Compresor(GestorVentanaPrincipal gestor)
    {
       cantSignos = 0;
       errorCode = "Compresor preparado";
       this.gestor = gestor;
    }

    /**
     *  Devuelve el estado del compresor luego de una operaci�n dada
     *  @return un String con el estado del compresor
     */
    public String getErrorCorde() 
    { 
        return errorCode; 
    }
    public void comprimirRecursivo(File f)
    {
        tInicio = System.currentTimeMillis();
        gestor.setEstado(GestorVentanaPrincipal.Estado.comprimir);
        tamanio=Long.valueOf("0");
        cont = 0;
    
        try
        {
            //abro los archivos..

        File arch = f;
        nombre = arch.getName();
          
        //obtengo el nombre del archivo, sin la extension
        if (!arch.isDirectory()) 
        {

            nombre = arch.getName().substring( 0, nombre.indexOf(".") );
            gestor.setTxtArchivo(nombre);


        }       

            ruta=arch.getParent();
            //gestor.setTxtArchivo(arch.getAbsolutePath());
            File f2 = new File(arch.getParent()+"\\"+ nombre + ".clincker");
            comprimido = new RandomAccessFile(f2, "rw");
            long pos, newpos;
            pos=newpos=0;
            this.getTamanio(arch);
            comprimido.writeLong(tamanio);
            pos = comprimido.getFilePointer(); //No haria falta
            compress(pos,newpos, arch);

            comprimido.close();
            gestor.setEstado(GestorVentanaPrincipal.Estado.iniciado);
//            tFinal = new Date() - tInicio;

        } catch (IOException ex) {
            Logger.getLogger(Compresor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *  Comprime un archivo usando un Arbol de Huffman para determinar el c�digo de bit de cada signo. Genera un 
     *  archivo comprimido con el mismo nombre que el original, pero con extensi�n .clincker
     *  @param fileName el nombre del archivo a comprimir
     */
     public void comprimir (File arch)
    {

        try
        {
            // abro los archivos
            File f1 = arch;
            fuente=new RandomAccessFile (f1, "r");
            //muestro el archivo que se está comprimiendo
            gestor.setTxtArchivo(arch.getAbsolutePath());
            gestor.setTxEstado("Leyendo Archivo..");
            gestor.actualizarJPBArchivos(0, 100);

            // cuento cu�ntas veces aparece cada byte en el archivo
            int i;
            byte car;
            int c[] = new int[256];  // un vector de contadores
            for(i=0; i<256; i++) { c[i] = 0; }
            // contamos los signos...
            cantSignos = 0;
            int prog = 0;
            int tot= (int)fuente.length();
            while(fuente.getFilePointer() < fuente.length())
            {
                 car = fuente.readByte();            // leo un byte del archivo...
                 short sc = (short) (car & 0x00FF);  // ... lo convierto a short para evitar problemas de desborde
                 c[ sc ]++;   // cuento ese byte!!!
                 if (c[sc]<2) {
                   cantSignos++;
                  }
                  prog++;
                  gestor.actualizarJPBArchivos(prog, tot);
            }
            gestor.setTxEstado("Creando Arbol");

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
            gestor.setTxEstado("Codificando Arbol");
            // armamos el �rbol y obtenenos el c�digo Huffman de cada signo
            ht.codificar();

            // cantidad de bytes del archivo fuente
            long tArch = fuente.length();
            int contArch = 0;

            String rutafinal=arch.getParentFile().getCanonicalPath().substring(ruta.length(),arch.getParentFile().getCanonicalPath().length());

            // guardo en el archivo comprimido informaci�n para el descompresor...
            // ...empiezo guardando el nombre y la extensi�n del original...
            comprimido.writeUTF(rutafinal+"\\"+arch.getName());

            // ... luego guardo la longitud en bytes del archivo original...
            comprimido.writeLong(tArch);
            // ... la cantidad de s�mbolos (o sea, la cantidad de hojas del �rbol)...
            comprimido.writeInt(cantSignos);

            // ... ahora la tabla de s�mbolos tal como est� en el arbol...
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = ht.getSigno(i);
                comprimido.writeByte(signo);
            }

            gestor.setTxEstado("Guardando Arbol en archivo..");
            // ... ahora el vector que representa al �rbol...
            NodoHuffman a[] = ht.getArbol();
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del �rbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, guardar todos sus datos...
                comprimido.writeInt( a[i].getFrecuencia() );
                comprimido.writeInt( a[i].getPadre() );
                comprimido.writeBoolean( a[i].isLeft() );
                if (i>=cantSignos) {
                    comprimido.writeInt( a[i].getIzquierdo());
                    comprimido.writeInt( a[i].getDerecho());
                }


            }
            gestor.setTxEstado("Grabando compresion..");
            // comienza fase de compresi�n (por fin...)
            short mascara = 0x0080;  // el valor 0000 0000 1000 0000
            short salida  = 0x0000;  // el valor 0000 0000 0000 0000
            int bit = 0;             // en qu� bit vamos?
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
                        //se llen� el byte de salida...
                        comprimido.writeByte( (byte)salida ); // graba el menos significativo!!!
                        bit = 0;
                        mascara = 0x0080;
                        salida  = 0x0000;
                    }
                }
                //Cuento cada Bit y actualizo informacion de la interfaz
                cont++;
                contArch++;
                gestor.actualizarJPBProceso(cont, tamanio);
                gestor.actualizarJPBArchivos(contArch, tArch);
                this.tFinal = System.currentTimeMillis();
                long t = (System.currentTimeMillis() - tInicio) / 100;
                
                gestor.calcularTiempo(cont, tamanio, t);
                
                //Tiempo Estimado

            }


            if (bit != 0)
            {
                // grabar el �ltimo byte que estaba incompleto
                comprimido.writeByte( (byte)salida );  // graba el menos significativo!!!
            }
            if ((long)contArch == fuente.length())
            {
                //TERMINO!!
                gestor.setTxEstado("Archivo comprimido correctamente..");
                gestor.setTxtTiempoRestante("0.0s");
                //gestor.estadoInicial
            }
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
            if( ext.compareTo("cmp") != 0 ) { throw new Exception ("El archivo no tiene la extensi�n cmp..."); }
            
            // abro el archivo comprimido...
            File f1 = new File( fileName );
            comprimido = new RandomAccessFile(f1, "r");    
            
            // ... y recupero el nombre del archivo original
            String original = comprimido.readUTF();
            
            // creo el archivo con el nombre del original
            File f2 = new File(original);
            if( f2.exists() ) { f2.delete(); }
            nuevo = new RandomAccessFile(f2, "rw");
            
            // y ahora, recupero todos los datos que el compresor dej� adelante...
            
            // ... empezando por el tama�o del archivo original...
            long tArch = comprimido.readLong();
            
            // ... la cantidad de signos de la tabla (o sea, la cantidad de hojas)...
            cantSignos = comprimido.readInt();
            
            // ...creo de nuevo el �rbol en memoria...
            ht = new ArbolHuffman(cantSignos);
            
            // ... y recupero uno a uno los signos originales, guard�ndolos de nuevo en el �rbol...
            int i;
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = comprimido.readByte();
                ht.setSigno(signo, i);
            }
            
            // ...ahora le toca al vector del �rbol...
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del �rbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, recuperar todos sus datos y volver a armar el �rbol...
                int f  = comprimido.readInt();           // frecuencia
                int padre = comprimido.readInt();        // padre
                boolean left = comprimido.readBoolean(); // es izquierdo?
                int hi = comprimido.readInt();           // hijo izquierdo
                int hd = comprimido.readInt();           // hijo derecho
                NodoHuffman nh = new NodoHuffman( f, padre, left, hi, hd );
                ht.setNodo( nh, i );
            }
            
            // y habiendo llegado ac�, el descompresor vuelve a pedir que se creen los c�digos de Huffman
            ht.obtenerCodigos();
                       
            // de ac� saco el vector que representa al �rbol y el �ndice de la raiz...
            NodoHuffman []v2 = ht.getArbol();
            int raiz =  v2.length - 1;  // la raiz est� en la �ltima casilla del vector!!!!
            
            // comienza la fase de descompresi�n
            short aux;                     // auxiliar para desenmascarar
            short mascara;
            int bit, nodo = raiz;          // comenzamos desde la raiz y vamos bajando
            long cantBytes = 0;            // cu�ntos bytes llevo grabados??
            
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
                        // el bit en la posici�n "bit" era un uno...
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
                        // llegamos a una hoja... grabar el signo que est� en ella
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

      
        private void getTamanio (File f)
        {
            if (f.isDirectory())
            {
                File aux [] = f.listFiles();
                for (int i =0 ; i < aux.length; i++)
                {
                    getTamanio(aux[i]);
                    
                }
            }
            else
                tamanio += f.length();
        }
        
        private void compress(long pos, long newpos,File arch){   //No haria falta pasar pos
        try {

            if (arch.isDirectory()) {

                File aux[]=arch.listFiles();
                for (int i = 0; i < aux.length; i++) {

                    compress(pos,newpos,aux[i]);
                }
            }else{
                pos=comprimido.getFilePointer();
                comprimido.writeLong(0);
                this.comprimir(arch);
                newpos=comprimido.getFilePointer();
                comprimido.seek(pos);
                comprimido.writeLong(newpos);
                comprimido.seek(newpos);

            }
        } catch (Exception e) {
        }
        }
         public void descomprimirRecursivo(File arch){
        
        try {

            // abro el archivo comprimido...
            File f1 = arch;
            tamanio=arch.length();
            cont = 0;
            comprimido = new RandomAccessFile(f1, "r");
            tamOriginal= comprimido.readLong(); //Nuevo
            //donde esta el file pointer?
            while(comprimido.getFilePointer() <comprimido.length()){
                long tam=comprimido.readLong();                
                this.descomprimir(tam,arch);
            }


            comprimido.close();

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());
        }

    }

         private boolean descomprimir(long tam,File arch)
    {
        try
        {
            // ... y recupero el nombre del archivo original
            String original = comprimido.readUTF();
            try {
                File dir = new File("\\"+arch.getParent()+"\\"+original.substring(0,original.lastIndexOf("\\")));
                dir.mkdirs();
               
            } catch (Exception e) {
            }

            File f2 = new File("\\"+arch.getParent()+"\\"+original);

            //Actualizo informacion
            gestor.setTxtArchivo(arch.getParent() + original);
            gestor.setTxEstado("Leyendo informacion de estructura de archivo");
            gestor.actualizarJPBArchivos(0,100);
            
            if( f2.exists() ){
                int op=JOptionPane.showConfirmDialog(null,"El archivo "+original+" existe, ¿desea sobreescribirlo?");

                if (op==0) {
                    f2.delete();
                }else{
                    //no grabo el nuevo archivo me voy al prox
                    comprimido.seek(tam);
                    cont=(int)tam;                    
                    return false;
                }

            }
            nuevo = new RandomAccessFile(f2, "rw");

            // y ahora, recupero todos los datos que el compresor dej� adelante...

            // ... empezando por el tama�o del archivo original...
            long tArch = comprimido.readLong();
            int contArch = 0;

            // ... la cantidad de signos de la tabla (o sea, la cantidad de hojas)...
            cantSignos = comprimido.readInt();

            // ...creo de nuevo el �rbol en memoria...
            ht = new ArbolHuffman(cantSignos);

            // ... y recupero uno a uno los signos originales, guard�ndolos de nuevo en el �rbol...
            int i;
            for(i = 0; i < cantSignos; i++)
            {
                byte signo = comprimido.readByte();
                ht.setSigno(signo, i);
            }

            gestor.setTxEstado("Leyendo Arbol...");

            // ...ahora le toca al vector del �rbol...
            int n = cantSignos * 2 - 1;  // cantidad total de nodos del �rbol
            for(i = 0; i < n; i++)
            {
                // ...por cada nodo, recuperar todos sus datos y volver a armar el �rbol...
                int f  = comprimido.readInt();           // frecuencia
                int padre = comprimido.readInt();        // padre
                boolean left = comprimido.readBoolean(); // es izquierdo?
                int hi=-1;
                int hd=-1;
                if (i>=cantSignos) {
                    hi = comprimido.readInt();           // hijo izquierdo
                    hd = comprimido.readInt();           // hijo derecho
                }

                NodoHuffman nh = new NodoHuffman( f, padre, left, hi, hd );
                ht.setNodo( nh, i );
            }

            // y habiendo llegado ac�, el descompresor vuelve a pedir que se creen los c�digos de Huffman
            ht.obtenerCodigos();

            // de ac� saco el vector que representa al �rbol y el �ndice de la raiz...
            NodoHuffman []v2 = ht.getArbol();
            int raiz =  v2.length - 1;  // la raiz est� en la �ltima casilla del vector!!!!

            // comienza la fase de descompresi�n
            short aux;                     // auxiliar para desenmascarar
            short mascara;
            int bit, nodo = raiz;          // comenzamos desde la raiz y vamos bajando
            long cantBytes = 0;            // cu�ntos bytes llevo grabados??

            gestor.setTxEstado("Descomprimiendo..");
            // leo byte por byte el archivo comprimido
            while(comprimido.getFilePointer() < tam)
            {
                byte  car = comprimido.readByte();
                short sCar = (short) (car & 0x00FF);  // guardo el byte en un short, pero con todo el primer byte en cero
                mascara = 0x0080;
                for(bit = 0; bit < 8 && cantBytes != tArch; bit++)
                {
                    aux = (short)(sCar & mascara);
                    if(aux == mascara)
                    {
                        // el bit en la posici�n "bit" era un uno...
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
                        // llegamos a una hoja... grabar el signo que est� en ella
                        byte sal = ht.getSigno(nodo);
                        nuevo.writeByte(sal);
                        cantBytes++;

                        //contadores para calcular progreso
                        contArch++;
                        cont++;

                        // volver a la raiz
                        nodo = raiz;
                    }
                   
                }

                try
                {
                gestor.actualizarJPBProceso(cont, tamOriginal);
                gestor.actualizarJPBArchivos(contArch, tArch);
                long t = (System.nanoTime() - tInicio) / 1000;
                gestor.calcularTiempo(cont, tamOriginal, t);
                }
                catch (Exception e)
                {
                    System.out.println("Error: " + e.getMessage());
                }
                
            }
            
            
            if (nuevo.length() == tArch)
            {
                gestor.setTxEstado("Archivo Descomprimido exitosamente");
                gestor.setEstadoInicial();
            }
            else
            {
                gestor.setTxEstado("Error de tamaño de archivo descomprimido");
            }


            nuevo.close();

        }

        catch(IOException e)
        {
            System.out.println("Error de IO: " + e.getMessage());
            return false;
        }

        

        return true;
    }
}

