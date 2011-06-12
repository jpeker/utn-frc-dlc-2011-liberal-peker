/*
 * ThreadTiempos.java
 *
 */

package GUI;


/**
 *
 * @author Liberal, Peker
 */
public class ThreadTiempos extends Thread{
    private GestorVentanaPrincipal gestor;
    private long tiempoI;
    private long tiempoF;
    private int cont;

    public ThreadTiempos(GestorVentanaPrincipal gestor)
    {

        this.gestor = gestor;
        cont = 0;
       
    }

    @Override
    public void run()
    {
        tiempoI = System.currentTimeMillis();
        try {
        sleep(100);
        while (gestor.getEstado() == GestorVentanaPrincipal.Estado.comprimir)
        {
            

                sleep(100);
                tiempoF = System.currentTimeMillis();
                Float t = (float)((tiempoF - tiempoI)/1000) ;
                gestor.setTxtTiempoProceso(String.valueOf(t) + "s");
                if (cont == 10)
                {
                    gestor.calcularTiempo();
                    cont = 0;
                }
                cont ++;



           
            }
         } catch (InterruptedException ex) {
               System.out.println("Error: " + ex.getMessage());


        }

    }

}
