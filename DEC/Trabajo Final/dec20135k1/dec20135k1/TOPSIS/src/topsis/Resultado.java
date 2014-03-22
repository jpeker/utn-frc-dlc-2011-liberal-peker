/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Federico
 */
public class Resultado {

    private DefaultTableModel modelo;
    private double matriz[][];
    
    public Resultado(DefaultTableModel modelo, double[][] matriz) {
        this.modelo = modelo;
        this.matriz = matriz;
    }   
    
    
    
    /**
     * @return the modelo
     */
    public DefaultTableModel getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the matriz
     */
    public double[][] getMatriz() {
        return matriz;
    }

    /**
     * @param matriz the matriz to set
     */
    public void setMatriz(double[][] matriz) {
        this.matriz = matriz;
    }
}
