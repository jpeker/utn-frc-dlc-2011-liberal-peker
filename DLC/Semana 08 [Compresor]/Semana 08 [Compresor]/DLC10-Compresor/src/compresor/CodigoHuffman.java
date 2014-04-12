package compresor;

/**
 * Representa el c�digo Huffman de un signo del alfabeto de entrada.
 * 
 * @author Ing. Valerio Frittelli
 * @version Octubre de 2004
 */
public class CodigoHuffman
{
    public static final int MAXBITS = 175;    // m�xima cantidad de bits que aceptamos para un c�digo
    private byte bits[];                      // ac� guardamos los bits, de atr�s hacia adelante
    private int startPos;                     // indica d�nde comienza la cadena de bits dentro del vector 

    /**
     *  Constructor por defecto. Ajusta startPos en MAXBITS y crea el vector de tama�o MAXBITS
     */
    public CodigoHuffman() 
    { 
        startPos = MAXBITS; 
        bits = new byte[MAXBITS]; 
    }
    
    /**
     *  Constructor. Inicializa el objeto con los datos tomados de otro, que viene como par�metro
     */
    public CodigoHuffman (CodigoHuffman c)
    {
        for(int i = c.startPos; i < MAXBITS; i++)
        {
            bits[i] = c.bits[i];
        }
        startPos = c.startPos;
    }
    
    /**
     *  Acceso al valor de startPos
     *  @return el valor de startPos
     */
    public int getStartPos() 
    {
        return startPos;
    }
    
    /**
     * Ajusta el valor de un bit espec�fico en la posici�n startPos -1
     * @param bit el bit a almacenar en la posici�n que corresponde en el momento de la invocaci�n (startPos - 1)
     */
    public void setBit(byte bit)
    {
        if (startPos == 0) 
        {
            // Esta salida por consola est� para la fase de prueba... no debe ir si el proyecto ya fue testeado...
            //System.out.println("C�digo muy largo");
            System.exit(1);
        }
        startPos--;
        bits[startPos] = bit;
    }
    
    /**
     *  Accede al vector que contiene al c�digo Huffman del signo
     *  @return una referencia al vector que contiene al c�digo
     */
    public byte[] getCodigo()
    {
        return bits;
    }
    
    /**
     *  Obtiene una cadena con la respesentaci�n del objeto
     *  @return una cadena con todos los bits presentes en el vector bits
     */
    public String toString()
    {
       StringBuffer res = new StringBuffer("");
       for(int i = startPos; i < MAXBITS; i++)
       {
           res.append(bits[i]);
       }
       return res.toString();
    }
}
