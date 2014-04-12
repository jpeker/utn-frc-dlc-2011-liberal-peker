package compresor;

/**
 * Representa un �rbol de Huffman, implementado sobre un arreglo.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2004
 */
public class ArbolHuffman
{
    public static final int MAXSYMBS = 256;               // todos los valores ASCII...
    public static final int MAXNODES = 2 * MAXSYMBS - 1;  // cantidad total de nodos del �rbol si usamos todos los ASCII    
    private CodigoHuffman codigos[];                      // ac� guardamos los c�digos Huffman de cada signo
    private NodoHuffman   arbol  [];                      // este es el arbol: los primeros "tam" casilleros son las hojas (signos) 
    private ColaDePrioridad fila;
    private byte signos [];                               // ac� guardamos los valores de los signos
    private int tam;                                      // cantidad de signos (hojas) del �rbol
 
    /**
     *  Constructor por defecto. Crea un �rbol para 256 signos (todo el espectro ASCII)
     */ 
    public ArbolHuffman ()
    {
        this(MAXSYMBS); 
    }
 
    /**
     *  Constructor. Crea un �rbol para n signos
     */ 
    public ArbolHuffman (int n)
    {
        int i, nodos = 2*n - 1;    // cantidad de nodos
        tam = n;                   // cantidad de signos (o sea, hojas) a emplear

        codigos = new CodigoHuffman[tam];
        for(i=0; i < tam; i++)
        {
           codigos[i] = new CodigoHuffman();
        }
        
        arbol   = new NodoHuffman[nodos];
        for(i=0; i < nodos; i++)
        {
           arbol[i] = new NodoHuffman();
        }

        signos  = new byte[tam];        
        for(i=0; i < tam; i++)
        {
           signos[i] = 0;
        }
        
        fila = new ColaDePrioridad();
    }

    /**
     *  Devuelve la cantidad de signos que est� manejando el �rbol
     *  @return la cantidad de signos del �rbol (tam)
     */
    public int length() 
    { 
        return tam; 
    }

    /**
     *  Agrega un nodo al �rbol
     *  @param x el valor del signo a agregar
     *  @param f la frecuencia del signo
     *  @param i la posici�n del signo en el arreglo que representa al �rbol
     */
    public void setNodo(byte x, int f, int i)
    {
         setSigno(x, i);
         setFrecuencia(f, i);
         Frecuencia p = new Frecuencia(f, i);
         fila.insertar(p);   
    }
    
    /**
     * Agrega un nodo al �rbol, tomando todos los datos de un objeto NodoHuffman ya creado
     * param nh el objeto a agregar en el �rbol
     * @param i el �ndice de la casilla donde se agrega el nuevo nodo
     */
    public void setNodo(NodoHuffman nh, int i)
    {
        if(nh != null){ arbol[i] = nh; }
    }

    /**
     *  Accede al valor de un signo espec�fico
     *  @param i el �ndice del signo a acceder
     *  @return el valor del signo en la posici�n i
     */
    public byte getSigno(int i)
    { 
        return signos[i];
    }
    
    /**
     *  Cambia el valor del signo en la posici�n i
     *  @param x el nuevo valor del signo
     *  @param i el �ndice del signo a cambiar
     */
    public void setSigno(byte x, int i)
    { 
        signos[i] = x;
    }

    /**
     *  Obtiene la frecuencia del signo en la posici�n i
     *  @param i indice del signo cuya frecuencia se pide
     *  @return el valor de la frecuencia de ese signo
     */
    public int  getFrecuencia(int i)
    { 
        return arbol[i].getFrecuencia(); 
    }
    
    /**
     *  Cambia la frecuencia de un signo
     *  @param f la nueva frecuencia
     *  @param i el indice del signo cuya frecuencia se quiere cambiar
     */
    public void setFrecuencia(int f, int i)
    { 
        arbol[i].setFrecuencia(f);
    }

