/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.excel;

import dec.dominio.Problema;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emiliano
 */
public class ElectreToExcel extends ToExcel{

    public ElectreToExcel(Problema problema, File file) {
        super(problema, file);
    }

    @Override
    protected void detail() {
        //Normalizacion
        //Seteamos los pesos Normalizados
        valores.clear();
        valores.add("Matrix Normalizada");
        this.libro.addRow(valores, RowType.TITLE);
        this.libro.addEmptyRow();
        pesos.clear();
        pesos.add("Pesos");
        for (int i = 1; i < posPesos.length-1; i++) {
            pesos.add("="+posPesos[i]+"/"+posPesos[posPesos.length-1]);
        }
        posPesos = this.libro.addRow(pesos,RowType.CONTENT);



        String primeraFila[] = posValores.get(0);
        String ultimaFila[] = posValores.get(posValores.size()-1);
        List mejor = new ArrayList();
        List rango = new ArrayList();
        mejor.add("Mejor");
        rango.add("Rango");
        for (int i = 1; i < primeraFila.length; i++) {
            if(super.problema.getCriterioList().get(i-1).isMaximizacion()){
                mejor.add("=MAX("+primeraFila[i]+":"+ultimaFila[i]+")");
            }else{
                mejor.add("=MIN("+primeraFila[i]+":"+ultimaFila[i]+")");
            }
            rango.add("=(MAX("+primeraFila[i]+":"+ultimaFila[i]+")-MIN("+primeraFila[i]+":"+ultimaFila[i]+"))");
        }
        String posMejor[] = this.libro.addRow(mejor,RowType.CONTENT);
        String posRango[] = this.libro.addRow(rango,RowType.CONTENT);
        this.libro.addEmptyRow();

        //seteamos los valores Normalizados
        this.libro.addRow(cabecera,RowType.HEADER);
        posValores2 = new ArrayList<String[]>();
        for (int i = 0; i < posValores.size(); i++) {
            valores.clear();
            String[] fila = posValores.get(i);
            valores.add("=T("+fila[0]+")");
            for (int j = 1; j < fila.length; j++) {
                valores.add("=ABS("+posMejor[j]+"-"+fila[j]+")/("+posRango[j]+")");
            }
            posValores2.add(this.libro.addRow(valores,RowType.CONTENT));
        }
        this.libro.addEmptyRow();

        //Indices de concordancia
        valores.clear();
        valores.add("Indices de concordancia");
        this.libro.addRow(valores, RowType.TITLE);
        this.libro.addEmptyRow();

        valores.clear();
        valores.add("Alternativas");
        for(int i = 0; i<super.posValores.size(); i++){
            valores.add("=T("+super.posValores.get(i)[0]+")");
        }
        this.libro.addRow(valores,RowType.HEADER);

        posValores.clear();
        for (int i = 0; i < posValores2.size(); i++) {
            valores.clear();
            String[] fila = posValores2.get(i);
            valores.add("=T("+fila[0]+")");
            for (int j = 1; j < fila.length; j++) {
                if(i!=(j-1)){
                    String filaComparacion[]=posValores2.get(j-1);
                    StringBuilder cell = new StringBuilder("=SUM(");
                    
                    for(int k = 1; k<filaComparacion.length; k++){

                        cell.append("IF("+fila[k]+">"+filaComparacion[k]+","+super.posPesos[k]+",0)");
                        if(filaComparacion.length-1!=k){
                            cell.append(",");
                        }
                    }
                    cell.append(")");
                    System.out.println(cell.toString());
                    valores.add(cell.toString());
                }else{
                    valores.add(" X ");
                }
            }
            posValores.add(this.libro.addRow(valores,RowType.CONTENT));
        }
        this.libro.addEmptyRows(2);
    }

    @Override
    protected void header() {
        
    }
    
}
