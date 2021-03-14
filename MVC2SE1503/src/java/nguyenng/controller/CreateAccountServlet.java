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
import nguyenng.registration.RegistrationCreateError;
import nguyenng.registration.RegistrationDAO;

/**
 *
 * @author bchao
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    private final String ERROR_PAGE = "createAccountError";
    private final String LOGIN_PAGE = "loginPage";

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
        String fullname = request.getParameter("txtFullname");

        String url = ERROR_PAGE;
        RegistrationCreateError errors = new RegistrationCreateError();
        boolean foundErr = false;

        try {
            //1. Check valid user input
            if (username.trim().length() < 6 || username.trim().length() > 30) {
                foundErr = true;
                errors.setUsernameLengthErr("Username requires input from 6 to 30 characters.");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundErr = true;
                errors.setPasswordLengthErr("Password requires input from 6 to 20 characters.");
            } else if (!password.trim().equals(confirm.trim())) {
                foundErr = true;
                errors.setConfirmNotMatchErr("Confirm must match password");
            }
            if (fullname.trim().length() < 2 || fullname.trim().length() > 50) {
                foundErr = true;
                errors.setFullnameLengthErr("Full name requires input from 6 to 30 characters.");
            }
            
            if (foundErr) {
                request.setAttribute("CREATE_ERROR", errors);
            } else {
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.createNewAccount(username, password, fullname, false);
                if (result) {
                    url = LOGIN_PAGE;
                } //if create successfully
            }
        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            log("CreateAccountServlet _ SQLException: " + ex.getCause());
            if (errMsg.contains("duplicate")) {
                errors.setUsernameIsExistedErr(username + " existed.");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch (NamingException ex) {
            log("CreateAccountServlet _ NamingException: " + ex.getCause());
            //getCause dua stack trace
        } finally {
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
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
