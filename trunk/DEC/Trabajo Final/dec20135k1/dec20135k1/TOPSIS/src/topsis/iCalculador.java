/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Federico
 */

public interface iCalculador {
    
    /**
     *  Este metodo arma la tabla Normal (Normalizacion de matriz)
     * @param listaCriterios Lista de los criterios del modelo es un arraylist de Criterios(nombre,peso y criterio)
     * @param listaAlternativa Lista de alternativas del modelo es un arraylist de Alternativas (nombre)
     * @param matriz Es una matriz con los datos del modelo
     * @param modelo Es el modelo del jTable(tablaMatriz)
     * @return El modelo actualizado y la matriz Normal
     */
    public Resultado armarTabla(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matriz[][],DefaultTableModel modelo);
    /**
     * Este metodo arma la tabla normalizada 
     * @param listaCriterios ver armarTabla
     * @param listaAlternativa ver armarTabla
     * @param matrizDatos Es la matriz de datos del modelo
     * @param matrizNormal Es la matriz Normal devuelta por armarTabla
     * @param modelo Es el modelo del jTable(tablaMatrizNormalizada)
     * @return El modelo actualizado y la matriz Normalizada
     */
    public Resultado armarTablaNormalizada(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matrizDatos[][],double matrizNormal[][],DefaultTableModel modelo);
    /**
     * Este metodo arma la tabla normalizada y ponderada
     * @param listaCriterios ver armarTabla
     * @param listaAlternativa ver armarTabla
     * @param matriz Es la matriz Normalizada devuelta por armarTablaNormalizada
     * @param modelo Es el modelo del jTable(tablaMatrizNormalizadaPonderada)
     * @return El modelo actualizado y la matriz Normalizada y Ponderada
     */
    public Resultado armarTablaNormalizadaPonderada(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matriz[][],DefaultTableModel modelo);
    /**
     * Este metodo calcula la distancia del ideal de cada alternativa
     * @param listaCriterios ver armarTabla
     * @param listaAlternativa ver armarTabla
     * @param matriz Es la matriz Normalizada y Pondera devuelta por armarTablaNormalizadaPonderada
     * @param modelo Es el modelo de del jTable(tablaIdeal)
     * @param p Es el tipo de distancia (Ciudad o Euclídea)
     * @return El modelo actualizado y la matriz ideal
     */
    public Resultado calculoDistanciaIdeal(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matriz[][],DefaultTableModel modelo, int p);
   /**
     * Este metodo calcula la distancia del AntiIdeal de cada alternativa
     * @param listaCriterios ver armarTabla
     * @param listaAlternativa ver armarTabla
     * @param matriz Es la matriz Normalizada y Pondera devuelta por armarTablaNormalizadaPonderada
     * @param modelo Es el modelo de del jTable(tablaAntiIdeal)
     * @return El modelo actualizado y la matriz AntiIdeal
     */
    public Resultado calculoDistanciaAntiIdeal(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matriz[][],DefaultTableModel modelo,int p);
    /**
     * Este metodo calcula el indice de similaridad de cada alternativa
     * @param listaCriterios ver armarTabla
     * @param listaAlternativa ver armarTabla
     * @param matrizIdeal Es la matriz ideal devuelta por calculoDistanciaIdeal
     * @param matrizAntiIdeal En la matriz antiIdeal devuelta por calculoDistanciaAntIdeal 
     * @param modelo Es el modelo del jTable(tablaIndice)
     * @param p Es el tipo de distancia (Ciudad o Euclídea)
     * @return El modelo actualizado y la matriz Resultado(ojo si se lo va usar mas adelante solo usar la columna 1 que contiene los datos, la columna 0 contiene un orden)
     */
    public Resultado calculoIndice(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matrizIdeal[][],double matrizAntiIdeal[][],DefaultTableModel modelo);
    
}
