<%--
    Document   : usuario
    Created on : Jun 8, 2011, 5:42:58 PM
    Author     : dlcusr


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


<html>
 <head>
     <title>Fotos del usuario </title>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="layout.css" rel="stylesheet" type="text/css" />
 </head>
      <body>
           <jsp:include page="cabecera.jsp"/>
           <div class="content">
               <table width="100%" border="0">
                <tr>
                <td> <a href="CtrlUsuario?action=show"> Usuarios</a></td>
                <td align="left"> <a href="CtrlAmigo?action=show&id=${id}&nombre=${nombre}"> Amigo de ${nombre}</a> </td>
                 <td align="left"> <a href="CtrlFoto?action=show&ida1=${idAmigo1}&ida2=${idAmigo2}"> Listado de Fotos </a> </td>
               </tr>
            </table>
           <h1>Fotos con comentarios</h1>
          </div>

          <br>
          <div>
           <p>
           

          <img src="${path}" alt="no se puede mostrar" width="800" height="600"  />";
                 
	  </p>
          <p>
           <h1>Comentarios</h1>
            <br>
              <table width="640px" border="1">
               <tr>
           <th>Usuario </th>
           <th>Comentario </th>
           </tr>
            <c:set scope="page" var="index" value="-1"></c:set>
                <c:forEach var="comen" items="${comentar}">
                  <tr>
                   <c:set var="index" value="${index+1}"></c:set>
                   <td> ${comen.user.nombre} </td>
                   <td> ${comen.comentarios} </td>
                  </tr>
               </c:forEach>

                </table>

                    </div>
        
               </body>

</html>