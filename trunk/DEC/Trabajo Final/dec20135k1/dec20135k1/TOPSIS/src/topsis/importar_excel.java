/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topsis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.*;
import jxl.read.biff.BiffException;

/**
 *
 * @author AndrésDarío
 */
public class importar_excel {

    Vector columna = new Vector();  //  @jve:decl-index=0:
    Vector filas = new Vector();  //  @jve:decl-index=0:
    ArrayList<Criterio> criterios = new ArrayList<>();
    ArrayList<Alternativa> alternativasA = new ArrayList<>();
    String[] matrizFinal;
    private boolean fistTimeNotEmpty = true;
    private int rowSize = 0;
    private int firstUsedRow = -1;

    public importar_excel() {
    }
    Vector alternativas = new Vector();
    List<String[]> matrizValores = new ArrayList<String[]>();
    /**
     * Devuelve la lista de alternativas que tiene el archivo excel
     * @param f
     * @return 
     */
   public List<Alternativa> getAlternativas(Workbook f){
       
       List<Alternativa> listaAlternativas = new ArrayList<Alternativa>();
       Workbook workbook = null;
        
                workbook = f;
                Sheet sheet = workbook.getSheet(0);
                if(sheet.getCell(0, sheet.getRows() -2).getContents().equalsIgnoreCase("Peso")
                && sheet.getCell(0, sheet.getRows() -1).getContents().equalsIgnoreCase("Tipo")){
                    for (int i = 1; i < sheet.getRows()-2; i++) {
                        Cell cell1 = sheet.getCell(0, i);
                //si la celda no esta vacia
                        if (!cell1.getContents().isEmpty()) {
                            Alternativa alternativa = new Alternativa(cell1.getContents());
                            for(int j = 1; j< sheet.getColumns(); j++){
                                int valor = Integer.valueOf((sheet.getCell(j, i).getContents()));
                                alternativa.addValor(valor);
                            }
                            listaAlternativas.add(alternativa);
   
                            } 
                         }
                    return listaAlternativas;
                    }
             return null;            

       
       
            //return alternativas;
        
   }  
   /**
    * Devuelve la lista de criterios de un archivo Excel
    * @param file
    * @return 
    */
   public List<Criterio> getCriterios(Workbook file){
        List<Criterio> listaCriterios = new ArrayList<Criterio>();
        Workbook workbook = null;
       
                workbook = file;
                Sheet sheet = workbook.getSheet(0);
                
                    for (int i = 1; i < sheet.getColumns(); i++) {
                        Cell cell1 = sheet.getCell(i, 0);
                
                        if (!cell1.getContents().isEmpty()) {
                            double peso = Double.parseDouble(sheet.getCell(i, sheet.getRows()-2).getContents());
                            int tipo = 0;
                            if(sheet.getCell(i, sheet.getRows()-1).getContents().equalsIgnoreCase("Max")){
                                tipo = 1;
                            }
                            Criterio criterio = new Criterio(sheet.getCell(i,0).getContents(),peso, tipo); 
                            listaCriterios.add(criterio);
   
                            } 
                         }
                    return listaCriterios;         

      
         
        
   }
    //este metod otiene que ejecutarse dsp de ejecutar la columna de las alternativas

    public List<String[]> getMatriz(File file) {
        Workbook workbook = null;
        int pos;
        List<String[]> matriz = new ArrayList<String[]>();

        try {
            try {
                workbook = Workbook.getWorkbook(file);
            } catch (IOException ex) {
                //Logger.getLogger( principal.class. getName()).log(Level.SEVERE, null, ex);
            }
            Sheet sheet = workbook.getSheet(0);
            //columna.clear();   
            for (int i = 1; i < sheet.getColumns(); i++) {
                matriz.add(new String[this.rowSize]);
                pos = 0;
                for (int j = 0; j < (this.rowSize); j++) {
                    Cell cell1 = sheet.getCell(i, j);
                    matriz.get(matriz.size() - 1)[pos] = cell1.getContents();
                    pos++;
                }

            }
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return matriz;
    }

    public void CrearTabla() {
        File file = new File("");
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Archivos de Microsoft Excel", "xls");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        //alternativas = this.getAlternativas(file);
        this.matrizValores = this.getMatriz(file);
    }

    public void transformarObjetos(Vector Alternativas, List<String[]> criterios) {
        ArrayList<Alternativa> alternativas = new ArrayList<Alternativa>();
        this.alternativasA.clear();
        Iterator iterator = alternativas.iterator();
        while (iterator.hasNext()) {
            String stringAlternativa = (String) iterator.next();
            if (!stringAlternativa.isEmpty() || stringAlternativa.equalsIgnoreCase("peso") || stringAlternativa.equalsIgnoreCase("tipo")) {
                Alternativa alternativa = new Alternativa("stringAlternativa");
                alternativas.add(alternativa);
                this.alternativasA.add(alternativa);
            }
        }

        this.criterios.clear();
        Iterator criteriosIterator = criterios.iterator();
        while (criteriosIterator.hasNext()) {
            String[] tempArray = (String[]) criteriosIterator.next();
            //for(int i =0; i< tempArray.length; i++){
            String nombre = tempArray[0];
            double peso = Double.parseDouble(tempArray[tempArray.length - 2]);
            String tipo = tempArray[tempArray.length - 1];
            int intTipo = 0;
            if (!tipo.isEmpty() && tipo.equalsIgnoreCase("max")) {
                intTipo = 1;
            }
            Criterio criterio = new Criterio(nombre, peso, intTipo);
            this.criterios.add(criterio);
        }

    }
}
