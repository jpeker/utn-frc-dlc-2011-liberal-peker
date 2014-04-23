/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Kapica
 */
public class Imagen extends javax.swing.JPanel {

public Imagen() {
this.setSize(200, 300); //se selecciona el tamaño del panel
}

//Se crea un método cuyo parámetro debe ser un objeto Graphics

public void paint(Graphics grafico) {
Dimension height = getSize();

//Se selecciona la imagen que tenemos en el paquete de la //ruta del programa

ImageIcon Img = new ImageIcon(getClass().getResource("/resources/DT.png"));

//se dibuja la imagen que tenemos en el paquete Images //dentro de un panel

grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);

setOpaque(false);
super.paintComponent(grafico);
}
}
