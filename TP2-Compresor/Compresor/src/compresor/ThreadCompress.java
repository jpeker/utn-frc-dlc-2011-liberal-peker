/*
 * ThreadCompress.java
 *
 */

package compresor;

import GUI.GestorVentanaPrincipal;
import java.io.File;

/**
 *
 * @author Liberal, Peker
 */
public class ThreadCompress extends Thread{

    private Compresor compresor;
    private File arch;
    private String nombre;
    private GestorVentanaPrincipal gestor;

public ThreadCompress( File f, GestorVentanaPrincipal gestor) {

        this.nombre= f.getName();
        compresor = new Compresor(gestor);
        arch = f;
        this.gestor = gestor;
    }

      @Override
    public void run(){

        compresor.comprimirRecursivo(arch);

    }


}
