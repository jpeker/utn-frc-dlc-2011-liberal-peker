/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import jxl.*;

public class exportar_excel {

	    private File archi;
	    private List<JTable> tabla;
	    private List<String> nom_hoja;
	    private WritableCellFormat	fomato_fila ;
	    private WritableCellFormat	fomato_columna;

	    public exportar_excel(List<JTable> tab, File ar) throws Exception {
	        this.archi = ar;
	        this.tabla = tab;
	        if(tab.size()<0){
	            throw new Exception("ERROR");
	        }
	    }
            public exportar_excel(){
                
            }

	    public boolean export() {
	        try {
	            DataOutputStream out = new DataOutputStream(new FileOutputStream(archi));
	            WritableWorkbook w = Workbook.createWorkbook(out);
	            w.createSheet("Reporte----by javaface", 0);
	           
	            for (int index=0;index<tabla.size();index++) {
	                JTable table=tabla.get(index);
	              
	                  WritableSheet s = w.getSheet(0);
	                  
	                for (int i = 0; i < table.getColumnCount(); i++) {
	                    for (int j = 0; j < table.getRowCount(); j++) {
	                        Object objeto = table.getValueAt(j, i);
	                        
	                        createColumna(s,table.getColumnName(i),i);//crea la columna
	                        createFilas(s,i,j,String.valueOf(objeto));//crea las filas
	                       
	                    }
	                }
	            }
	            w.write();
	            w.close();
	            out.close();
	            return true;

	        } catch (IOException | WriteException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }
	    private void createColumna(WritableSheet sheet,String columna,int number_columna)throws WriteException {
			//creamos el tipo de letra
			WritableFont times10pt = new WritableFont(WritableFont.TAHOMA, 14);
			// definimos el formato d ela celda
			WritableCellFormat	times = new WritableCellFormat(times10pt);
			// Permite si se ajusta automáticamente a las células
			//times.setWrap(true);
			// crea una negrita con subrayado
			WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD, false,UnderlineStyle.SINGLE);
			fomato_columna = new WritableCellFormat(times10ptBoldUnderline);
			// Permite que se ajusta automáticamente a las células
			//fomato_columna.setWrap(true);
			CellView cv = new CellView();
			cv.setSize(220);
			cv.setDimension(70);
			cv.setFormat(times);
			cv.setFormat(fomato_columna);
			//cv.setAutosize(true);
			// escribimos las columnas
			addColumna(sheet, number_columna, 0, columna,fomato_columna);//numero de columna , 0 es la fila
		}
	    /****************************************/
	    private void createFilas(WritableSheet sheet,int number_columna,int filas,String name_filas)throws WriteException {
			//creamos el tipo de letra
			WritableFont times10pt = new WritableFont(WritableFont.ARIAL, 12);
			times10pt.setColour(Colour.GOLD);
			// definimos el formato d ela celda
			WritableCellFormat	times = new WritableCellFormat(times10pt);
			times.setBorder(Border.TOP, BorderLineStyle.MEDIUM, Colour.GOLD);
			// Permite si se ajusta automáticamente a las células
			//times.setWrap(true);
			// crea una negrita con subrayado
			WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE);
			fomato_fila = new WritableCellFormat(times10ptBoldUnderline);
			// Permite que se ajusta automáticamente a las células
			//fomato_fila.setWrap(true);
			CellView cv = new CellView();
			cv.setDimension(70);
			cv.setFormat(times);
			cv.setFormat(fomato_fila);
			//cv.setAutosize(true);
			// escribimos las columnas
			addFilas(sheet, number_columna, filas, name_filas,fomato_fila);
		}
	   
	    
	    /***********************************/
	    private void addColumna(WritableSheet sheet, int column, int row, String s,WritableCellFormat format)throws RowsExceededException, WriteException {
			Label label;
			label = new Label(column, row, s, format);
			sheet.addCell(label);
		}
	    private void addFilas(WritableSheet sheet, int column, int row, String s,WritableCellFormat format)throws WriteException, RowsExceededException {
			Label label;
			label = new Label(column, row, s, format);
			sheet.addCell(label);
		}
            public void exportarModelo(DefaultTableModel normalizacionMatriz, DefaultTableModel matrizNormalizada, DefaultTableModel matrizPonderada,
                    DefaultTableModel distanciaIdeal, DefaultTableModel distanciaAntiIdeal, DefaultTableModel conclusion){
        try {
            File archivoModelo = new File("ResolucionModelo.xls");
            WritableWorkbook workbook = Workbook.createWorkbook(archivoModelo);
            WritableSheet hojaNormalizacionMatriz = workbook.createSheet("Normalización de Matriz", 0);
            WritableSheet hojaMatrizNormalizada = workbook.createSheet("Matriz Normalizada", 1);
            WritableSheet hojaMatrizPonderada = workbook.createSheet("Matriz Normalizada y ponderada", 2);
            WritableSheet hojaDistanciaIdeal = workbook.createSheet("Distancias ideal ", 3);
            WritableSheet hojaDistanciaAntiIdeal = workbook.createSheet("Distancias anti ideal ", 4);
            WritableSheet hojaConclusion = workbook.createSheet("Conclusion", 5);
            this.llenarHoja(normalizacionMatriz, hojaNormalizacionMatriz);
            this.llenarHoja(matrizNormalizada, hojaMatrizNormalizada);
            this.llenarHoja(matrizPonderada, hojaMatrizPonderada);
            this.llenarHoja(distanciaIdeal, hojaDistanciaIdeal);
            this.llenarHoja(distanciaAntiIdeal, hojaDistanciaAntiIdeal);
            this.llenarHoja(conclusion, hojaConclusion);
            workbook.write(); 
            workbook.close();
            
            
        } catch (WriteException | IOException ex) {
            Logger.getLogger(exportar_excel.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            }
            private void llenarHoja(DefaultTableModel modelo, WritableSheet hojaDestino){
                for(int i = 0; i < modelo.getRowCount(); i++){
                    for(int j = 0 ; j < modelo.getColumnCount(); j++){
                        try {
                            Label label = new Label (j, i, modelo.getValueAt(i, j).toString());
                            hojaDestino.addCell(label);
                        } catch (WriteException ex) {
                            Logger.getLogger(exportar_excel.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                }
            }

	
}
