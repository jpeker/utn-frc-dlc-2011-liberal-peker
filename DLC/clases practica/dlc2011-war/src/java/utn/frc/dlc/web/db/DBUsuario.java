/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utn.frc.dlc.web.db;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utn.frc.dlc.base.Usuario;
import utn.frc.dlc.db.SqlManager;
/**
 *
 * @author dlcusr
 */
public class DBUsuario {

    public static List loadUsuarios(SqlManager sql, String query) throws Exception {
        if (sql==null) throw new Exception("DBUsuario.loadUsuarios ERROR: Sql inválido.");
        if (query==null) throw new Exception("DBUsuario.loadUsuarios ERROR: Query inválida.");
       List usuarios = null;
       Usuario user = null;
        sql.prepare(query);
        ResultSet rs = sql.executeQuery();
        if (rs.first()) {
            usuarios = new ArrayList();
            do {
                 user = new Usuario();
                int id = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String mail = rs.getString("mail");
                user.setId(id);
                user.setNombre(nombre);
                user.setApellido(apellido);
                user.setMail(mail);
                usuarios.add(user);
            } while (rs.next());
        }
        rs.close();
        return usuarios;
    }
public static List loadUsuarios(SqlManager sql) throws Exception {
        String query = "SELECT * FROM usuario u  order by nombre";
        return loadUsuarios(sql, query);
    }

}
