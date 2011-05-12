package utn.frc.dlc.web;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utn.frc.dlc.db.SqlManager;

/**
 *
 * @author dlcusr
 */
public class HelloWord extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
         ResultSet resultado;
        try {
 
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HelloWord</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HelloWord at " + request.getContextPath () + "</h1>");
         SqlManager sql = new SqlManager();
         sql.setConnectionMode(SqlManager.POOLCONNECTIONMODE);
         sql.setResourceName("jdbc/pgdlcdb");

         try{

         sql.connect();
         out.println("<h1>Pker </h1>");

         resultado = sql.executeQuery("Select * from usuario");

        

          out.println( "<table width=640px border=1>" );
            out.println("<div class=table>  ");
          out.println("<tr>");
           out.print("<th>id Usuario</th>");
             out.print("<th>Nombre</th>"); 
              out.print("<th>Apellido</th>"); 
            out.print("<th>Apellido</th>"); 
            out.println(" <th>Mail</th> ");
               out.println(" </tr>");
           out.println(" <tr>");
           resultado.first();
             out.println("  <td> "+resultado.getInt(0) +"</td> ");
               out.println("  <td> "+resultado.getString(1) +"</td> ");
                 out.println("  <td> "+resultado.getString(2) +"</td> ");
            out.println("  <td> "+resultado.getString(3) +"</td> ");
                 out.println(" </body>");

            out.println("</html>");
          }
         catch (Exception e)
         {
         out.println("imnposible de conectar  "+e.toString());
          System.out.println("imnposible de conectar  "+e.getMessage());
         }
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
