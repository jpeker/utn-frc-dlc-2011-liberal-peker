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
public class ManejadorCalculos implements iCalculador{

    
    @Override
    public Resultado armarTabla(ArrayList<Criterio> listaCriterios, ArrayList<Alternativa> listaAlternativa, double[][] matriz, DefaultTableModel modelo) {
        double matrizNormal [][]=new double[listaAlternativa.size()+1][listaCriterios.size()];
        modelo.setRowCount(listaAlternativa.size());
        for(int i=0;i<listaCriterios.size();i++)
        {
            Criterio c=listaCriterios.get(i);
            modelo.addColumn(c.getNombre());
        }
        
        for(int i=0;i<matriz.length;i++)
           for(int j=0;j<matriz[i].length;j++)           
           {
               double valor=matriz[i][j];
               valor=Math.pow(valor,2);
               matrizNormal[i][j]=valor;
               modelo.setValueAt(valor, i, j);               
           }
        
        int b=matrizNormal.length - 1;
        int a=matrizNormal[b].length;
        for(int i=0;i<a;i++)
        {   double suma=0;
            for (int j=0;j<b ;j++)
            {
              suma+=matrizNormal[j][i];
            }
            double pot=Math.pow(suma,0.5);
            matrizNormal[b][i]=pot;
        }
        
        Resultado res=new Resultado(modelo,matrizNormal);
        return res;
    }

    @Override
    public Resultado armarTablaNormalizada(ArrayList<Criterio> listaCriterios, ArrayList<Alternativa> listaAlternativa, double matrizDatos[][], double matrizNormal[][], DefaultTableModel modelo) {
        double matrizNormalizada [][]=new double[listaAlternativa.size()][listaCriterios.size()];
        modelo.setRowCount(listaAlternativa.size());
        for(int i=0;i<listaCriterios.size();i++)
        {
            Criterio c=listaCriterios.get(i);
            modelo.addColumn(c.getNombre());
        }
       
       int b=matrizNormal.length - 1;
       for (int i=0;i<matrizNormalizada.length;i++ )
           for(int j=0;j<matrizNormalizada[i].length;j++)
           {
               double valor = matrizDatos[i][j]/ matrizNormal[b][j];
               matrizNormalizada[i][j]=valor;
               modelo.setValueAt(valor, i, j);
           }
       
       Resultado res=new Resultado(modelo,matrizNormalizada);
       return res;
    }

    @Override
    public Resultado armarTablaNormalizadaPonderada(ArrayList<Criterio> listaCriterios, ArrayList<Alternativa> listaAlternativa, double[][] matriz, DefaultTableModel modelo) {
        double matrizNormalizadaPonderada[][]=new double[listaAlternativa.size()+2][listaCriterios.size()];
        modelo.setRowCount(listaAlternativa.size());
        for(int i=0;i<listaCriterios.size();i++)
        {
            Criterio c=listaCriterios.get(i);
            modelo.addColumn(c.getNombre());
        }
        

       for (int i=0;i<matriz.length;i++ )
           for(int j=0;j<matriz[i].length;j++)
            {
              double valor=(listaCriterios.get(j).getPeso()*matriz[i][j]);
              matrizNormalizadaPonderada[i][j]=valor;
              modelo.setValueAt(valor, i, j);
            }
       
       int b=matrizNormalizadaPonderada.length - 2;
       int a=matrizNormalizadaPonderada[b].length;
       int c=matrizNormalizadaPonderada.length -1;
        for(int i=0;i<a;i++)
        {   double max=0;
            double min=1;
            for (int j=0;j<b ;j++)
            {  max=Math.max(max, matrizNormalizadaPonderada[j][i]);
               min=Math.min(min, matrizNormalizadaPonderada[j][i]);
            }
            matrizNormalizadaPonderada[b][i]=max;
            matrizNormalizadaPonderada[c][i]=min;
         }
      
        Object maximo[]=new Object[a];
        Object minimo[]=new Object[a];
        for(int i=0;i<a;i++)
        {
            maximo[i]=matrizNormalizadaPonderada[b][i];
            minimo[i]=matrizNormalizadaPonderada[c][i];
        }
        modelo.addRow(maximo);
        modelo.addRow(minimo);
        
        Resultado res=new Resultado(modelo,matrizNormalizadaPonderada);
        return res;
    }
    
