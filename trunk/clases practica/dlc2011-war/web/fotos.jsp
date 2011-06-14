
<%-- 
    Document   : fotos
    Created on : Jun 9, 2011, 7:53:08 PM
    Author     : dlcusr
--%>

<%@page
    language= "java"
    import= "utn.frc.dlc.base.Fotos"
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
    List fotos = null;
    Fotos fot = null;
    int idAm1 = Integer.parseInt( request.getParameter("dato1"));
    int idAm2 = Integer.parseInt( request.getParameter("dato2"));
    try {
        sql.setConnectionMode(SqlManager.POOLCONNECTIONMODE);
        sql.setResourceName("jdbc/pgdlcdb");

        sql.connect();
        sql.prepare("SELECT * FROM foto f where (f.idAmigo1 ="+idAm1+" and idAmigo2 ="+idAm2+")or (f.idAmigo1 ="+idAm2+" and idAmigo2 ="+idAm1+")");

        ResultSet rs = sql.executeQuery();
        if (rs.first()) {
            fotos= new ArrayList();
            do {
                fot = new Fotos();
                int id = rs.getInt("idfoto");
                String path = rs.getString("pathfoto");
                 String fe =  rs.getString(5);
                fot.setIdfoto(id);
                fot.setFecha(fe);
                fot.setIdAmigo1(idAm1);
                fot.setIdAmigo2(idAm2);
                fot.setPathfoto(path);
                fotos.add(fot);
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
     <title>Fotos del usuario </title
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="layout.css" rel="stylesheet" type="text/css" />
 </head>
      <body>
        
           <div class="content">
           <h1> Listado de Fotos </h1>
          </div>
          
          <br>
          <div>

                <%
            String t = errMsg;
            if (t != null) {
                t =  t ;
            } else if (fotos != null) {
                t = "";
                int count = fotos.size();
                
                for (int i=0; i<count; i++) {
                    fot = (Fotos)fotos.get(i);
                    t += "<p>";
                    t += "<img src="+fot.getPathfoto()+" alt=no se puede mostrar width=304 height=224 usemap =#"+ fot.getPathfoto() + " />";
                    t +=  "<br>";
                    t += "<map name="+ fot.getPathfoto() + ">";
                    t +=  "<area shape=rect coords=0,0,304,224 alt=no se puede mostrar  href=comentario.jsp?dato1="+fot.getIdfoto()+"&dato2='"+fot.getPathfoto()+"'";
                    t +=  "/></map>";
                    t += "agregada el "+fot.getFecha();
                    t += "</p>";
                }
            }
            out.println(t);


            %>

          </div>
        
      </body>
</html>
