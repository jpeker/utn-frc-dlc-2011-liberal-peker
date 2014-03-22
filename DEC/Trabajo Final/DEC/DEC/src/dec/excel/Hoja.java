/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author Emiliano
 */
public class Hoja {

    private Sheet sheet;
    private int lastRow = 0;
    private int marginTop;
    private int marginLeft;    

    public Hoja(Sheet sheet) {
        this.sheet = sheet;
        this.marginTop = 0;
        this.marginLeft = 1;
    }

    public Hoja(Sheet sheet, int marginTop, int marginLeft) {
        this(sheet);
        this.marginTop = marginTop;
        this.marginLeft = marginLeft;
    }   

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void setSheet(Sheet sheet) {
        this.sheet = sheet;
    }

    public Row createRow(int num){
        Row row = this.sheet.createRow(num);
        this.lastRow = this.sheet.getLastRowNum();
        return row;
    }
    public Row createRow(){
        this.lastRow++;
        return this.sheet.createRow(this.lastRow);
    }

    public void addEmptyRow(){
        this.lastRow++;
    }

    public void addEmptyRows(int nro){
        this.lastRow+=nro;
    }

    public Row getLastRow(){
        return this.sheet.getRow(lastRow);
    }

    public void autoSizeColuns(int column){
        this.sheet.autoSizeColumn(column+marginLeft);
    }
}
