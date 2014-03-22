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
public class normalizarMatriz extends javax.swing.JFrame {

    /**
     * Creates new form normalizarMatriz
     */
    private ArrayList<Criterio>criterios;
    private ArrayList<Alternativa>alternativas;
    private DefaultTableModel modeloMatrizDatos;
    private DefaultTableModel modeloMatrizNormalizada;
    private DefaultTableModel modeloMatrizNormalizadaPonderada;
    private DefaultTableModel modeloIdeal;
    private DefaultTableModel modeloAntiIdeal;
    private DefaultTableModel modeloIndice;
    private double matrizDatos [][];
    private double matrizNormal [][];
    private double matrizNormalizada [][];
    private double matrizNormalizadaPonderada [][];
    private double matrizIdeal[][];
    private double matrizAntiIdeal[][];
    private double resultado[];
    public normalizarMatriz(ArrayList<Criterio>listaCriterios,ArrayList<Alternativa>listaAlternativa,double matriz[][]) {
        initComponents();
        criterios=listaCriterios;
        alternativas=listaAlternativa;
        modeloMatrizDatos=(DefaultTableModel)tablaMatrizDatos.getModel();
        modeloMatrizNormalizada=(DefaultTableModel)tablaMatrizNormalizada.getModel();
        modeloMatrizNormalizadaPonderada=(DefaultTableModel)tablaMatrizNormalizadaPonderada.getModel();
        modeloIdeal=(DefaultTableModel)tablaIdeal.getModel();
        modeloAntiIdeal=(DefaultTableModel)tablaAntiIdeal.getModel();
        modeloIndice=(DefaultTableModel)tablaIndice.getModel();
        matrizDatos=matriz;        
        matrizNormal=new double[alternativas.size()+1][criterios.size()];
        matrizNormalizada=new double[alternativas.size()][criterios.size()];
        matrizNormalizadaPonderada=new double[alternativas.size()+2][criterios.size()];
        matrizIdeal=new double[alternativas.size()][criterios.size()+2];
        matrizAntiIdeal=new double[alternativas.size()][criterios.size()+2];
        resultado=new double[alternativas.size()];
        armarTabla();
        armarMatrizNormal();
        armarMatrizNormalizada();
        armarMatrizNormalizadaPonderada();
        calculoDistanciasIdeal();
        calculoIndice();
        
    }
    private void armarTabla()
    {
        modeloMatrizDatos.setRowCount(alternativas.size());
        for(int i=0;i<criterios.size();i++)
        {
            Criterio c=criterios.get(i);
            modeloMatrizDatos.addColumn(c.getNombre());
        }
        
        for(int i=0;i<matrizDatos.length;i++)
           for(int j=0;j<matrizDatos[i].length;j++)           
           {
               double valor=matrizDatos[i][j];
               valor=Math.pow(valor,2);
               matrizNormal[i][j]=valor;
               modeloMatrizDatos.setValueAt(valor, i, j);               
           }
             
    }
    private void armarMatrizNormal()
    {
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

        
    }
   private void armarMatrizNormalizada()
   {
       modeloMatrizNormalizada.setRowCount(alternativas.size());
        for(int i=0;i<criterios.size();i++)
        {
            Criterio c=criterios.get(i);
            modeloMatrizNormalizada.addColumn(c.getNombre());
        }
       
       int b=matrizNormal.length - 1;
       for (int i=0;i<matrizNormalizada.length;i++ )
           for(int j=0;j<matrizNormalizada[i].length;j++)
           {
               double valor = matrizDatos[i][j]/ matrizNormal[b][j];
               matrizNormalizada[i][j]=valor;
               modeloMatrizNormalizada.setValueAt(valor, i, j);
           }
       
              

   }
   private void armarMatrizNormalizadaPonderada()
   {
       modeloMatrizNormalizadaPonderada.setRowCount(alternativas.size());
        for(int i=0;i<criterios.size();i++)
        {
            Criterio c=criterios.get(i);
            modeloMatrizNormalizadaPonderada.addColumn(c.getNombre());
        }
        

       for (int i=0;i<matrizNormalizada.length;i++ )
           for(int j=0;j<matrizNormalizada[i].length;j++)
            {
              double valor=(criterios.get(j).getPeso()*matrizNormalizada[i][j]);
              matrizNormalizadaPonderada[i][j]=valor;
              modeloMatrizNormalizadaPonderada.setValueAt(valor, i, j);
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
        modeloMatrizNormalizadaPonderada.addRow(maximo);
        modeloMatrizNormalizadaPonderada.addRow(minimo);           
        
   }
   
   private void calculoDistanciasIdeal()
   {
       modeloIdeal.setRowCount(alternativas.size());
        for(int i=0;i<criterios.size();i++)
        {
            Criterio c=criterios.get(i);
            modeloIdeal.addColumn(c.getNombre());
        }
        modeloIdeal.addColumn("Suma");
        modeloIdeal.addColumn("S+");
        
        modeloAntiIdeal.setRowCount(alternativas.size());
        for(int i=0;i<criterios.size();i++)
        {
            Criterio c=criterios.get(i);
            modeloAntiIdeal.addColumn(c.getNombre());
        }
        modeloAntiIdeal.addColumn("Suma");
        modeloAntiIdeal.addColumn("S-");
        
        int b=matrizNormalizadaPonderada.length - 2;
        int c=matrizNormalizadaPonderada.length -1;
        for (int i=0;i<b;i++ )
            for(int j=0;j<matrizNormalizadaPonderada[i].length;j++)
            {
                double abs=Math.abs(matrizNormalizadaPonderada[i][j]-matrizNormalizadaPonderada[b][j]);
                double valor=Math.pow(abs,2);
                matrizIdeal[i][j]=valor;
                modeloIdeal.setValueAt(valor, i, j);
                abs=Math.abs(matrizNormalizadaPonderada[i][j]-matrizNormalizadaPonderada[c][j]);
                valor=Math.pow(abs,2);
                matrizAntiIdeal[i][j]=valor;
                modeloAntiIdeal.setValueAt(valor, i, j);
            }
   

        for(int i=0;i<matrizIdeal.length;i++)
        {
            double suma=0;
            double sumas=0;
            for(int j=0;j<matrizIdeal[i].length-2;j++)
            {
                suma+=matrizIdeal[i][j];
                sumas+=matrizAntiIdeal[i][j];
            }
            matrizIdeal[i][matrizIdeal[i].length-2]=suma;
            matrizAntiIdeal[i][matrizAntiIdeal[i].length-2]=sumas;
            modeloIdeal.setValueAt(suma, i,matrizIdeal[i].length-2);
            modeloAntiIdeal.setValueAt(sumas, i,matrizAntiIdeal[i].length-2);
            double valor=Math.pow(suma, 0.5);
            matrizIdeal[i][matrizIdeal[i].length-1]=valor;
            modeloIdeal.setValueAt(valor, i, matrizIdeal[i].length-1);
            valor=Math.pow(sumas, 0.5);
            matrizAntiIdeal[i][matrizAntiIdeal[i].length-1]=valor;
            modeloAntiIdeal.setValueAt(valor, i, matrizAntiIdeal[i].length-1);
        }
   }
   
   private void calculoIndice()
   {
       modeloIndice.setRowCount(alternativas.size());
       for(int i=0;i<alternativas.size();i++)
       {
           Alternativa a=alternativas.get(i);
           modeloIndice.setValueAt(a.getNombre(), i, 0);
       }
       for(int i=0;i<matrizIdeal.length;i++)
       {
           double valor=matrizAntiIdeal[i][matrizAntiIdeal[i].length-1]/(matrizIdeal[i][matrizIdeal[i].length-1] + matrizAntiIdeal[i][matrizAntiIdeal[i].length-1]);
           resultado[i]=valor;
           modeloIndice.setValueAt(valor, i, 1);
       }
       
   }
   public void mostrarResultado()
   {
       this.btnSiguienteActionPerformed(null);
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMatrizDatos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaMatrizNormalizada = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaMatrizNormalizadaPonderada = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaIdeal = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaAntiIdeal = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaIndice = new javax.swing.JTable();
        btnSiguiente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Normalización Matriz"));

        tablaMatrizDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaMatrizDatos);

        tablaMatrizNormalizada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaMatrizNormalizada);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Matriz Normalizada");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(641, 641, 641))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Matriz normalizada y ponderada"));

        tablaMatrizNormalizadaPonderada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaMatrizNormalizadaPonderada);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Distancias al Ideal y Anti-Ideal"));

        tablaIdeal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tablaIdeal);

        tablaAntiIdeal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tablaAntiIdeal);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Indice de Simililaridad"));

        tablaIndice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "C*"
            }
        ));
        jScrollPane6.setViewportView(tablaIndice);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(53, 53, 53)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(640, 640, 640)
                        .addComponent(btnSiguiente)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(62, 62, 62)
                .addComponent(btnSiguiente)
                .addContainerGap(278, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        ventanaFinal ven=new ventanaFinal(alternativas,resultado);
        ven.setVisible(true);
    }//GEN-LAST:event_btnSiguienteActionPerformed

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable tablaAntiIdeal;
    private javax.swing.JTable tablaIdeal;
    private javax.swing.JTable tablaIndice;
    private javax.swing.JTable tablaMatrizDatos;
    private javax.swing.JTable tablaMatrizNormalizada;
    private javax.swing.JTable tablaMatrizNormalizadaPonderada;
    // End of variables declaration//GEN-END:variables
}
