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
 * @author Kapica Liberal 
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
        List matrix = new ArrayList(); //matriz para solucion final
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
                    cell.append(")>="+C+",(");//analisis de concordancia >=

                    cell.append("MAX(");
                    for(int k = 1; k<filaComparacion.length; k++){

                        cell.append("IF("+fila[k]+"<"+filaComparacion[k]+","+"ABS("+fila[k]+"-"+filaComparacion[k]+")"+",0)");
                        if(filaComparacion.length-1!=k){
                            cell.append(",");
                        }
                    }
                    cell.append(")"+"*(1/("+max+"))");

                    cell.append(")<="+D+"),1,0)"); //analisis de discordancia <=

                    System.out.println(cell.toString());
                    valores.add(cell.toString());

                }else{
                    valores.add("X ");
                }
            }
            for(int h=1;h<valores.size();h++){
            
                matrix.add(valores.get(h));
            }
            posValores.add(this.libro.addRow(valores,RowType.CONTENT));
            
        }

                this.libro.addEmptyRows(2);
      List valorFinal = new ArrayList();

        //valores.add("Alternativas");
        for(int i = 0; i<super.posValores.size(); i++){
            valorFinal.add("=T("+super.posValores.get(i)[0]+")");
        }
        this.libro.addRow(valorFinal,RowType.HEADER);
      
      valores.clear();
      int l= super.posValores.size();
      int g=0;
      int tot=0;
      //valores.clear();
      System.out.println(l);
      System.out.println(g);
      
      
      String eliminador="";
      String eliminador2="";
    
      do{

       StringBuilder cell = new StringBuilder("=SUM(");
       System.out.println(cell.toString());
       for(int h=0;h<matrix.size();h++){
         if(g==h)
         {  
          eliminador=matrix.get(g).toString();
         
                for(int w=0;w<eliminador.length();w++){
                if(w!=0)
                  eliminador2+=eliminador.charAt(w);//elimini el 1er caracter sea X o =

                }

          cell.append(eliminador2 + ",");
          eliminador="";
          eliminador2="";
          System.out.println(cell.toString());
          g+=l;
          System.out.println(g);
         }
         
        }
       cell.append(")");
       System.out.println(g);
       System.out.println(cell.toString());
         valores.add(cell.toString());
         tot++;
         g=tot;
      }
       while(tot<l);

                posValores.add(this.libro.addRow(valores,RowType.CONTENT));


      this.libro.addRow(valores, 1048559, 0, RowType.CONTENT);
      eliminador="";
      eliminador2="";
      String abecedario="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      ArrayList <String[]> letraFinal= new ArrayList<String[]>();
           //  letraFinal.add(1,"hola"); add("1048560");

      //valores.clear();
      StringBuilder cell = new StringBuilder("=");
      
      for(int i=0;i<valores.size();i++)
      {
          cell.append("IF(AND(");
      boolean z=false;
          for(int j=0;j<valores.size();j++)
          { 
              if(i!=j)//no debo usar el mismo valor para comparar
             {
               char letra= abecedario.charAt(i);
               System.out.println(letra);
               char letra2= abecedario.charAt(j);
                System.out.println(letra);
                cell.append(letra+"1048560"); //cell.append(valores.get(i));
                cell.append(">"+letra2+"1048560"); //1048560
               System.out.println(cell.toString());
              
              if(j<valores.size()-1)//no poner la ultima ,
              {
                  cell.append(",");

              }
             
            }//cierre if

              if(i==valores.size()-1 && j == valores.size()-1)
                  {
                    cell.deleteCharAt(cell.length()-1);
                  }
           }//cierre j


          eliminador=valorFinal.get(i).toString();
                for(int w=0;w<eliminador.length();w++)
                {
                if(w!=0)
                  eliminador2+=eliminador.charAt(w);//elimini el 1er caracter sea X o =
                }

          cell.append("),"+eliminador2); //cell.append(valorFinal.get(i)+",");
          eliminador="";
          eliminador2=""; 
          if(i<valores.size()-1)
              {
                cell.append(",");
              }
         }//fin i

      for(int i=0;i<valores.size();i++)
     {
         cell.append(")");
         
     }
      System.out.print(cell.toString());
//
//      

//      valores.clear();
//      valores.add("La mejor Alternativa es: ");
//
//     this.libro.addRow(valores,85,RowType.TITLE);
//
      this.libro.addCell("La mejor Alternativa es: ");

      this.libro.addCell(cell.toString());

      valores.clear();
      valores.add("La mejor Alternativa es:");
     // valores.add(cell.toString());
      this.libro.addRow(valores,68,1,RowType.TITLE);

      valores.clear();
       valores.add(cell.toString());
       this.libro.addRow(valores,69,1,RowType.TITLE);
      this.libro.addCell(cell.toString());
      //this.libro.addRow(valores,RowType.HEADER);

     // this.libro.addRow(valores, 1048574, 1, RowType.CONTENT);



//        for(int e=0;e<matrix.size();e++)
//        {
//
//          for (int j = 0; j < matrix.get(e); j++) {
//                if(e!=j)
//                {
//
//                }}
//        }




    }

    @Override
    protected void header() {
        
    }
    
}