    @Override
    public Resultado calculoDistanciaIdeal(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matriz[][],DefaultTableModel modelo,int p){
        double matrizIdeal[][]=new double[listaAlternativa.size()][listaCriterios.size()+2];
        modelo.setRowCount(listaAlternativa.size());
        for(int i=0;i<listaCriterios.size();i++)
        {
            Criterio c=listaCriterios.get(i);
            modelo.addColumn(c.getNombre());
        }
        modelo.addColumn("Suma");
        modelo.addColumn("S+");
                
        
        int b=matriz.length - 2;
        int c=matriz.length -1;
        for (int i=0;i<b;i++ )
            for(int j=0;j<matriz[i].length;j++)
            {
                double abs=Math.abs(matriz[i][j]-matriz[b][j]);
                double valor=Math.pow(abs,p);
                matrizIdeal[i][j]=valor;
                modelo.setValueAt(valor, i, j);
            }
   

        for(int i=0;i<matrizIdeal.length;i++)
        {
            double suma=0;            
            for(int j=0;j<matrizIdeal[i].length-2;j++)
            {
                suma+=matrizIdeal[i][j];               
            }
            matrizIdeal[i][matrizIdeal[i].length-2]=suma;            
            modelo.setValueAt(suma, i,matrizIdeal[i].length-2);
            double para=0.5;
            if(p==1)
                para=1;
            double valor=Math.pow(suma, para);
            matrizIdeal[i][matrizIdeal[i].length-1]=valor;
            modelo.setValueAt(valor, i, matrizIdeal[i].length-1);            
        }
        Resultado res=new Resultado(modelo,matrizIdeal);
        return res;
    }
    
    @Override        
    public Resultado calculoDistanciaAntiIdeal(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matriz[][],DefaultTableModel modelo,int p){
        double matrizAntiIdeal[][]=new double[listaAlternativa.size()][listaCriterios.size()+2];      
        
        modelo.setRowCount(listaAlternativa.size());
        for(int i=0;i<listaCriterios.size();i++)
        {
            Criterio c=listaCriterios.get(i);
            modelo.addColumn(c.getNombre());
        }
        modelo.addColumn("Suma");
        modelo.addColumn("S-");
        
        int b=matriz.length - 2;
        int c=matriz.length -1;
        for (int i=0;i<b;i++ )
            for(int j=0;j<matriz[i].length;j++)
            {
                double abs=Math.abs(matriz[i][j]-matriz[c][j]);
                double valor=Math.pow(abs,p);
                matrizAntiIdeal[i][j]=valor;
                modelo.setValueAt(valor, i, j);
            }
   

        for(int i=0;i<matrizAntiIdeal.length;i++)
        {
            double sumas=0;
            for(int j=0;j<matrizAntiIdeal[i].length-2;j++)
            {
                sumas+=matrizAntiIdeal[i][j];
            }
            matrizAntiIdeal[i][matrizAntiIdeal[i].length-2]=sumas;
            modelo.setValueAt(sumas, i,matrizAntiIdeal[i].length-2);
            double para=0.5;
            if(p==1)
                para=1;
            double valor=Math.pow(sumas, para);           
            matrizAntiIdeal[i][matrizAntiIdeal[i].length-1]=valor;
            modelo.setValueAt(valor, i, matrizAntiIdeal[i].length-1);
        }

        Resultado res=new Resultado(modelo,matrizAntiIdeal);
        return res;
    
    }
    
    @Override  
    public Resultado calculoIndice(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matrizIdeal[][],double matrizAntiIdeal[][],DefaultTableModel modelo){
       double resultado[][]=new double[listaAlternativa.size()][2];
       modelo.setRowCount(listaAlternativa.size());
       for(int i=0;i<listaAlternativa.size();i++)
       {
           Alternativa a=listaAlternativa.get(i);
           resultado[i][0]=(i+1);
           modelo.setValueAt(a.getNombre(), i, 0);
       }
       for(int i=0;i<matrizIdeal.length;i++)
       {
           double valor=matrizAntiIdeal[i][matrizAntiIdeal[i].length-1]/(matrizIdeal[i][matrizIdeal[i].length-1] + matrizAntiIdeal[i][matrizAntiIdeal[i].length-1]);
           resultado[i][1]=valor;
           modelo.setValueAt(valor, i, 1);
       }
       
       Resultado res=new Resultado(modelo,resultado);
       return res;
    }


    
    
    


}