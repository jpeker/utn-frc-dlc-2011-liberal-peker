<%-- 
    Document   : comentario
    Created on : Jun 10, 2011, 10:47:06 AM
    Author     : dlcusr
--%>

<%@page
    language= "java"
    import= "utn.frc.dlc.base.Comentarios"
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
    List comentarios = null;
    Comentarios com = null;
    Usuario user = null;
    int id = Integer.parseInt(request.getParameter("dato1"));
    String path = request.getParameter("dato2");
    try {
        sql.setConnectionMode(SqlManager.POOLCONNECTIONMODE);
        sql.setResourceName("jdbc/pgdlcdb");

        sql.connect();
        sql.prepare("select * from comentario c, usuario u where c.idUsuario = u.idUsuario and c.idFoto ="+ id );

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
                com.setIdComentario(idco);
                com.setIdfoto(id);
                user.setId(idAm);
                user.setApellido(apellido);
                user.setNombre(nombre);
                com.setUser(user);
                com.setComentarios(comen);
                comentarios.add(com);
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
     <title>Fotos del usuario </title>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="layout.css" rel="stylesheet" type="text/css" />
 </head>
      <body>
           <div class="content">
           <h1>Fotos con comentarios</h1>
          </div>

          <br>
          <div>
           <p>
              <% String p="";

                p += "<img src="+path+ " alt=no se puede mostrar width=800 height=600  />";
              out.print(p);
              %>
	  </p>
          <p>
           <h1>Comentarios</h1>
            <br>
              <table width="640px" border="1">
               <tr>
           <th>Usuario </th>
           <th>Comentario </th>
           </tr>
            <%
            String t = errMsg;
            if (t != null) {
                t = "<tr><td colspan=\"1\">" + t + "</td></tr>";
            } else if (comentarios != null) {
                t = "";
                int count = comentarios.size();
                for (int i=0; i<count; i++) {
                    com = (Comentarios)comentarios.get(i);
                    t += "<tr>";
                    t += "<td> "+ com.getUser().getNombre() +" "+ com.getUser().getApellido()  + "</td>";
                    t += "<td>" + com.getComentarios()+ "</td>";
                    t += "</tr>";
                }
            }
            out.println(t);


            %>

 </table>

                    </div>
        
               </body>

</html>