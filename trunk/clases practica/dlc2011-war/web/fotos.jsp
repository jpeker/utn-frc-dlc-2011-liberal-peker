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
           <h1> Listado de Fotos </h1>
          </div>
          
          <br>
          <div>

         <c:set scope="page" var="index" value="-1"></c:set>
              <c:forEach var="foto" items="${fotos}">
              <p>
            <c:set var="index" value="${index+1}"></c:set>
          <img src="${foto.pathfoto}" alt= "no se puede mostrar" width="304" height="224" usemap ="#${foto.pathfoto}" />
          <br>
            <map name="${foto.pathfoto}">
           <area shape="rect" coords="0,0,304,224" href="CtrlComentario?action=show&idfoto=${foto.idfoto}&path=${foto.pathfoto}" alt="no se puede mostrar" />
	  </map>
          agregada el ${foto.fecha}
          </p>
            </c:forEach>

          </div>
         
      </body>
</html>
