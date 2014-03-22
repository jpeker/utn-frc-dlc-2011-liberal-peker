/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.gui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.beansbinding.Converter;

/**
 *
 * @author Emiliano
 */
public class DateConverter extends Converter<Date,String>{

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public String convertForward(Date value) {
        return this.sdf.format(value);
    }

    @Override
    public Date convertReverse(String value) {
        try {
            return this.sdf.parse(value);
        } catch (ParseException ex) {
            Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

}
