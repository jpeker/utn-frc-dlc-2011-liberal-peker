/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utn.frc.dlc.base;

/**
 *
 * @author dlcusr
 */
public class Fotos {


    public Fotos() {
    }
    private String  pathfoto;
    private int idAmigo1;
    private int idAmigo2;
    private int idfoto;
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public int getIdAmigo1() {
        return idAmigo1;
    }

    public void setIdAmigo1(int idAmigo1) {
        this.idAmigo1 = idAmigo1;
    }

    public int getIdAmigo2() {
        return idAmigo2;
    }

    public void setIdAmigo2(int idAmigo2) {
        this.idAmigo2 = idAmigo2;
    }

    public int getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(int idfoto) {
        this.idfoto = idfoto;
    }

    public String getPathfoto() {
        return pathfoto;
    }

    public void setPathfoto(String pathfoto) {
        this.pathfoto = pathfoto;
    }


}
