/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.controller;

import dec.dominio.Problema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Kapica Liberal 
 */
public class ProblemaController {

    private static ProblemaController instance;
    private static File lastProblema;

    private ProblemaController(){
        
    }

    public static ProblemaController getInstance(){
        if(ProblemaController.instance == null){
           ProblemaController.instance = new ProblemaController();
        }
        return ProblemaController.instance;
    }

    public void saveProblema(Problema problema, File file) throws FileNotFoundException, IOException{
        FileOutputStream f = new FileOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(f);
        output.writeObject(problema);
        f.close();
        output.close();
        ProblemaController.lastProblema = file;
    }

    public Problema loadProblema(File file) throws FileNotFoundException, IOException, ClassNotFoundException{
        Object o = null;
        FileInputStream f;
        f = new FileInputStream(file);
        ObjectInputStream input = new ObjectInputStream(f);
        o = input.readObject();
        f.close();
        input.close();
        if(o instanceof Problema){
            ProblemaController.lastProblema = file;
            return (Problema)o;
        }else{
            throw new RuntimeException("Error al obtener el Problema desde el archivo");
        }
    }
    public Problema loadLastProblema() throws FileNotFoundException, IOException, ClassNotFoundException{
        return ProblemaController.getInstance().loadProblema(ProblemaController.lastProblema);
    }
    public void saveLastProblema(Problema problema) throws FileNotFoundException, IOException{
        ProblemaController.getInstance().saveProblema(problema, lastProblema);
    }
}
