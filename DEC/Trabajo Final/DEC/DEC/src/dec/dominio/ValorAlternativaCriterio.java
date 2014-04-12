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
public class ValorAlternativaCriterio implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Float valor;
    private Problema problemaId;
    private Criterio criterioId;
    private Alternativa alternativaId;
    
    private Float valorAuxiliar;
    private Float valorIdeal;
    private Float valorAntiIdeal;

    public ValorAlternativaCriterio() {
    }

    public ValorAlternativaCriterio(Integer id) {
        this.id = id;
    }

    public ValorAlternativaCriterio(Criterio criterioId) {
        this.criterioId = criterioId;
    }

    public ValorAlternativaCriterio(Problema problemaId, Criterio criterioId, Alternativa alternativaId) {
        this.problemaId = problemaId;
        this.criterioId = criterioId;
        this.alternativaId = alternativaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Problema getProblemaId() {
        return problemaId;
    }

    public void setProblemaId(Problema problemaId) {
        this.problemaId = problemaId;
    }

    public Criterio getCriterioId() {
        return criterioId;
    }

    public void setCriterioId(Criterio criterioId) {
        this.criterioId = criterioId;
    }

    public Alternativa getAlternativaId() {
        return alternativaId;
    }

    public void setAlternativaId(Alternativa alternativaId) {
        this.alternativaId = alternativaId;
    }

    public Float getValorAuxiliar() {
        return valorAuxiliar;
    }

    public void setValorAuxiliar(Float valorAuxiliar) {
        this.valorAuxiliar = valorAuxiliar;
    }

    public Float getValorAntiIdeal() {
        return valorAntiIdeal;
    }

    public void setValorAntiIdeal(Float valorAntiIdeal) {
        this.valorAntiIdeal = valorAntiIdeal;
    }

    public Float getValorIdeal() {
        return valorIdeal;
    }

    public void setValorIdeal(Float valorIdeal) {
        this.valorIdeal = valorIdeal;
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
        if (!(object instanceof ValorAlternativaCriterio)) {
            return false;
        }
        ValorAlternativaCriterio other = (ValorAlternativaCriterio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dec.dominio.ValorAlternativaCriterio[id=" + id + "]";
    }

}
