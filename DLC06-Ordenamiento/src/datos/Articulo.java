package datos;

/**
 * Representa un artículo a la venta en un comercio cualquiera, que podrá ser usado dentro de un
 * Register para grabar en un RegisterFile. Como Grabable extiende a Comparable, la clase Articulo debe 
 * dar una implementación de compareTo().
 * 
 * @author Ing. Valerio Frittelli
 * @version Marzo de 2008
 */

import java.io.*;
import javax.swing.*;
import persistencia.*;

public class Articulo implements Grabable
{
       private int     codigo;       // 4 bytes en disco
       private String  descripcion;  // una cadena, queremos sea de 50 caracteres máximo = 100 bytes en disco
       
       /**
        * Constructor por defecto. Los atributos quedan con valores por default.
        */
       public Articulo ()
       {
       }
       
       /**
        * Inicializa cada atributo de acuerdo a los parámetros.
        */
       public Articulo (int cod, String nom)
       {
           codigo   = cod;
           descripcion = nom;
       }
       
       /**
        * Accede al valor del codigo.
        * @return el valor del atributo codigo.
        */
       public int getCodigo()
       {
          return codigo;
       }

       /**
        * Accede al valor de la descripción.
        * @return el valor del atributo descripción.
        */
       public String getDescripcion()
       {
          return descripcion;
       }
       
       /**
        * Cambia el valor del codigo.
        * @param c el nuevo valor del atributo codigo.
        */
       public void setCodigo (int c)
       {
          codigo = c;
       }

       /**
        * Cambia el valor de la descripción.
        * @param nom el nuevo valor del atributo descripción.
        */
       public void setDescripcion (String nom)
       {
          descripcion = nom;
       }

       /**
        *  Calcula el tamaño en bytes del objeto, tal como será grabado. Pedido por Grabable.
        *  @return el tamaño en bytes del objeto.
        */
       public int sizeOf()
       {
             int tam = 104;  // 4 + 100... Alguna duda?
             return tam;
       }
    
       /**
        *  Indica cómo grabar un objeto. Pedido por Grabable.
        *  @param el archivo donde será grabado el objeto.
        */
       public void grabar (RandomAccessFile a)
       {
           try
           {
                a.writeInt(codigo);
                RegisterFile.writeString (a, descripcion, 50);
           }
           catch(IOException e)
           {
                JOptionPane.showMessageDialog(null, "Error al grabar el registro: " + e.getMessage());
                System.exit(1);
           }
       }

       /**
        *  Indica cómo leer un objeto. Pedido por Grabable.
        *  @param a el archivo donde se hará la lectura.
        */
       public void leer (RandomAccessFile a)
       {
           try
           {
                codigo  = a.readInt();
                descripcion  = RegisterFile.readString(a, 50).trim();
           }
           catch(IOException e)
           {
                JOptionPane.showMessageDialog(null, "Error al leer el registro: " + e.getMessage());
                System.exit(1);
           }
       }
        
       /**
        * Compara dos objetos de la clase Articulo. 
        * @return 0 si los objetos eran iguales, 1 si el primero era mayor, -1 en caso contrario.
        * @param x el objeto contra el cual se compara.
        */
       public int compareTo (Object x)
       {
         Articulo a = (Articulo) x;
         return this.codigo - a.codigo;
       } 


       /**
        *  Redefinición del heredado desde Object. Considera que dos Articulos son iguales si sus
        *  códigos lo son.
        *  @param x el objeto contra el cual se compara.
        *  @return true si los códigos son iguales, false en caso contrario.
        */
       @Override
       public boolean equals (Object x)
       {
           if( x == null || !( x instanceof Articulo ) ) return false;
           
           Articulo a = (Articulo)x;
           return (codigo == a.codigo);
       }
       
       /**
        *  Redefinición del heredado desde Object. La convención es si equals() dice que dos
        *  objetos son iguales, entonces hashCode() debería retornar el mismo valor para ambos.
        *  @return el hashCode del Articulo. Se eligió el código para ese valor.
        */
       @Override
       public int hashCode ()
       {
           return codigo;
       }

       /**
        *  Redefinición del heredado desde Object.
        *  @return una cadena representando el contenido del objeto.
        */
       @Override
       public String toString()
       {
           return "\nCodigo: " + codigo + "\tDescripcion: " + descripcion;     
       }
}
