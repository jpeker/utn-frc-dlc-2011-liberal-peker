/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Kapica Liberal 
 */
public class Libro {

    private Workbook workbook;
    private Map<String,Hoja> hojas = new HashMap<String, Hoja>();
    private Hoja hojaActual;
    private CellStyle styleHeader;
    private Font fontHeader;
    private CellStyle styleTitle;
    private Font fontTitle;
    private final String COLUMNS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Libro() {        
        workbook = new XSSFWorkbook();

        fontHeader = this.workbook.createFont();
        fontHeader.setColor(IndexedColors.WHITE.getIndex());
        fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleHeader = this.workbook.createCellStyle();
        styleHeader.setFont(fontHeader);
        styleHeader.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        styleHeader.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);        

        fontTitle = this.workbook.createFont();
        fontTitle.setFontHeightInPoints((short)14);
        fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleTitle = this.workbook.createCellStyle();
        styleTitle.setFont(fontTitle);
    }

    public void addHoja(String nombre){
        Sheet sheet = this.workbook.createSheet(nombre);
        this.hojaActual = new Hoja(sheet);
        this.hojas.put(nombre, hojaActual);
    }

    public void setHoja(String nombre){
        this.hojaActual = this.hojas.get(nombre);
    }

    public void addEmptyRow(){
        this.hojaActual.addEmptyRow();
    }

    public void addEmptyRows(int nro){
        this.hojaActual.addEmptyRows(nro);
    }

    public String[] addRow(List values, RowType rowType){
        return this.addRow(values, -1, this.hojaActual.getMarginLeft(),rowType);
    }

    public String[] addRow(List values, int rowNum, RowType rowType){
        return this.addRow(values, rowNum, this.hojaActual.getMarginLeft(),rowType);
    }

    public String[] addRow(List values,int rowNum, int column, RowType rowType){
        String positions[] = new String[values.size()];
        Row row = null;
        if(rowNum < 0){
            row = this.hojaActual.createRow();
        }else{
            row = this.hojaActual.createRow(rowNum);
        }
        for (int i = 0; i < values.size(); i++) {
            Object object = values.get(i);
            Cell cell = row.createCell(column+i);
            if(rowType.equals(RowType.HEADER)){
                this.setHeaderValue(cell, object);
            }else if(rowType.equals(RowType.TITLE)){
                this.setTitleValue(cell, object);
            }else{
                this.setCellValue(cell, object);
            }
            positions[i] = this.position(cell);
        }
        return positions;
    }

    public String addCell(Object value){
        Row row = this.hojaActual.getLastRow();        
        Cell cell = row.createCell(row.getLastCellNum());
        this.setCellValue(cell, value);
        return this.position(cell);
    }   

    private void setCellValue(Cell cell, Object value){
        Class clazz = value.getClass();
        if(clazz.equals(Double.class) ||
            clazz.equals(Long.class) ||
            clazz.equals(Float.class) ||
            clazz.equals(Integer.class) ||
            clazz.equals(Short.class)){
            cell.setCellValue(Double.parseDouble(value.toString()));
        }else if(clazz.equals(Boolean.class)){
            cell.setCellValue((Boolean)value);
        }else if(clazz.equals(Date.class)){
            cell.setCellValue((Date)value);
        }else if(clazz.equals(String.class)){
            String val = value.toString();
            if(val.charAt(0) == '='){
                cell.setCellFormula(val.substring(1));
            }else{
                cell.setCellValue(val);
            }
        }else{
            cell.setCellValue(value.toString());
        }        
    }

    private void setHeaderValue(Cell cell, Object value){
        cell.setCellStyle(this.styleHeader);
        this.setCellValue(cell, value);
    }

    private void setTitleValue(Cell cell, Object value){
        cell.setCellStyle(this.styleTitle);
        this.setCellValue(cell, value);
    }

    private String position(Cell cell){
        StringBuilder sb = new StringBuilder();
        sb.append(this.COLUMNS.charAt(cell.getColumnIndex()));
        sb.append(cell.getRowIndex()+1);
        return sb.toString();
    }

    public void autoSizeColumns(int nroColumn){
        this.hojaActual.autoSizeColuns(nroColumn);
    }

    public void writeWorkbook(File file) throws FileNotFoundException, IOException{
        FileOutputStream fileOut = new FileOutputStream(file);
        this.workbook.write(fileOut);
        fileOut.close();
    }


}
