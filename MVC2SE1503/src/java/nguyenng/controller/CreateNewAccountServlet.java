/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nguyenng.registration.RegistrationDAO;
import nguyenng.registration.RegistrationInsertError;

/**
 *
 * @author bchao
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String INSERT_ERROR_PAGE = "createNewAccount.jsp";

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

        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullName");

        boolean bErr = false;
        RegistrationInsertError errors = new RegistrationInsertError();
        String url = INSERT_ERROR_PAGE;
        try {
            if (username.trim().length() < 6 | username.trim().length() > 12) {
                bErr = true;
                errors.setUsernameLengthErr("Username must have a length of 6-12 characters");
            }
            if (password.trim().length() < 6 | password.trim().length() > 30) {
                bErr = true;
                errors.setPasswordLengthErr("Password must have a length of 6-30 characters");
            } else if (!confirm.trim().equals(password)) {
                bErr = true;
                errors.setConfirmNotMatchErr("Confirm password must match password.");
            }
            if (fullname.trim().length() < 2 | fullname.trim().length() > 50) {
                bErr = true;
                errors.setFullNameLengthErr("Full name must have a length of 2-50 characters");
            }

            if (bErr) {
                request.setAttribute("INSERT_ERRS", errors);
            } else {
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.insertRecord(username, password, fullname, false);

                if (result) {
                    url = LOGIN_PAGE;
                }
            }
        } catch (SQLException ex) {
            log("CreateNewAccountServlet: SQLException " + ex.getMessage());
            errors.setUsernameIsExistedErr("Username " + username + " has already existed.");
            request.setAttribute("INSERT_ERRS", errors);
        } catch (NamingException ex) {
            log("CreateNewAccountServlet: NamingException " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
