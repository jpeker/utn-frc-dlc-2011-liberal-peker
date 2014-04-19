/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.excel;

import java.util.Map;

/**
 *
 * @author PC ACER
 */
public class AHPRC {
    public Map<Integer, Double> mapaValoresAlternativas;
    public AHPRC(){
    //Cargar todos los valores de IA, soporta hasta 10 alternativas.
    mapaValoresAlternativas.put(3, 0.58);
    mapaValoresAlternativas.put(4, 0.9);
    mapaValoresAlternativas.put(5, 1.12);
    mapaValoresAlternativas.put(6, 1.24);
    mapaValoresAlternativas.put(7, 1.32);
    mapaValoresAlternativas.put(8, 1.41);
    mapaValoresAlternativas.put(9, 1.45);
    mapaValoresAlternativas.put(10, 1.49);
    }
    public double getIAValue(int cantAlternativas){
    double IAValue = mapaValoresAlternativas.get(cantAlternativas);
    return IAValue;
    }
    public double getIC(int cantCriterios,double lambaMax){

    return 1.2;
    }

    public double resolveLamdaMax(double[][] arrayOfCriteriaComparsion){
        arrayOfCriteriaComparsion[0][0]=1.2;
        return 0;
    }

}
