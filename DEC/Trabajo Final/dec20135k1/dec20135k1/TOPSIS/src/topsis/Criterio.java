/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis;

/**
 *
 * @author Federico
 */
public class Criterio {
    
    private String nombre;
    private double peso;
    private int tipo;

    public Criterio(String nombre,double peso,int tipo)
    {
        this.nombre=nombre;
        this.peso=peso;
        this.tipo=tipo;
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
     * @return the peso
     */
    public double getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
}
