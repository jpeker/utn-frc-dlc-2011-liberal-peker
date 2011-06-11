<%-- 
    Document   : amigos
    Created on : Jun 9, 2011, 6:40:35 PM
    Author     : dlcusr
--%>

<%@page
    language= "java"
    import= "utn.frc.dlc.base.Amigos"
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
    List amigos = null;
    Amigos ami = null;
    int id = Integer.parseInt(request.getParameter("dato1"));
    try {
        sql.setConnectionMode(SqlManager.POOLCONNECTIONMODE);
        sql.setResourceName("jdbc/pgdlcdb");

        sql.connect();
        sql.prepare("SELECT * FROM usuario u, amigo a where u.idUsuario = a.idAmigo2 AND idAmigo1 = "+ id );

        ResultSet rs = sql.executeQuery();
        if (rs.first()) {
            amigos = new ArrayList();
            do {
                ami = new Amigos();
               
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                int idAm2 = rs.getInt("idAmigo2");
                ami.setIdAmigo1(id);
                ami.setIdAmigo2(idAm2);
                ami.setNombre1(nombre);
                ami.setApellido1(apellido);
                amigos.add(ami);
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
          <form name="go" action="usuario.jsp">
           <div class="body">
           <div class="content">
               <h3>Amigos de <%out.print(request.getParameter("dato2")); %></h3>
          </div>

          <br>
          <div>
          <table width="640px" border="1">
          <div class="table">
               </div>
          <tr>
	    <th>Nombre</th>
	    <th>Apellido </th>
               </tr>
        <%
            String t = errMsg;
            if (t != null) {
                t = "<tr><td colspan=\"1\">" + t + "</td></tr>";
            } else if (amigos != null) {
                t = "";
                int count = amigos.size();
                for (int i=0; i<count; i++) {
                    ami = (Amigos)amigos.get(i);
                    t += "<tr>";
                    t += "<td><a href=fotos.jsp?dato1="+ami.getIdAmigo1() +"&dato2="+ami.getIdAmigo2() +">" + ami.getNombre1() + "</a></td>";
                    t += "<td>" + ami.getApellido1() + "</td>";
                    t += "</tr>";
                }
            }
            out.println(t);


            %>
    </table>
    <br/>
   <input type="submit" id="volver" value="volver" onclik="action">
          </div>
             </div>
    </form>
               </body>

</html>
