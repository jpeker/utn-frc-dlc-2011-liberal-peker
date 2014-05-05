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
 * @author Kapica Liberal Ramirez
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
        valores.add("Matriz Normalizada");
        this.libro.addRow(valores, RowType.TITLE);
        this.libro.addEmptyRow();
        pesos.clear();
        pesos.add("Pesos");
        for (int i = 1; i < posPesos.length-1; i++) {
            pesos.add("="+posPesos[i]+"/"+posPesos[posPesos.length-1]);
        }
        posPesos = this.libro.addRow(pesos,RowType.CONTENT);

        //valores para armar d y 1/d para matriz de discordancia
        float mayorD=0;
        float menorD=0;
        
       

        String primeraFila[] = posValores.get(0);
        String ultimaFila[] = posValores.get(posValores.size()-1);
        List mejor = new ArrayList();
        List rango = new ArrayList();
        List d = new ArrayList();
        List sobreD= new ArrayList();
        mejor.add("Mejor");
        rango.add("Rango");
        d.add("d");
        sobreD.add("1/d");

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
        String max="MAX(";
        String min="MIN(";
        for (int i = 0; i < posValores.size(); i++) {

            valores.clear();
            String[] fila = posValores.get(i);
            valores.add("=T("+fila[0]+")");
            for (int j = 1; j < fila.length; j++) {
                //valores.add("=ABS("+posMejor[j]+"-"+fila[j]+")/("+posRango[j]+")");

                //verifico cual es el mayor y menor numero de la matriz Normalizada para armar d y 1/d
               
//                if(menorD>(Float.parseFloat(fila[j])/Float.parseFloat(posTotales[j])))
//                  menorD= Float.parseFloat(fila[j])/Float.parseFloat(posTotales[j]);
//                if(mayorD<(Float.parseFloat(fila[j])/Float.parseFloat(posTotales[j])))
//                  mayorD= Float.parseFloat(fila[j])/Float.parseFloat(posTotales[j]);
                
//                System.out.println(mayorD);
                valores.add("="+fila[j]+"/"+posTotales[j]);
                max+=fila[j]+"/"+posTotales[j];
                       if(i<posValores.size()-1 || j<fila.length-1)
                        {
                            max += ",";
                        }
                min+=fila[j]+"/"+posTotales[j];
                        if(i<posValores.size()-1 || j<fila.length-1)
                        {
                            min += ",";
                        }
              
            }
            posValores2.add(this.libro.addRow(valores,RowType.CONTENT));
        }
        this.libro.addEmptyRow();

       max+=")";
       min+=")";
       System.out.println(max);
       System.out.println(min);
       max+="-"+min;
       d.add("="+max);
       sobreD.add("=1/("+max+")");
      // sobreD.add(posValores2.get(1).toString());
       String dd[]= this.libro.addRow(d, RowType.CONTENT);
        String sobredd[]= this.libro.addRow(sobreD, RowType.CONTENT);
        this.libro.addEmptyRow();
        //Indices de concordancia
//        valores.clear();
//        valores.add("Indices de concordancia");
//        this.libro.addRow(valores, RowType.TITLE);
//        this.libro.addEmptyRow();
//
//        valores.clear();
//        valores.add("Alternativas");
//        for(int i = 0; i<super.posValores.size(); i++){
//            valores.add("=T("+super.posValores.get(i)[0]+")");
//        }
//        this.libro.addRow(valores,RowType.HEADER);
//
//        posValores.clear();
//        for (int i = 0; i < posValores2.size(); i++) {
//            valores.clear();
//            String[] fila = posValores2.get(i);
//            valores.add("=T("+fila[0]+")");
//            for (int j = 0; j < fila.length; j++) {
//                if(i!=j){
//                    String filaComparacion[]=posValores2.get(j);
//                    StringBuilder cell = new StringBuilder("=SUM(");
//
//                    for(int k = 0; k<filaComparacion.length; k++){
//
//                        cell.append("IF("+fila[k]+">"+filaComparacion[k]+","+super.posPesos[k]+",0)");
//                        if(filaComparacion.length-1!=k){
//                            cell.append(",");
//                        }
//                    }
//                    cell.append(")");
//                    System.out.println(cell.toString());
//                    valores.add(cell.toString());
//                }else{
//                    valores.add(" X ");
//                }
//            }
//            posValores.add(this.libro.addRow(valores,RowType.CONTENT));
//        }
//        this.libro.addEmptyRows(2);


//Indices de concordancia
        valores.clear();
        valores.add("Matriz de Concordancia");
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
            for (int j = 0; j < this.problema.getAlternativaList().size(); j++) {
                if(i!=j){
                    String filaComparacion[]=posValores2.get(j);
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

   //Indices de Discordancia
        valores.clear();
        valores.add("Matriz de Discordancia");
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
            for (int j = 0; j < this.problema.getAlternativaList().size(); j++) {
                if(i!=j){
                    String filaComparacion[]=posValores2.get(j);
                    StringBuilder cell = new StringBuilder("=MAX(");

                    for(int k = 1; k<filaComparacion.length; k++){

                        cell.append("IF("+fila[k]+"<"+filaComparacion[k]+","+"ABS("+fila[k]+"-"+filaComparacion[k]+")"+",0)");
                        if(filaComparacion.length-1!=k){
                            cell.append(",");
                        }
                    }
                    cell.append(")"+"*(1/("+max+"))");
                    System.out.println(cell.toString());
                    valores.add(cell.toString());
                }else{
                    valores.add(" X ");
                }
            }
            posValores.add(this.libro.addRow(valores,RowType.CONTENT));
        }
        this.libro.addEmptyRows(2);

        //Indices de Superacion

        String C=problema.getC(); //limite de superacion de concordancia
        String D=problema.getD(); //limite de superacion de discordancia

        valores.clear();
        valores.add("Matriz de Superación");
        this.libro.addRow(valores, RowType.TITLE);
        this.libro.addEmptyRow();
    
        valores.clear();
         valores.add("Lim. de Concord.:"+C+" - Lim. de Discord.:"+D);
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
            for (int j = 0; j < this.problema.getAlternativaList().size(); j++) {
                if(i!=j){
                    String filaComparacion[]=posValores2.get(j);
                    StringBuilder cell = new StringBuilder("=IF(AND(");

                    cell.append("SUM(");
                     for(int k = 1; k<filaComparacion.length; k++){

                        cell.append("IF("+fila[k]+">"+filaComparacion[k]+","+super.posPesos[k]+",0)");
                        if(filaComparacion.length-1!=k){
                            cell.append(",");
                        }
                    }
                    cell.append(")>"+C+",(");

                    cell.append("MAX(");
                    for(int k = 1; k<filaComparacion.length; k++){

                        cell.append("IF("+fila[k]+"<"+filaComparacion[k]+","+"ABS("+fila[k]+"-"+filaComparacion[k]+")"+",0)");
                        if(filaComparacion.length-1!=k){
                            cell.append(",");
                        }
                    }
                    cell.append(")"+"*(1/("+max+"))");

                    cell.append(")<"+D+"),1,0)");
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
