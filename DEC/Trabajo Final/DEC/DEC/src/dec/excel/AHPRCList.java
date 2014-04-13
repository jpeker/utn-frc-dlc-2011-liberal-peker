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
public class AHPRCList {
    public Map<Integer, Double> mapaValoresAlternativas;
    public AHPRCList(){
    mapaValoresAlternativas.put(3, 0.58);
    mapaValoresAlternativas.put(4, 0.9);
    mapaValoresAlternativas.put(5, 1.12);
    mapaValoresAlternativas.put(6, 1.24);
    mapaValoresAlternativas.put(7, 1.32);
    mapaValoresAlternativas.put(8, 1.41);
    mapaValoresAlternativas.put(9, 1.45);
    mapaValoresAlternativas.put(10, 1.49);
    }
    public double getMapValue(int cantAlternativas){
    double mapValue = -1;
    if(cantAlternativas>2)
    {
        mapValue = mapaValoresAlternativas.get(cantAlternativas);
    }
    return mapValue;
    }

}