    /**
     *  Obtiene el c�digo Huffman de un signo
     *  @param i el indice del signo cuyo c�digo se quiere acceder
     *  @return una referencia al objeto que contiene al c�digo
     */
    public CodigoHuffman getCodigo(int i)
    { 
        return codigos[i]; 
    }
    
    /**
     *  Cambia el c�digo Huffman de un signo
     *  @param c una referencia al objeto con el nuevo c�digo
     *  @param i el indice del signo cuyo c�digo se desea cambiar
     */
    public void setCodigo(CodigoHuffman c, int i)
    { 
        codigos[i] = c;
    }

    /**
     *  Obtiene el c�digo Huffman de un signo
     *  @param x el signo cuyo c�digo se quiere acceder
     *  @return una referencia al objeto que contiene al c�digo
     */
    public CodigoHuffman getCodigo(byte x)
    {
        int i = buscar (x);
        return getCodigo(i);
    }
    
    /**
     *  Cambia el c�digo Huffman de un signo
     *  @param c una referencia al objeto con el nuevo c�digo
     *  @param x el signo cuyo c�digo se desea cambiar
     */
    public void setCodigo(CodigoHuffman c, byte x)
    {
        int i = buscar(x);
        codigos[i] = c;  
    }

    /**
     *  Devuelve el arreglo que representa al Arbol de Huffman
     *  @return una referencia al arreglo que contiene al Arbol de Huffman
     */
    public NodoHuffman [] getArbol()
    {
        return arbol;   
    }


    /**
     *  Arma el �rbol con todos los signos, y obtiene los c�digos Huffman de cada uno
     */
    public void codificar()
    {
        armarArbol();
        obtenerCodigos();
    }

    public void armarArbol ()
    {
        int p, f, iraiz;
        Frecuencia p1, p2;
        for(p=tam; p < 2*tam-1; p++)
        {
            /*
                p apunta al siguiente nodo disponible en el �rbol. Obtener los objetos
                p1 y p2 con menor frecuencia de la cola de prioridad
            */
            p1 = (Frecuencia)fila.borrar();
            p2 = (Frecuencia)fila.borrar();
    
            // ponemos ambos como hijos del nodo p
            int ip1 = p1.getIndice();
            int ip2 = p2.getIndice();
            arbol[ip1].setPadre(p);
            arbol[ip1].setLeft(true);
            arbol[ip2].setPadre(p);
            arbol[ip2].setLeft(false);
            f = arbol[ip1].getFrecuencia() + arbol[ip2].getFrecuencia();
            arbol[p].setFrecuencia(f);
            arbol[p].setIzquierdo(ip1);
            arbol[p].setDerecho(ip2);
            Frecuencia nf = new Frecuencia (f, p);
            fila.insertar(nf);
        }
    }
    
    public void obtenerCodigos()
    {
        int i, p, iraiz = arbol.length - 1;
    
        // ahora extraemos los c�digos del �rbol y los guardamos en el vector de c�digos
        for(i=0; i<tam; i++)
        {
            p = i;
            while(p != iraiz)
            {
                if(arbol[p].isLeft())
                {
                    codigos[i].setBit((byte)0);
                }
                else
                {
                    codigos[i].setBit((byte)1);
                }
                p = arbol[p].getPadre();
            }
        }
    }

    
    /**
     *  Obtiene una tabla con los signos y sus c�digos Huffman, en forma de String
     *  @return un string con la tabla de signos y sus c�digos
     */
    public String toString()
    {
        StringBuffer res = new StringBuffer("Signos Codificados");
 
        for(int i=0; i<tam; i++)
        {
            res.append("\nSigno: " + (char)signos[i] + "\tCodigo: " + codigos[i].toString());
        }
  
        return res.toString();
    }


    private int buscar(byte x)
    {
       int i;
       for(i = 0; i<tam; i++)
       {
          if(x==signos[i]) return i;
       }
       return -1;
    }
}
