/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BoundaryDeleteModifiy.java
 *
 * Created on 10/05/2011, 19:31:02
 */

package interfaz;
import persistencia.*;
import datos.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Peker
 */
public class BoundaryDeleteModifiy extends javax.swing.JFrame {
       private   OpenHashFile m1;
       private   Alumno   alu;
       private   Boolean bandera;
    /** Creates new form BoundaryDeleteModifiy */
    public BoundaryDeleteModifiy( OpenHashFile m,boolean b) {
        initComponents();
        m1=m;
        alu=new Alumno();
        bandera=b;
       if(bandera)this.btnBuscar.setText("Modificar");
       else
           this.btnBuscar.setText("Eliminar");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtLegajo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lbl1 = new javax.swing.JLabel();
        txtPromedio = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnModificar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtStatus = new javax.swing.JTextField();
        lblPromedio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Legajo");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtPromedio.setEditable(false);

        txtNombre.setEditable(false);

        btnModificar.setText("Modificar");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jButton2.setText("Salir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtStatus.setEditable(false);

        lblPromedio.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(38, 38, 38)
                        .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnBuscar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(btnModificar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblNombre)
                                .addComponent(lblPromedio, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPromedio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                            .addGap(174, 174, 174))
                        .addComponent(txtStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtPromedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPromedio)))
                .addGap(9, 9, 9)
                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnModificar)
                    .addComponent(jButton2))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        this.txtLegajo.setEditable(false);
        this.btnBuscar.setEnabled(false);
        if(this.txtLegajo.getText()!=""){
            alu.setLegajo(Integer.parseInt(this.txtLegajo.getText()));
            if(bandera)
            {
              alu =(Alumno)m1.find(alu);
               if(alu!=null)
               {
               this.btnModificar.setEnabled(true);
               this.lblNombre.setText("Nombre");
               this.lblPromedio.setText("Promedio");
               this.txtNombre.setEditable(true);
               this.txtPromedio.setEditable(true);
               this.txtNombre.setText(alu.getNombre());
               this.txtPromedio.setText(String.valueOf(alu.getPromedio()));
                this.txtStatus.setText("Alumno econtrado si desea modifcar haz clik en Modificar");
               }
            else{
              this.txtStatus.setText("No se encontro el Alumno");
               this.btnBuscar.setEnabled(true);
            }
            }
            else{
             if(m1.remove(alu))
             {
                 this.txtLegajo.setEditable(true);
                 this.txtStatus.setText("Baja Realizada");
             }
                else
                {
                    this.btnBuscar.setEnabled(true);
                    this.txtStatus.setText("No se encontro el Alumno");
                }
            }
        }
        else
             JOptionPane.showMessageDialog(null, "Ingrese el legajo ", "Error ", 0);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
       this.txtLegajo.setEditable(true);
        this.btnBuscar.setEnabled(true);
       
        if(this.txtNombre.getText()!=""&&this.txtPromedio.getText()!="")
       {
         this.btnModificar.setEnabled(false);
       String nomb=this.txtNombre.getText();
       float prom=Float.parseFloat(this.txtPromedio.getText());
       alu.setNombre(nomb);
       alu.setPromedio(prom);
        if ( m1.update( alu ) )  this.txtStatus.setText("Alumno Modificado");
       this.btnModificar.setEnabled(true);
               this.lblNombre.setText("");
               this.lblPromedio.setText("");
               this.txtNombre.setEditable(false);
               this.txtPromedio.setEditable(false);
               this.txtNombre.setText("");
               this.txtPromedio.setText("");
        }
        else
             JOptionPane.showMessageDialog(null, "Ingrese todos los datos del alumno ", "Error ", 0);
    }//GEN-LAST:event_btnModificarActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPromedio;
    private javax.swing.JTextField txtLegajo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPromedio;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables

}
