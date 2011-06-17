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

// Hilo para realizar la descompresion
public class ThreadDecompress extends Thread{

    private Compresor compresor;
    private File arch;
    public boolean stopRequested=false; // parar o no el hilo de descompresion
    //private int count = 0;

    public ThreadDecompress(File arch, GestorVentanaPrincipal gestor) {
        this.arch=arch;
        compresor = new Compresor(gestor);
    }


    @Override
    public void run(){
        while (!stopRequested) {
      //  try {
      //     Thread.sleep(300);
      // }
      //catch (InterruptedException x) {}
      //System.out.println("Running ... count=" + count);
      //count++;
      compresor.descomprimirRecursivo(arch);
      stopRequested=true;
    }
  }

  public void stopRequest() { // Detener la descompresion recursiva
      compresor.setdescomp(false);
  }

 }