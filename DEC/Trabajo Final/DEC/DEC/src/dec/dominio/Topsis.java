/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.dominio;

/**
 *
 * @author Kapica Liberal Ramirez
 */

/*
 * NO EMPLEADA ESTA CLASE NO SE PARA QUE ESTA EL DEBUG NUNCA LLAMA A LA
 * INSTANCIA.
 *
 *
 */

public class Topsis {

    private final Problema problema;
    private final Integer P;

    public Topsis(Problema problema, Integer P) {
        this.problema = problema;
        this.P = P;
    }

    public Problema contruirMatrizNormalizada(){
        Float sumPesos = this.problema.getSumPesos();
        for (Criterio criterio : this.problema.getCriterioList()) {
            criterio.setPesoNormalizado(criterio.getPeso()/sumPesos);
        }        
        for (int i = 0; i < this.problema.getCriterioList().size(); i++) {
            Float sumCriterio = 0F;
            for (int j = 0; j < this.problema.getAlternativaList().size(); j++) {
                sumCriterio += this.problema.getAlternativaList().get(j).getValores().get(i).getValor();
            }
            for (int j = 0; j < this.problema.getAlternativaList().size(); j++) {
                ValorAlternativaCriterio valor = this.problema.getAlternativaList().get(j).getValores().get(i);
                valor.setValorAuxiliar(valor.getValor()/sumCriterio);
            }
        }
        return this.problema;
    }

    public Problema ponderarMatriz(){
        for (int i = 0; i < this.problema.getCriterioList().size(); i++) {
            Criterio criterio = this.problema.getCriterioList().get(i);
            Float ideal = 0F, antiIdeal = 1F;
            for (int j = 0; j < this.problema.getAlternativaList().size(); j++) {
                ValorAlternativaCriterio valor = this.problema.getAlternativaList().get(j).getValores().get(i);
                valor.setValorAuxiliar(valor.getValorAuxiliar()*criterio.getPesoNormalizado());
                if(valor.getValorAuxiliar() > ideal){
                    ideal = valor.getValorAuxiliar();
                }else if(valor.getValorAuxiliar() <antiIdeal ){
                    antiIdeal = valor.getValorAuxiliar();
                }
            }
            criterio.setIdeal(ideal);
            criterio.setAntiIdeal(antiIdeal);
        }
        return this.problema;
    }

    public Problema calcularDistancias(){
        for(Alternativa alternativa : this.problema.getAlternativaList()){
            Float ideal = 0F, antiIdeal = 0F;
            for(ValorAlternativaCriterio valor : alternativa.getValores()){                
                Float auxIdeal = valor.getValorAuxiliar()-valor.getCriterioId().getIdeal();
                valor.setValorIdeal((float)Math.pow(Math.abs(auxIdeal), P));
                ideal += valor.getValorIdeal();

                Float auxAntiIdeal = valor.getValorAuxiliar()-valor.getCriterioId().getAntiIdeal();
                valor.setValorAntiIdeal((float)Math.pow(Math.abs(auxAntiIdeal), P));
                antiIdeal += valor.getValorAntiIdeal();
            }
            alternativa.setDistanciaIdeal((float)Math.pow(ideal, (1/P)));
            alternativa.setDistanciaAntiIdeal((float)Math.pow(antiIdeal, (1/P)));
        }
        return this.problema;
    }

    
}
