/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.gui;

import dec.dominio.Alternativa;
import dec.dominio.Criterio;
import dec.dominio.Problema;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kapica Liberal 
 */
public class MiTableModel extends DefaultTableModel{    
    private Problema problema;   

    public MiTableModel(Problema problema) {
        this.problema = problema;        
        super.addColumn("Alternativas");
        for (Criterio criterio : this.problema.getCriterioList()) {
            super.addColumn(criterio.getNombre() + ": " + criterio.getPeso());
        }
        for(Alternativa alternativa : this.problema.getAlternativaList()){
            this.addRow(alternativa);
        }
    }

    private void addRow(Alternativa alternativa){
        Object row[] = new Object[alternativa.getValores().size()+1];        
        row[0] = alternativa.getNombre();
        for (int i = 1; i < row.length; i++) {
            row[i] = alternativa.getValores().get(i-1).getValor();
        }
        super.addRow(row);
    }

    public void addAlternativa(Alternativa alternativa){
        alternativa.setOrden(super.getRowCount()+1);
        this.problema.addAlternativa(alternativa);
        this.addRow(alternativa);
    }

    public void addCriterio(Criterio criterio){
        this.problema.addCriterio(criterio);
        super.addColumn(criterio.getNombre() + ": " + criterio.getPeso());
    }

    public Problema getProblema()throws NumberFormatException {
        for (int i = 0; i < super.getRowCount(); i++) {
            Alternativa alternativa = this.problema.getAlternativaList().get(i);
            for (int j = 1; j < super.getColumnCount(); j++) {
                Object object = super.getValueAt(i, j);
                Float valor = null;
                if(object != null){
                    valor = Float.parseFloat(object.toString());                    
                }else{
                    valor = 0f;
                }
                alternativa.getValores().get(j-1).setValor(valor);
            }
        }        
        return problema;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return false;
        }else{
            return true;
        }
    }
}
