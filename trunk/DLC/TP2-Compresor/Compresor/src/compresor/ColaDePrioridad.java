/*
 * ColaDePrioridad.java
 *
 */

package compresor;
/**
 *  Una lista genrica
 *  @author  Ing. Valerio Frittelli
 *  @version Abril de 2004
 */
public class ColaDePrioridad
{
      private Nodo frente;
      
      /** 
       * Constructor por defecto
       */
      public ColaDePrioridad ()
      {
        frente = null;
      }
      
      /**
       *  Acceso a la direccin del primer Nodo
       *  @return direccin del primer Nodo
       */
      public Nodo getFrente()
      {
         return frente;   
      }
      
      /**
       *  Modifica la direccin del primer Nodo
       *  @param p nueva direccin del primer Nodo
       */
      public void setFrente(Nodo p)
      {
         frente = p;   
      }
            
      /**
       *  Inserta un nodo en forma ordenada
       *  @param x el objeto a almacenar en el nuevo nodo
       */
      public void insertar(Comparable x)
      {
           // insertar ordenado
           if (x != null)
           {
        	  if (frente != null && x.getClass() != frente.getInfo().getClass()) return;
        	  Nodo nue = new Nodo (x, null);
    		  Nodo p = frente, q = null;
    		  while(p != null && x.compareTo(p.getInfo()) > 0)
    		  {
                  q = p;
    			  p = p.getNext();
    		  }
    		  nue.setNext(p);
    		  if(q != null)
    		  {
    			  q.setNext(nue);
    		  }
    		  else
    		  {
    			  frente = nue;
    		  }
           }
      } 
      
      /**
       *  Elimina el primer nodo
       *  @return una referencia al info del nodo eliminado, o null si la lista estaba vaca
       */
      public Comparable borrar() 
      {
            Comparable p = null;
            if(frente != null)
            {
               p = frente.getInfo();
               frente = frente.getNext();
            }  
            return p;
      } 
      
      /**
       *  Busca un nodo en la lista que contenga al objeto x 
       *  @param x el objeto a buscar
       *  @return una referencia al nodo que contiene a x, si exista tal nodo, o null si no exista
       */
      public Nodo buscar (Comparable x)
      {
        Nodo p = frente;
        while(p!=null)
        {
           Comparable y = p.getInfo();
           if(x.getClass() == y.getClass() && x.compareTo(y)==0) 
           {
              break; 
           }   
           p = p.getNext();   
        }
        return p;
      }

      /**
       *  Redefine el mtodo toString
       *  @return el contenido de la lista convertido a String
       */
    @Override
      public String toString()
      {
         Nodo p = frente;
         String res = "Contenido: ";
         while(p != null)
         {
            res = res + p.toString();
            p = p.getNext();
         }
         return res;
      }
}
