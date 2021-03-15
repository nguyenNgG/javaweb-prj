/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nguyenng.registration.RegistrationDAO;
import nguyenng.registration.RegistrationUpdateError;

/**
 *
 * @author bchao
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

    private final String SEARCH_PAGE = "searchPage";
    private final String SEARCH_CONTROLLER = "search";

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

        Map<String, String> listUrl = (Map<String, String>) request
                .getServletContext()
                .getAttribute("URL_MAPPING");

        String url = listUrl.get(SEARCH_PAGE);

        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String chkAdmin = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("lastSearchValue");
        boolean role = false;

        RegistrationUpdateError errors = new RegistrationUpdateError();
        boolean foundErr = false;

        try {
            System.out.println(chkAdmin);
            if (chkAdmin != null) {
                role = true;
            }
            if (password.trim().length() < 6 || password.trim().length() > 20) {
                foundErr = true;
                errors.setPasswordLengthErr("Password requires input from 6 to 20 characters.");
            }
            if (foundErr) {
                request.setAttribute("UPDATE_ERROR", errors);
                request.setAttribute("UPDATE_ERROR_USERNAME", username);
                url = listUrl.get(SEARCH_CONTROLLER)
                        + "?txtSearchValue=" + searchValue;
            } // end if errors found
            else {
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.updateAccount(username, password, role);
                if (result) {
                    url = listUrl.get(SEARCH_CONTROLLER)
                            //                        + "?btAction=Search"
                            + "?txtSearchValue=" + searchValue;
                } // end if update successfully
            } // no errors found
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException ex) {
            log("UpdateAccountServlet _ SQLException: " + ex.getCause());
            response.sendError(461);
        } catch (NamingException ex) {
            log("UpdateAccountServlet _ NamingException: " + ex.getCause());
            response.sendError(461);
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
