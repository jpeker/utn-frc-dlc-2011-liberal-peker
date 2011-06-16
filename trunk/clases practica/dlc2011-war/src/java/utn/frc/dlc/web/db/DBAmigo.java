/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utn.frc.dlc.web.db;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utn.frc.dlc.base.Amigos;
import utn.frc.dlc.db.SqlManager;
/**
 *
 * @author dlcusr
 */
public class DBAmigo {
 public static List loadAmigos(SqlManager sql, String query) throws Exception {
        if (sql==null) throw new Exception("DBUAmigo.loadAmigos ERROR: Sql inválido.");
        if (query==null) throw new Exception("DBAmigo.loadAmigos ERROR: Query inválida.");
       List amigos = null;
       Amigos ami = null;
        sql.prepare(query);
        ResultSet rs = sql.executeQuery();
        if (rs.first()) {
            amigos = new ArrayList();
            do {
                ami = new Amigos();

                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int idAm2 = rs.getInt("idAmigo2");
                int idAm1 = rs.getInt("idAmigo1");
                ami.setIdAmigo1(idAm1);
                ami.setIdAmigo2(idAm2);
                ami.setNombre1(nombre);
                ami.setApellido1(apellido);
                amigos.add(ami);
            } while (rs.next());
        }
        rs.close();
        return amigos;
    }
public static List loadAmigos(SqlManager sql) throws Exception {
        String query = "SELECT * FROM amigos a  ";
        return loadAmigos(sql, query);
    }
}
