/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dec.gui.util;

import org.jdesktop.beansbinding.Validator;

/**
 *
 * @author Kapica Liberal 
 */
public class RequiredValidator extends Validator{

    @Override
    public Result validate(Object value) {
        if(value == null || value.toString().trim().length() == 0){
            return new Result(value, "El valor es requerido");
        }else{
            return null;
        }
    }
}
