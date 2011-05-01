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
     private static  OpenHashFile m1;
     //private static  ListHashFile m1;
     private static Alumno   alu;

     /**
      * Muestra el contenido de un archivo (incluidos los registros marcados como borrados) 
      * en consola estandar.
      */
     public static void mostrarTodo ( RegisterFile m )
     {
          RegisterFileIterator rfi = m.createIterator();
          if( rfi == null ) System.out.println( "El archivo est� vac�o..." );
          else{
          while ( rfi.hasNext() )
          {
               Grabable x = rfi.current();
               System.out.print( x.toString() );
               rfi.next();
          }
     }  }

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
       
    public static void main (String[] args)
    {
        int x, op;
        Alumno resp = null;
      m1 = new OpenHashFile( "Alumnos.dat", "rw",11 );
       // m1 = new ListHashFile( "Alumnos.dat", "rw" );
        alu = new Alumno();
        
        do
        {
            System.out.println ("\n\nOpciones ABM de archivos");
            System.out.println ("1.  Alta de un registro de Alumno");
            System.out.println ("2.  Listado de Alumnos");
            System.out.println ("3.  Baja de un Alumno");
            System.out.println ("4.  B�squeda de un Alumno");
            System.out.println ("5.  Modificaci�n de un Alumno");
            
            System.out.println ("6. Salir");

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
                         System.out.print( "Ingrese el legajo del alumno a borrar: " );
                         x = Consola.readInt();
                         alu.setLegajo(x);
                         if ( m1.remove( alu ) )System.out.println("Baja realizada...");
                         else System.out.println("Ese alumno no estaba en el archivo");
                         break;
                
                case 4:
                         System.out.print( "Ingrese el legajo del alumno a buscar: " );
                         x = Consola.readInt();
                         alu.setLegajo(x);
                         resp = ( Alumno ) m1.find( alu );
                         if ( resp != null ) System.out.print( "Alumno encontrado: " + resp );
                         else System.out.print( "Alumno no encontrado..." );
                         break;
               
                case 5:
                         System.out.println( "Ingrese los datos del alumno a modificar:" );
                         leerAlumno();
                         if ( m1.update( alu ) ) System.out.print( "Datos modificados..." );
                         else System.out.print( "Alumno no encontrado..." );
                         break;
                         
                case 6: 
                         m1.close();
                         break;
            }
         }
         while (op != 6);
         System.exit(0);
    }
}
