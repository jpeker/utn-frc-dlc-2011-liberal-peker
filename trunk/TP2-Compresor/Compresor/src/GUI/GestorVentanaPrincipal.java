/*
 * GestorVentanaPrincipal.java
 *
 */

package GUI;
import javax.swing.*;

/**
 *
 * @author Liberal, Peker
 *
 */

public class GestorVentanaPrincipal {

    private JLabel _txtArchivo;
    private JProgressBar _jpbArchivo;
    private JLabel _txtTiempoProceso;
    private JLabel _txtTiempoRestante;
    private JLabel _txtEstado;
    private JProgressBar _jpbProceso;
    private long tiempoRestante;
    public static enum Estado {iniciado, comprimir, descomprimir , comprimirPausado, descomprimirPausado};
    private  Estado estado ;

    public GestorVentanaPrincipal( JLabel _txtArchivo, JProgressBar _jpbArchivo, JLabel _txtTiempoProceso, JLabel _txtTiempoRestante, JLabel _txtEstado, JProgressBar _jpbProceso)
    {
     this._txtArchivo = _txtArchivo;
     this._jpbArchivo = _jpbArchivo;
     this._txtTiempoProceso  = _txtTiempoProceso;
     this._txtTiempoRestante  = _txtTiempoRestante;
     this._txtEstado  = _txtEstado;
     this._jpbProceso = _jpbProceso;
     this.tiempoRestante = 0;

//     this.estado = estado.iniciado;

    }

    public void setTxtArchivo( String text)
    {
        this._txtArchivo.setText(text);
    }

    public void setTxEstado(String text)
    {
        this._txtEstado.setText(text);
    }

    public void setTxtTiempoProceso( String text)
    {
        this._txtTiempoProceso.setText(text);
    }

    public void setTxtTiempoRestante( String text)
    {
        this._txtTiempoRestante.setText(text);
    }
    /*
     * Actualiza la progress Bar de Archivos
     * @param i valor
     * @param tam tamaño de la barra
     */
     public void actualizarJPBArchivos (int i , long tam)
    {
        this._jpbArchivo.setValue((int)((i*100/tam)));
        this._jpbArchivo.setString("Porcentaje: " + this._jpbArchivo.getValue() + "%");
        Float f = (float)tam;
    }
    /*
     * Actualiza la progress Bar de Archivos
     * @param i valor
     * @param tam tamaño de la barra
     */

    public void actualizarJPBProceso(int i, long tam)
    {

        this._jpbProceso.setValue((int)((i*100/tam)));
        this._jpbProceso.setString("Porcentaje: " + this._jpbProceso.getValue() + "%");
        Float f = (float)tam;
        //this._jpbProceso.setText(i + " Bytes de "+ f /1024+" KB");

    }
    public void setEstadoInicial()
    {
        //deshabilitar botones
        //limpiar path
    }
    public void calcularTiempo(long posicion, long tamanio, long tiempo)
    {
        if (posicion != 0)
        {
         this.tiempoRestante = ((tamanio * tiempo) / posicion) - tiempo;
        }
        
        //this._txtTiempoRestante.setText(String.valueOf(this.tiempo));

    }
    public void calcularTiempo()
    {
        this._txtTiempoRestante.setText(String.valueOf((float)tiempoRestante /10 ) + "s");
    }
//    public long getTiempo()
//    {
//        return this.tiempo;
//    }
    public Estado getEstado()

    {
        return estado;
    }
    public void setEstado(Estado e)
    {
        this.estado = e;
    }

}
