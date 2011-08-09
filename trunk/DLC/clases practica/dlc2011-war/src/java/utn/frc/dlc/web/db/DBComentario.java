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
import utn.frc.dlc.base.Comentarios;


/**
 *
 * @author dlcusr
 */
public class DBComentario {
    public static List loadComentarios(SqlManager sql, String query) throws Exception {
        if (sql==null) throw new Exception("DBComentario.loadComentarios ERROR: Sql inválido.");
        if (query==null) throw new Exception("DBComentario.loadComentarios ERROR: Query inválida.");
     List comentarios = null;
    Comentarios com = null;
    Usuario user = null;
        sql.prepare(query);
        ResultSet rs = sql.executeQuery();
        if (rs.first()) {
                comentarios= new ArrayList();
            do {
              com= new Comentarios();
                user = new Usuario();
                int idco = rs.getInt("idcomentario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int idAm = rs.getInt("idUsuario");
                String comen =  rs.getString("comentarios");
                int idf = rs.getInt("idfoto");
                com.setIdComentario(idco);
                com.setIdfoto(idf);
                user.setId(idAm);
                user.setApellido(apellido);
                user.setNombre(nombre);
                com.setUser(user);
                com.setComentarios(comen);
                comentarios.add(com);
            } while (rs.next());
        }
        rs.close();
        return comentarios;
    }
public static List loadComentarios(SqlManager sql) throws Exception {
        String query = "SELECT * FROM comentario c  ";
        return loadComentarios(sql, query);
    }

}
