<%--
    Document   : cabecera
    Created on : Jun 8, 2011, 4:55:48 PM
    Author     : dlcusr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table width="100%" border="0" bgcolor="#1D899F">
    <tbody>
        <tr>
            <td align="left">
                <img alt="Logo del sitio" src="img/logo.jpg"/>
            </td>
            <td></td>
        </tr>
        <tr height="20">
            <td align="left">
                <c:out value="Bienvenido ${usr.nombre} ${usr.apellido}"></c:out>
            </td>
            <td align="right">
                ${fecha}
            </td>
        </tr>
    </tbody>
</table>



