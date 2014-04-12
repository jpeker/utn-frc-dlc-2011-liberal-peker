/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.excel;

import dec.dominio.Alternativa;
import dec.dominio.Criterio;
import dec.dominio.Problema;
import dec.dominio.ValorAlternativaCriterio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Kapica Liberal Ramirez
 */
public abstract class ToExcel {
    protected Libro libro;
    protected Problema problema;
    private File file;

    protected List pesos = new ArrayList();
    protected String posPesos[] = null;
    protected String posTotales[] = null;
    protected List<String[]> posValores2;
    protected List cabecera = new ArrayList();
    protected List valores = new ArrayList();
    protected List<String[]> posValores;

    public ToExcel(Problema problema, File file) {
        this.problema = problema;
        this.file = file;
    }

    public void excecute() throws FileNotFoundException, IOException{
        this.libro = new Libro();
        this.libro.addHoja("primero");

        List list = new ArrayList();
        list.add(problema.getNombre());
        libro.addRow(list, RowType.TITLE);
        if(problema.getAutor()!=null){
            list.clear();
            list.add(problema.getAutor());
            libro.addRow(list, RowType.CONTENT);
        }

        list.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        list.add(sdf.format(problema.getFecha()));
        libro.addRow(list, RowType.CONTENT);

        if(problema.getDescripcion()!=null){
            list.clear();
            list.add(problema.getDescripcion());
            libro.addRow(list, RowType.CONTENT);
        }
        libro.addEmptyRow();
        this.header();
        libro.addEmptyRow();

        //Seteamos los pesos y la cebecera
        valores.clear();
        valores.add("Matriz de Decisión");
        this.libro.addRow(valores, RowType.TITLE);
        this.libro.addEmptyRow();

        pesos.add("Pesos");
        for(Criterio c : problema.getCriterioList()){
            pesos.add(c.getPeso());
        }

        posPesos = this.libro.addRow(pesos,RowType.CONTENT);
        String aux = this.libro.addCell("=SUM("+posPesos[1]+":"+posPesos[posPesos.length-1]+")");
        posPesos = Arrays.copyOf(posPesos, posPesos.length+1);
        posPesos[posPesos.length-1] = aux;
        cabecera.add("Alternativas");
        for(Criterio c : problema.getCriterioList()){
            cabecera.add(c.getNombre()+": "+c.getPeso());
        }
        this.libro.addRow(cabecera,RowType.HEADER);

        //seteamos los valores iniciales
        posValores = new ArrayList<String[]>();
        for(Alternativa a : problema.getAlternativaList()){
            valores.clear();
            valores.add(a.getNombre());
            for(ValorAlternativaCriterio valor : a.getValores()){
                valores.add(valor.getValor());
            }
            posValores.add(this.libro.addRow(valores,RowType.CONTENT));
        }
        List totales = new ArrayList();
        totales.add("Total");
        String primeraFila[] = posValores.get(0);
        String ultimaFila[] = posValores.get(posValores.size()-1);
        for (int i = 1; i < primeraFila.length; i++) {
            aux = "=SUM("+primeraFila[i]+":"+ultimaFila[i]+")";
            totales.add(aux);
        }
        posTotales = this.libro.addRow(totales,RowType.HEADER);
                
        this.libro.addEmptyRows(1);
        
        this.detail();

        this.libro.writeWorkbook(this.file);
    }

    protected abstract void detail();
    protected abstract void header();
}
