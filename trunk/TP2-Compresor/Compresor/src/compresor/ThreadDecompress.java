/*
 * ThreadDecompress.java
 *
 */

package compresor;

/**
 *
 * @author Liberal, Peker
 */

import GUI.GestorVentanaPrincipal;
import java.io.File;


public class ThreadDecompress extends Thread{

    private Compresor compresor;
    private File arch;
   



    public ThreadDecompress(File arch, GestorVentanaPrincipal gestor) {
        this.arch=arch;
        compresor = new Compresor(gestor);
        
        //this.barra=barra;
        //this.texto=texto;
    }


    @Override
    public void run(){
    compresor.descomprimirRecursivo(arch);

    }

    }