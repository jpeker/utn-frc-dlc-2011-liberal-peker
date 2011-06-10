<%-- 
    Document   : usuario
    Created on : Jun 8, 2011, 5:42:58 PM
    Author     : dlcusr


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
--%>
<%@page
    language= "java"
    import= "utn.frc.dlc.base.Usuario"
    import= "utn.frc.dlc.db.SqlManager"
    import= "java.util.List"
    import= "java.sql.ResultSet"
    import= "java.util.ArrayList"
    contentType="text/html"
    pageEncoding="UTF-8"
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    String errMsg = null;
    SqlManager sql = new SqlManager();
    List usuarios = null;
    Usuario user = null;

    try {
        sql.setConnectionMode(SqlManager.POOLCONNECTIONMODE);
        sql.setResourceName("jdbc/pgdlcdb");

        sql.connect();
        sql.prepare("SELECT * FROM usuario u ");

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
        sql.close();

    } catch (Exception e) {
        errMsg = e.getMessage();
        out.println(errMsg);
    }

%>

<html>
 <head>
     <title> Usuarios - AMigos </title>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="layout.css" rel="stylesheet" type="text/css" />
 </head>
      <body >
           <div class="body">
           <div class="content">
           <h3>Usuarios</h3>
          </div>

          <br>
          <div>
          <table width="640px" border="1">
          <div class="table">
               </div>
          <tr>
	    <th>id Usuario</th>
	    <th>Nombre</th>
            <th>Apellido</th>
	    <th>Mail</th>
          </tr>
             <%
            String t = errMsg;
            if (t != null) {
                t = "<tr><td colspan=\"1\">" + t + "</td></tr>";
            } else if (usuarios != null) {
                t = "";
                int count = usuarios.size();
                for (int i=0; i<count; i++) {
                    user = (Usuario)usuarios.get(i);
                    t += "<tr>";
                    t += "<td> <a href=amigos.jsp?dato1="+ user.getId() +"&dato2="+ user.getNombre()+">" + user.getId() + "</a></td>";
                    t += "<td>" + user.getNombre() + "</td>";
                    t += "<td>" + user.getApellido() + "</td>";
                    t += "<td>" + user.getMail() + "</td>";
                    t += "</tr>";
                }
            }
            out.println(t);
            

            %>

 </table>

                    </div>
             </div>
               </body>

</html>