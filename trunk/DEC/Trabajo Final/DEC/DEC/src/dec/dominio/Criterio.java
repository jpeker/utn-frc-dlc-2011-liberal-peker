/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.dominio;

import java.io.Serializable;

/**
 *
 * @author Kapica Liberal Ramirez
 */
public class Criterio implements Serializable {
    private static final long serialVersionUID = 1L;   
    private Integer id;
    private String nombre;
    private Float peso;
    private boolean maximizacion;
    private Problema problemaId;
    
    private Float pesoNormalizado;    
    private Float ideal;    
    private Float antiIdeal;

    public Criterio() {
    }

    public Criterio(Integer id) {
        this.id = id;
    }
    public Criterio(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Criterio(String nombre, Float peso) {
        this.nombre = nombre;
        this.peso = peso;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Problema getProblemaId() {
        return problemaId;
    }

    public void setProblemaId(Problema problemaId) {
        this.problemaId = problemaId;
    }

    public Float getPesoNormalizado() {
        return pesoNormalizado;
    }

    public void setPesoNormalizado(Float pesoNormalizado) {
        this.pesoNormalizado = pesoNormalizado;
    }

    public Float getAntiIdeal() {
        return antiIdeal;
    }

    public void setAntiIdeal(Float antiIdeal) {
        this.antiIdeal = antiIdeal;
    }

    public Float getIdeal() {
        return ideal;
    }

    public void setIdeal(Float ideal) {
        this.ideal = ideal;
    }

    public boolean isMaximizacion() {
        return maximizacion;
    }

    public void setMaximizacion(boolean maximizacion) {
        this.maximizacion = maximizacion;
    }
    
    

    /*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criterio)) {
            return false;
        }
        Criterio other = (Criterio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if(this.id.equals(other.id)){
            return true;
        }
        return super.equals(other);
    }*/

    @Override
    public String toString() {
        return "dec.dominio.Criterio[id=" + id + "]";
    }

}
