/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;

/**
 *
 * @author Emiliano
 */
public class Alternativa implements Serializable{
    private static final long serialVersionUID = 1L;   
    private Integer id;
    private String nombre;
    private Integer orden;
    private Problema problemaId;
    private List<ValorAlternativaCriterio> valores;
    
    private Float distanciaIdeal;    
    private Float distanciaAntiIdeal;

    public Alternativa() {
        this.valores = new ArrayList<ValorAlternativaCriterio>();
    }

    public Alternativa(Integer id) {
        this.id = id;
    }

    public Alternativa(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Alternativa(String nombre) {
        this.nombre = nombre;
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

    public Problema getProblemaId() {
        return problemaId;
    }

    public void setProblemaId(Problema problemaId) {
        this.problemaId = problemaId;
    }

    public List<ValorAlternativaCriterio> getValores() {
        return valores;
    }

    public void setValores(List<ValorAlternativaCriterio> valores) {
        this.valores = valores;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public void addCriterio(Criterio criterio){
        this.valores.add(new ValorAlternativaCriterio(problemaId, criterio, this));
    }

    public Float getDistanciaAntiIdeal() {
        return distanciaAntiIdeal;
    }

    public void setDistanciaAntiIdeal(Float distanciaAntiIdeal) {
        this.distanciaAntiIdeal = distanciaAntiIdeal;
    }

    public Float getDistanciaIdeal() {
        return distanciaIdeal;
    }

    public void setDistanciaIdeal(Float distanciaIdeal) {
        this.distanciaIdeal = distanciaIdeal;
    }

    public Float getProximidad(){
        return distanciaAntiIdeal / (distanciaIdeal+distanciaAntiIdeal);
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
        if (!(object instanceof Alternativa)) {
            return false;
        }
        Alternativa other = (Alternativa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if(this.id.equals(other.id)){
            return true;
        }
        return super.equals(object);        
    }*/

    @Override
    public String toString() {
        return "dec.dominio.Alternativa[id=" + id + "]";
    }    
}
