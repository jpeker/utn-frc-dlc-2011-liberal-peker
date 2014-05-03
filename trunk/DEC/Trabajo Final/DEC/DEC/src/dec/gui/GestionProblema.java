/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GestionProblema.java
 *
 * 
 */

package dec.gui;

import dec.DECApp;
import dec.controller.ProblemaController;
import dec.dominio.Alternativa;
import dec.dominio.Criterio;
import dec.dominio.Problema;
import dec.excel.ElectreToExcel;
import dec.excel.TopsisToExcel;
import dec.gui.util.DateConverter;
import dec.gui.util.RequiredValidator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.jdesktop.application.Action;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Kapica Liberal Ramirez
 */
public class GestionProblema extends javax.swing.JPanel {

    private Problema problema;
    private MiTableModel modelo;

    /** Creates new form GestionProblema */
    public GestionProblema(final Problema problema) {
        this.problema = problema;       
        initComponents();
        this.txtNombreProblema.setText(this.problema.getNombre());
        this.txtAreaDescripcionProblema.setText(this.problema.getDescripcion());
      
        this.txtAutorProblema.setText(this.problema.getAutor());

        //seteo a String de la Fecha (parseo)
        Date date = this.problema.getFecha();
        DateFormat fechaformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.txtFechaProblema.setText(fechaformat.format(date));

        this.modelo = (MiTableModel) this.jTable1.getModel();
        this.jTable1.getTableHeader().setReorderingAllowed(false);
    }

    private void init(){       
        this.jTable1.setModel(new MiTableModel(this.problema));
        this.modelo = (MiTableModel) this.jTable1.getModel();
        this.jTable1.getTableHeader().setReorderingAllowed(false);
        
    }

    @Action
    public void addCriterio(){
        criterio.setMaximizacion(this.radioMax.isSelected());
        this.modelo.addCriterio(criterio);
        this.criterio = new Criterio();
        this.jTextField5.setText("");
        this.jTextField4.setText("");
        this.buttonGroup1.clearSelection();
    }
    @Action
    public void addAlternativa(){
        this.modelo.addAlternativa(alternativa);
        this.alternativa = new Alternativa();
        this.jTextField6.setText("");
    }
    @Action
    public void removeAlternativa(){
        this.problema.removeAlternativa(alternativaAux);
        this.init();
    }
    @Action
    public void removeCriterio() {
        this.problema.removeCriterio(criterioAux);
        this.init();
    }
    @Action
    public void editAlaternativa() {
        this.alternativaAux.setNombre(this.alternativaAux.getNombre());
        this.init();
    }
    @Action
    public void editCriterio() {
        this.criterioAux.setNombre(this.criterio.getNombre());
        this.criterioAux.setPeso(this.criterio.getPeso());
        this.criterioAux.setMaximizacion(this.radioMax.isSelected());
        this.init();
    }
   // @Action
   // public void detalleTopsis(){
   //     this.dialogTopsis.setVisible(true);
   // }
  //  @Action
  //  public void detalleElectre(){
   //     this.dialogElectre.setVisible(true);
  //  }
    
    @Action
    public void cerrar(){
        this.dialogElectre.dispose();        
        this.dialogTopsis.dispose();
    }

