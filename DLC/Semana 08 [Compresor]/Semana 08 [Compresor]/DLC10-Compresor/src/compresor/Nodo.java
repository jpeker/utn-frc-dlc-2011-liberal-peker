package compresor;

/**
 *  Clase para representar un nodo generico para una lista generica.
 *  @author Ing. Valerio Frittelli
 *  @version Abril de 2004
 */
public class Nodo
{
   private Comparable info;
   private Nodo next;
   
   /**
    *  Constructor por defecto. 
    */
   public Nodo ( )
   {
   }
   
   /**
    *  Crea un nodo incializando todos los atributos en base a parametros
    */
   public Nodo (Comparable x, Nodo p)
   {
     info = x;
     next = p;
   }
   
   /**
    *  Accede a la direccion del sucesor
    *  @return la direccion del nodo sucesor
    */
   public Nodo getNext()
   {
     return next;
   }
   
   /**
    * Cambia la direccion del sucesor
    * @param p direccion del nuevo sucesor
    */
   public void setNext(Nodo p)
   {
     next = p;
   }
   
   /**
    *  Accede al valor del info
    *  @return el valor del info
    */
   public Comparable getInfo()
   {
     return info;
   }
   
   /**
    * Cambia el valor del info
    * @param p nuevo valor del info
    */
   public void setInfo(Comparable p)
   {
     info = p;
   }

   /**
    * Convierte el contenido del nodo en String
    * @return el contenido del nodo convertido en String
    */
   @Override
   public String toString()
   {
     return info.toString();   
   }
}

