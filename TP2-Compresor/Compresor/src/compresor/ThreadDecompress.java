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
    public boolean stopRequested=false;
    private int count = 0;

    public ThreadDecompress(File arch, GestorVentanaPrincipal gestor) {
        this.arch=arch;
        compresor = new Compresor(gestor);
    }


    @Override
    public void run(){
        while (!stopRequested) {
        try {
          Thread.sleep(300);
      }
      catch (InterruptedException x) {}
      System.out.println("Running ... count=" + count);
      count++;
      compresor.descomprimirRecursivo(arch);
      stopRequested=true;
    }
  }

  public void stopRequest() {
      compresor.setdescomp(false);
  }

 }