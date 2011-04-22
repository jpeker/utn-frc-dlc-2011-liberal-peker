/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;
/**
 * Esta clase de interface especifica el comportamiento que debería
 * tener un iterador para un DirectAccessFile, cuando se pretenda recorrer
 * ese archivo en forma secuencial.
 *
 * @author Rodrigo Liberal
 * @version Abril de 2011
 */
public class DirectAccessFileDeleteIterator implements RegisterFileIterator {

    public DirectAccessFileDeleteIterator(){
    }
    public void first() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void next() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Grabable current() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
