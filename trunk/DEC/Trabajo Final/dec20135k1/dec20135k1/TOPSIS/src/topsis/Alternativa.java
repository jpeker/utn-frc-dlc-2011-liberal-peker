/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Federico
 */
public class Alternativa {
    
    private String nombre;
    private int numero;
    private List<Integer> valores = new ArrayList<Integer>();

    public List<Integer> getValores() {
        return valores;
    }
/**
 * 
 * @param nombre
 * @param numero 
 */
    public Alternativa(String nombre)
    {
        this.nombre=nombre;
        //this.numero=numero;
    }
    public void addValor(int valor){
        this.valores.add(valor);
    }
    public void limpiarValores(){
        this.valores.clear();
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
