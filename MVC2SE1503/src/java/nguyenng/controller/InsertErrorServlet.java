/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nguyenng.registration.RegistrationInsertError;

/**
 *
 * @author bchao
 */
@WebServlet(name = "InsertErrorServlet", urlPatterns = {"/InsertErrorServlet"})
public class InsertErrorServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Errors</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Insert Errors</h1>");

            RegistrationInsertError errors
                    = (RegistrationInsertError) request.getAttribute("INSERT_ERRS");
            if (errors != null) {
                System.out.println(errors.getUsernameLengthErr());
                System.out.println(errors.getPasswordLengthErr());
                System.out.println(errors.getFullNameLengthErr());
                System.out.println(errors.getConfirmNotMatchErr());
                System.out.println(errors.getUsernameIsExistedErr());
                if (errors.getUsernameLengthErr() != null) {
                    out.println("<font color='red'>"
                            + errors.getUsernameLengthErr()
                            + "</font><br/>");
                }
                if (errors.getPasswordLengthErr() != null) {
                    out.println("<font color='red'>"
                            + errors.getPasswordLengthErr()
                            + "</font><br/>");
                }
                if (errors.getConfirmNotMatchErr() != null) {
                    out.println("<font color='red'>"
                            + errors.getConfirmNotMatchErr()
                            + "</font><br/>");
                }
                if (errors.getFullNameLengthErr() != null) {
                    out.println("<font color='red'>"
                            + errors.getFullNameLengthErr()
                            + "</font><br/>");
                }
                if (errors.getUsernameIsExistedErr() != null) {
                    out.println("<font color='red'>"
                            + errors.getUsernameIsExistedErr()
                            + "</font><br/>");
                }
            }//end if errors existed

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
