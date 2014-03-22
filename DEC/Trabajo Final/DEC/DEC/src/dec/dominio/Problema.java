/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.dominio;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Emiliano
 */
public class Problema implements Serializable {
    private static final long serialVersionUID = 1L;   
    private Integer id;
    private String nombre;
    private String descripcion;
    private String autor;
    private Date fecha;
    private List<Criterio> criterioList;
    private List<Alternativa> alternativaList;
    private Double P;
    private transient File file;

    public Problema() {
        this.alternativaList = new ArrayList<Alternativa>();
        this.criterioList = new ArrayList<Criterio>();
    }

    public Problema(Integer id) {
        this.id = id;
    }

    public Problema(Integer id, String nombre) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getP() {
        return P;
    }

    public void setP(Double P) {
        this.P = P;
    }

    public List<Criterio> getCriterioList() {
        return criterioList;
    }

    public void setCriterioList(List<Criterio> criterioList) {
        this.criterioList = criterioList;
    }

    public List<Alternativa> getAlternativaList() {
        return alternativaList;
    }

    public void setAlternativaList(List<Alternativa> alternativaList) {
        this.alternativaList = alternativaList;        
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Float getSumPesos(){
        Float sum = 0F;
        for (Criterio criterio : criterioList) {
            sum += criterio.getPeso();
        }
        return sum;
    }

    public void addCriterio(Criterio criterio){        
        this.criterioList.add(criterio);
        criterio.setProblemaId(this);
        for (Alternativa a : alternativaList) {
            a.addCriterio(criterio);
        }
    }
    public void removeCriterio(Criterio criterio){
        this.criterioList.remove(criterio);
        criterio.setProblemaId(null);
    }

    public void addAlternativa(Alternativa alternativa){        
        for (Criterio criterio : criterioList) {
            alternativa.addCriterio(criterio);
        }
        this.alternativaList.add(alternativa);
        alternativa.setProblemaId(this);
    }
    public void removeAlternativa(Alternativa alternativa){
        this.alternativaList.remove(alternativa);
        alternativa.setProblemaId(null);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Problema)) {
            return false;
        }
        Problema other = (Problema) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return super.equals(other);
    }

    @Override
    public String toString() {
        return "dec.dominio.Problema[id=" + id + "]";
    }

}
