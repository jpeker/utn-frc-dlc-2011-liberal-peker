package interfaz;

/**
 * Clase para contener al método main.
 * 
 * @author Ing. Valerio Frittelli
 * @version Marzo de 2008
 */
import persistencia.*;
import datos.*;
public class Principal
{        
     private static DirectAccessFile m1;
     private static DirectAccessFile m2;
       
     private static Alumno   alu;
     private static Articulo art; 

     /**
      * Muestra el contenido de un archivo (incluidos los registros marcados como borrados) en consola estandar
      */
     public static void mostrarTodo ( RegisterFile m )
     {
          RegisterFileIterator rfi = m.createIterator();
          while ( rfi.hasNext() )
          {
               Grabable x = rfi.current();
               System.out.print( x.toString() );
               rfi.next();
          }
     }  

    /**
     * Carga un legajo por teclado 
     */
    public static void cargarLegajo( )
    {
          System.out.print("Ingrese el Legajo: ");
          int legajo = Consola.readInt();
          alu.setLegajo(legajo);
    }
       
    /**
      * Carga un registro de Alumno por teclado 
      */
    public static void leerAlumno ( )
    { 
          cargarLegajo();
          System.out.print("Ingrese el Nombre: ");
          String nombre = Consola.readLine();
          alu.setNombre(nombre);
          System.out.print("Ingrese el Promedio: ");
          float promedio = (float)Consola.readDouble();
          alu.setPromedio(promedio);
    } 
       
    /**
     * Carga un codigo de Articulo por teclado 
     */
    public static void cargarCodigo( )
    {
        System.out.print("Ingrese el C�digo: ");
        int codigo = Consola.readInt();
        art.setCodigo(codigo);
    }
       
    /**
     * Carga un registro de Articulo por teclado 
     */
    public static void leerArticulo ( )
    { 
        cargarCodigo();
        System.out.print("Ingrese la descripción: ");
        String nombre = Consola.readLine();
        art.setDescripcion(nombre);
    } 

    public static void main (String[] args)
    {
        int x, op;

        m1 = new DirectAccessFile( "Alumnos.dat", "rw" );
        m2 = new DirectAccessFile( "Articulos.dat", "rw" );
        
        alu = new Alumno();
        art = new Articulo();
        
        do
        {
            System.out.println ("\n\nOpciones ABM de archivos");
            System.out.println ("1.  Alta de un registro de Alumno");
            System.out.println ("2.  Listado de Alumnos");
            System.out.println ("3.  Ordenar archivo de Alumnos");
            System.out.println ("4.  Búsqueda binaria de Alumnos");

            System.out.println ("-----------------------------------");
            
            System.out.println ("5.  Alta de un registro de Articulo");      
            System.out.println ("6.  Listado de Articulos");
            System.out.println ("7.  Ordenar archivo de Articulos");
            System.out.println ("8.  Búsqueda binaria de Articulos");
            
            System.out.println ("9. Salir");

            System.out.print ( "Ingrese opcion: " );
            op = Consola.readInt();
            switch (op)
            {
                case 1:  
                         System.out.println( "Ingrese los datos del Alumno: " );
                         leerAlumno();
                         m1.add( alu );
                         break;   
                
                case 2:  
                         System.out.print("Se muestra el archivo de Alumnos...");
                         mostrarTodo( m1 );
                         break;
                         
                case 3:  
                         System.out.print( "Ordenando archivo de Alumnos..." );
                         m1.sort();
                         System.out.println("Hecho...");
                         break;
                
                case 4:
                         System.out.print( "Ingrese el legajo del alumno a buscar: " );
                         x = Consola.readInt();
                         alu.setLegajo(x);
                         long b1 = m1.binarySearch( alu );
                         if ( b1 != -1 ) System.out.print( "Alumno encontrado (binarySearch()): " + m1.get( b1 ) );
                         else System.out.print( "Alumno no encontrado (binarySearch())" );
                         break;
               
               case 5:  
                         System.out.println("Ingrese los datos del Artículo: ");
                         leerArticulo();
                         m2.add( art );
                         break;   

               case 6:  
                         System.out.print("Se muestra el archivo de Articulos...");
                         mostrarTodo( m2 );
                         break;
               case 7:  
                         System.out.print( "Ordenando archivo de Articulos..." );
                         m2.sort();
                         System.out.println("Hecho...");
                         break;
                
               case 8:
                         System.out.print( "Ingrese el código del artículo a buscar: " );
                         x = Consola.readInt();
                         art.setCodigo( x );
                         long b2 = m2.binarySearch( art );
                         if ( b2 != -1 )System.out.print( "Articulo encontrado (binarySearch()): " + m2.get( b2 ) );
                         else System.out.print( "Articulo no encontrado (binarySearch())" );
                         break;

                case 9: 
                         m1.close();
                         m2.close();
                         break;
            }
         }
         while (op != 9);
         System.exit(0);
    }
}
