/** 
 *  Incluye un main para testear la clase Archivo(archivos con la descripción de su registro en la cabecera)
 *  @author Ing. Valerio Frittelli
 *  @version Octubre 2004
 */
import java.io.*;
import java.util.*;
public class Principal
{
       private static Archivo archivo;
       private static Campo campo;
       
       public static void leer()
       {
          String nombre;
          int tipo, cantidad = 0;
          boolean esclave;
          int resp;

          System.out.print("Identificador del campo: ");
          nombre = Consola.readLine(); 
          System.out.print("Tipo (0: integer - 1: real - 2: cadena): ");
          tipo = Consola.readInt();
          if (tipo == 2) 
          { 
            // es tipo cadena... pedir longitud
            System.out.print("Longitud: ");
            cantidad = Consola.readInt();
          }
          System.out.print("El campo es clave del registro? (1: Si - 0: No): ");
          resp = Consola.readInt();
          esclave = (resp == 1)? true : false;
          
          campo = new Campo(nombre, tipo, cantidad, esclave);
       }
    
       public static void mostrarCampo ()
       {
          String nombre = campo.getNombre();
          int tipo = campo.getTipo(), cantidad = campo.getCantidad();
          boolean esclave = campo.isClave();
          String tip;
          
          if (tipo == 0) tip = "Entero";
          else if (tipo == 1) tip = "Real";
               else tip = "Cadena";
               
          System.out.println("Identificador: " + nombre);
          System.out.println("Tipo:          " + tip);
          if (cantidad != 0) System.out.println("Longitud:     " + cantidad);
          System.out.println("Clave?:        " + esclave);
       }

       public static void cargarMetaDatos () throws IOException
       {
            int seguir;
            Vector c = new Vector();
            archivo.crear();
            do 
            {
                  System.out.print("Creacion de la Tabla: " + archivo.getNombreGeneral() + "\n");
                  System.out.print("Ingrese la informacion de los campos del registro:\n\n");
                  leer();
                  // agrego el campo a un vector...
                  c.add(campo);
                  System.out.print("Otro campo?  (1: Si / 0: No): ");
                  seguir = Consola.readInt();
            }
            while (seguir == 1);

            // grabamos el vector en el archivo
            archivo.grabarMetaDato(c);
            archivo.cerrar();
            System.out.print("\n\nCreacion de estructura de tabla terminada... Archivo de datos creado (vacío)...");
       }

       /**
        *  Carga datos en el archivo, tomando la información de campos del archivo de metadatos
        */
       public static void cargarDatos ()throws IOException
       {
            int i, resp, seguir, cont;
            archivo.abrir("rw");

            // el archivo debe estar abierto para poder invocar a este método... de lo contrario el vector no existe...
            Campo []fields = archivo.getCampos();   // crea un vector con los datos de todos los campos
            int cantcampos = fields.length;         // cantidad de campos!!!
            cont = 0;
    
            do 
            {
                 System.out.print("Carga de la Tabla: " + archivo.getNombreGeneral() + "\n");
                 System.out.print("Valores de los campos del registro número " + cont + ":\n");
                 for (i=0; i<cantcampos; i++)
                 {
                    System.out.print("\nCampo " + fields[i].getNombre() + ": ");
                    switch(fields[i].getTipo())
                    {
                       case 0: // int
                           int fieldInt = Consola.readInt();
                           archivo.grabarInt(fieldInt);
                           break;
        
                       case 1: // float
                           float fieldFloat = (float)Consola.readDouble();
                           archivo.grabarFloat(fieldFloat);
                           break;
        
                       case 2: // cadena
                           String fieldString = Consola.readLine();
                           archivo.grabarString(fieldString, fields[i].getCantidad());
                           break;
                    }
                 }
                 cont ++;
                 System.out.print("Desea cargar otro registro? (1: Si / 0: No): ");
                 seguir = Consola.readInt();
            }
            while (seguir == 1);
            archivo.cerrar();
       }
  
       public static void mostrarDatos ()throws IOException
       {
            int i;
            int resp, seguir;
            int cont;

            archivo.abrir("r");
            Campo []fields = archivo.getCampos();
            int cantcampos = fields.length;
            
            

            System.out.print("Contenido de la tabla\n\n");
            while (!archivo.eof())
            {
                for (i=0; i<cantcampos; i++)
                {
                   System.out.print("\nCampo " + fields[i].getNombre() + ": ");
                   switch(fields[i].getTipo())
                   {
                      case 0: // int
                          int fieldInt = archivo.leerInt();
                          System.out.print(fieldInt);
                          break;
            
                      case 1: // float
                          float fieldFloat = archivo.leerFloat();
                          System.out.print(fieldFloat);
                          break;
            
                     case 2: // cadena
                         String fieldString = archivo.leerString(fields[i].getCantidad());
                         System.out.print(fieldString);
                         break; 
                       }
                }
            }
            archivo.cerrar();
       }
  
  
       public static void main (String[] args)throws IOException
       {
            archivo = new Archivo("Alumnos");
            int op;
        
            do
            {
               System.out.print("\n\nPrograma para manipulacion de una tabla, con metadatos\n");
               System.out.print("\n1. Crear tabla");
               System.out.print("\n2. Cargar tabla");
               System.out.print("\n3. Mostrar tabla");
               System.out.print("\n4. Salir");
               System.out.print("\n\n\n\t\tElija: ");
               op = Consola.readInt();
               switch (op)
               {
                   case 1:  cargarMetaDatos();
                            break;
                       
                   case 2:  cargarDatos();
                            break;
        
                   case 3:  mostrarDatos();
                            break;
        
                   case 4:;
               }
            }
            while (op != 4);
            System.exit(0);
       }
}