    @Action
    public void electre(){
        File file = this.getFile();
        if(file == null){
            return;
        }
        ElectreToExcel electreToExcel = new ElectreToExcel(this.problema,file);
        try {
            electreToExcel.excecute();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionProblema.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(GestionProblema.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    @Action
    public void topsis(){
        this.dialogTopsis.setVisible(true);
     //   if(!this.dialogTopsis.isActive())
       // {
            File file = this.getFile();
        if(file == null){
            return;
        }
        TopsisToExcel topsisToExcel = new TopsisToExcel(this.problema,file);
        try {
            topsisToExcel.excecute();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionProblema.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(GestionProblema.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
       // }
    }
    private File getFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(null);
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return true;
            }
            @Override
            public String getDescription() {
                return "Excel 2003";
            }
        });

        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return true;
            }
            @Override
            public String getDescription() {
                return "Excel 2007";
            }
        });
          int op = fileChooser.showSaveDialog(DECApp.getApplication().getMainFrame());
        //int op = fileChooser.showOpenDialog(DECApp.getApplication().getMainFrame());
        if(op == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            if(fileChooser.getFileFilter().getDescription().equalsIgnoreCase("Excel 2003")){
                file = new File(file.getAbsolutePath()+".xls");
            }else if(fileChooser.getFileFilter().getDescription().equalsIgnoreCase("Excel 2007")){
                file = new File(file.getAbsolutePath()+".xlsx");
            }
            return file;
        }else{
            return null;
        }
    }

    @Action
    public void grabar(){
        this.problema = this.modelo.getProblema();
        try {
            ProblemaController.getInstance().saveProblema(problema,problema.getFile());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionProblema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionProblema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Alternativa getAlternativa() {
        return alternativa;
    }
    public void setAlternativa(Alternativa alternativa) {
        this.alternativa = alternativa;
    }
    public Criterio getCriterio() {
        return criterio;
    }
    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }
    public Problema getProblema() {
        return problema;
    }
    public void setProblema(Problema problema) {
        this.problema = problema;
    }    


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        alternativa = new dec.dominio.Alternativa();
        criterio = new dec.dominio.Criterio();
        buttonGroup1 = new javax.swing.ButtonGroup();
        dialogTopsis = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        dialogElectre = new javax.swing.JDialog();
        alternativaAux = new dec.dominio.Alternativa();
        criterioAux = new dec.dominio.Criterio();
        nombre = new javax.swing.JLabel();
        descripcion = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        autor = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        addAlternativa1 = new javax.swing.JButton();
        btnEditCriterio = new javax.swing.JButton();
        btnDeleteCriterio = new javax.swing.JButton();
        radioMax = new javax.swing.JRadioButton();
        radioMin = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnEditAlternativa = new javax.swing.JButton();
        btnDeleteAlternativa = new javax.swing.JButton();
        addAlternativa = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProblema = new javax.swing.JTextField();
        txtFechaProblema = new javax.swing.JTextField();
        txtAutorProblema = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaDescripcionProblema = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        dialogTopsis.setBounds(new java.awt.Rectangle(400, 250, 200, 112));
        dialogTopsis.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dialogTopsis.setName("dialogTopsis"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dec.DECApp.class).getContext().getResourceMap(GestionProblema.class);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextField1.setName("jTextField1"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${problema.p}"), jTextField1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(dec.DECApp.class).getContext().getActionMap(GestionProblema.class, this);
        jButton9.setAction(actionMap.get("cerrar")); // NOI18N
        jButton9.setText(resourceMap.getString("jButton9.text")); // NOI18N
        jButton9.setName("jButton9"); // NOI18N

        javax.swing.GroupLayout dialogTopsisLayout = new javax.swing.GroupLayout(dialogTopsis.getContentPane());
        dialogTopsis.getContentPane().setLayout(dialogTopsisLayout);
        dialogTopsisLayout.setHorizontalGroup(
            dialogTopsisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTopsisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogTopsisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogTopsisLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(10, 10, 10)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        dialogTopsisLayout.setVerticalGroup(
            dialogTopsisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogTopsisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogTopsisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogElectre.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        dialogElectre.setName("dialogElectre"); // NOI18N

        javax.swing.GroupLayout dialogElectreLayout = new javax.swing.GroupLayout(dialogElectre.getContentPane());
        dialogElectre.getContentPane().setLayout(dialogElectreLayout);
        dialogElectreLayout.setHorizontalGroup(
            dialogElectreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        dialogElectreLayout.setVerticalGroup(
            dialogElectreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("Form.border.title"))); // NOI18N
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(375, 375));
        setMinimumSize(new java.awt.Dimension(0, 0));
        setName("Form"); // NOI18N
        setPreferredSize(new java.awt.Dimension(375, 375));

        nombre.setFont(resourceMap.getFont("nombre.font")); // NOI18N
        nombre.setName("nombre"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${problema.nombre}"), nombre, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        descripcion.setFont(resourceMap.getFont("descripcion.font")); // NOI18N
        descripcion.setName("descripcion"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${problema.descripcion}"), descripcion, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        fecha.setFont(resourceMap.getFont("descripcion.font")); // NOI18N
        fecha.setName("fecha"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${problema.fecha}"), fecha, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new DateConverter());
        bindingGroup.addBinding(binding);

        autor.setFont(resourceMap.getFont("autor.font")); // NOI18N
        autor.setName("autor"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${problema.autor}"), autor, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new MiTableModel(this.problema) );
        jTable1.setName("jTable1"); // NOI18N
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jTextField5.setName("jTextField5"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${criterio.nombre}"), jTextField5, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setValidator(new RequiredValidator());
        bindingGroup.addBinding(binding);

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jTextField4.setName("jTextField4"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${criterio.peso}"), jTextField4, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        addAlternativa1.setAction(actionMap.get("addCriterio")); // NOI18N
        addAlternativa1.setIcon(resourceMap.getIcon("addAlternativa1.icon")); // NOI18N
        addAlternativa1.setText(resourceMap.getString("addAlternativa1.text")); // NOI18N
        addAlternativa1.setName("addAlternativa1"); // NOI18N

        btnEditCriterio.setAction(actionMap.get("editCriterio")); // NOI18N
        btnEditCriterio.setIcon(resourceMap.getIcon("btnEditCriterio.icon")); // NOI18N
        btnEditCriterio.setText(resourceMap.getString("btnEditCriterio.text")); // NOI18N
        btnEditCriterio.setName("btnEditCriterio"); // NOI18N

        btnDeleteCriterio.setAction(actionMap.get("removeCriterio")); // NOI18N
        btnDeleteCriterio.setIcon(resourceMap.getIcon("btnDeleteCriterio.icon")); // NOI18N
        btnDeleteCriterio.setText(resourceMap.getString("btnDeleteCriterio.text")); // NOI18N
        btnDeleteCriterio.setName("btnDeleteCriterio"); // NOI18N

        buttonGroup1.add(radioMax);
        radioMax.setText(resourceMap.getString("radioMax.text")); // NOI18N
        radioMax.setName("radioMax"); // NOI18N

        buttonGroup1.add(radioMin);
        radioMin.setText(resourceMap.getString("radioMin.text")); // NOI18N
        radioMin.setName("radioMin"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4)
                            .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)))
                    .addComponent(radioMax)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(addAlternativa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(radioMin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditCriterio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCriterio)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(radioMax)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioMin, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditCriterio)
                            .addComponent(addAlternativa1)))
                    .addComponent(btnDeleteCriterio))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jTextField6.setName("jTextField6"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${alternativa.nombre}"), jTextField6, org.jdesktop.beansbinding.BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST"));
        binding.setValidator(new RequiredValidator());
        bindingGroup.addBinding(binding);

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        btnEditAlternativa.setAction(actionMap.get("editAlaternativa")); // NOI18N
        btnEditAlternativa.setIcon(resourceMap.getIcon("btnEditAlternativa.icon")); // NOI18N
        btnEditAlternativa.setText(resourceMap.getString("btnEditAlternativa.text")); // NOI18N
        btnEditAlternativa.setName("btnEditAlternativa"); // NOI18N

        btnDeleteAlternativa.setAction(actionMap.get("removeAlternativa")); // NOI18N
        btnDeleteAlternativa.setIcon(resourceMap.getIcon("btnDeleteAlternativa.icon")); // NOI18N
        btnDeleteAlternativa.setText(resourceMap.getString("btnDeleteAlternativa.text")); // NOI18N
        btnDeleteAlternativa.setName("btnDeleteAlternativa"); // NOI18N

        addAlternativa.setAction(actionMap.get("addAlternativa")); // NOI18N
        addAlternativa.setIcon(resourceMap.getIcon("addAlternativa.icon")); // NOI18N
        addAlternativa.setText(resourceMap.getString("addAlternativa.text")); // NOI18N
        addAlternativa.setName("addAlternativa"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addGap(40, 40, 40)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addAlternativa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditAlternativa)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteAlternativa)
                .addContainerGap(165, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteAlternativa)
                    .addComponent(addAlternativa)
                    .addComponent(btnEditAlternativa))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setName("jPanel5"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        txtNombreProblema.setEditable(false);
        txtNombreProblema.setText(resourceMap.getString("txtNombreProblema.text")); // NOI18N
        txtNombreProblema.setName("txtNombreProblema"); // NOI18N

        txtFechaProblema.setEditable(false);
        txtFechaProblema.setText(resourceMap.getString("txtFechaProblema.text")); // NOI18N
        txtFechaProblema.setName("txtFechaProblema"); // NOI18N

        txtAutorProblema.setEditable(false);
        txtAutorProblema.setText(resourceMap.getString("txtAutorProblema.text")); // NOI18N
        txtAutorProblema.setName("txtAutorProblema"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        txtAreaDescripcionProblema.setColumns(20);
        txtAreaDescripcionProblema.setEditable(false);
        txtAreaDescripcionProblema.setRows(5);
        txtAreaDescripcionProblema.setName("txtAreaDescripcionProblema"); // NOI18N
        txtAreaDescripcionProblema.setOpaque(false);
        jScrollPane2.setViewportView(txtAreaDescripcionProblema);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(36, 36, 36)
                        .addComponent(txtNombreProblema, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(txtAutorProblema, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(43, 43, 43)
                        .addComponent(txtFechaProblema, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                .addGap(222, 222, 222))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombreProblema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFechaProblema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAutorProblema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        jButton3.setAction(actionMap.get("electre")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jButton1.setAction(actionMap.get("topsis")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setPreferredSize(new java.awt.Dimension(65, 23));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jButton1)
                            .addGap(127, 127, 127)))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jButton4.setAction(actionMap.get("grabar")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setName("jButton4"); // NOI18N

        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2097, 2097, 2097)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(descripcion)
                                .addGap(772, 772, 772)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fecha)
                                    .addComponent(autor)))
                            .addComponent(nombre))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(nombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(descripcion))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fecha)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(autor))))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel3.AccessibleContext.accessibleName")); // NOI18N
        jPanel3.getAccessibleContext().setAccessibleDescription(resourceMap.getString("jPanel3.AccessibleContext.accessibleDescription")); // NOI18N
        jPanel4.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel4.AccessibleContext.accessibleName")); // NOI18N

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int columnIndex = jTable1.getSelectedColumn();
        if(columnIndex == 0){
            int rowIndex = jTable1.getSelectedRow();
            this.criterio = new Criterio();
            this.alternativaAux = this.problema.getAlternativaList().get(rowIndex);
            this.alternativa = new Alternativa(this.alternativaAux.getNombre());
            this.btnDeleteAlternativa.setEnabled(true);
            this.btnEditAlternativa.setEnabled(true);
            this.btnDeleteCriterio.setEnabled(false);
            this.btnEditCriterio.setEnabled(false);
        }else{
            this.alternativa = new Alternativa();
            this.criterioAux = this.problema.getCriterioList().get(columnIndex-1);
            this.criterio = new Criterio(this.criterioAux.getNombre(), this.criterioAux.getPeso());
            this.criterio.setMaximizacion(this.criterioAux.isMaximizacion());
            if(this.criterio.isMaximizacion()){
                this.radioMax.setSelected(true);
                this.radioMin.setSelected(false);
            }else{
                this.radioMax.setSelected(false);
                this.radioMin.setSelected(true);
            }
            this.btnDeleteAlternativa.setEnabled(false);
            this.btnEditAlternativa.setEnabled(false);
            this.btnDeleteCriterio.setEnabled(true);
            this.btnEditCriterio.setEnabled(true);
        }
        bindingGroup.unbind();
        bindingGroup.bind();
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAlternativa;
    private javax.swing.JButton addAlternativa1;
    private dec.dominio.Alternativa alternativa;
    private dec.dominio.Alternativa alternativaAux;
    private javax.swing.JLabel autor;
    private javax.swing.JButton btnDeleteAlternativa;
    private javax.swing.JButton btnDeleteCriterio;
    private javax.swing.JButton btnEditAlternativa;
    private javax.swing.JButton btnEditCriterio;
    private javax.swing.ButtonGroup buttonGroup1;
    private dec.dominio.Criterio criterio;
    private dec.dominio.Criterio criterioAux;
    private javax.swing.JLabel descripcion;
    private javax.swing.JDialog dialogElectre;
    private javax.swing.JDialog dialogTopsis;
    private javax.swing.JLabel fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JLabel nombre;
    private javax.swing.JRadioButton radioMax;
    private javax.swing.JRadioButton radioMin;
    private javax.swing.JTextArea txtAreaDescripcionProblema;
    private javax.swing.JTextField txtAutorProblema;
    private javax.swing.JTextField txtFechaProblema;
    private javax.swing.JTextField txtNombreProblema;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
