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
     <title> Usuarios - AMigos </title>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="layout.css" rel="stylesheet" type="text/css" />
 </head>
      <body >
            <jsp:include page="cabecera.jsp"/>
           <div class="body">
           <div class="content">
            <div>
                <a href="CtrlUsuario?action=show"> Usuarios </a> 
             </div>
          <h3>Amigos de ${nombre}</h3>
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
       <c:set scope="page" var="index" value="-1"></c:set>
                <c:forEach var="amigo" items="${amigos}">
                  <tr>
                   <c:set var="index" value="${index+1}"></c:set>
                    <td> <a href="CtrlFoto?action=show&ida1=${amigo.idAmigo1}&ida2=${amigo.idAmigo2}">${amigo.nombre1}</a></td>
                <td>${amigo.apellido1}</td>
                  </tr>
               </c:forEach>
    </table>
    <br/>
   
          </div>
             </div>
                  </body>

</html>
