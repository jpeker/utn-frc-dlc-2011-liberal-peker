/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utn.frc.dlc.base;

/**
 *
 * @author dlcusr
 */
public class Comentarios {
    private int idComentario;
    private Usuario user;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    public Comentarios() {
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

   

    public int getIdfoto() {
        return idfoto;
    }

    public void setIdfoto(int idfoto) {
        this.idfoto = idfoto;
    }
  
    private int idfoto;
    private String comentarios;


}
