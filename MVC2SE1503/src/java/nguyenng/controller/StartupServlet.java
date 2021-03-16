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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nguyenng.registration.RegistrationDAO;

/**
 *
 * @author bchao
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {

    private final String LOGIN_PAGE = "loginPage";
    private final String SEARCH_PAGE = "searchPage";

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
        String url = listUrl.get(LOGIN_PAGE);

        try {
            //1. Check cookie existed
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2. Get username and password
                for (Cookie cookie : cookies) {
                    String username = cookie.getName();
                    String password = cookie.getValue();
                    //3. Check if username & password is correct
                    RegistrationDAO dao = new RegistrationDAO();
                    boolean result = dao.checkLogin(username, password);
                    if (result) {
                        url = listUrl.get(SEARCH_PAGE);
                        HttpSession session = request.getSession(true);
                        //can luu tru thi can vung nho cap nhat
                        session.setAttribute("USER_USERNAME", username);
                        //su dung dao de lay fullname
                        String fullname = dao.getFullName(username);
                        if (fullname == null) {
                            fullname = username + " (username)";
                        }
                        session.setAttribute("USER_FULLNAME", fullname);
                    }//end if user valid
                } //traversing cookies
            }//end if cookies existed
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException ex) {
            log("StartupServlet _ SQLException: ", ex.getCause());
            response.sendError(461);
        } catch (NamingException ex) {
            log("StartupServlet _ SQLException: ", ex.getCause());
            response.sendError(461);
        } finally {
            //dung rd hay redirect cung dc
            //gia tri luu tru trong cookie k bi mat khi tra response
//            response.sendRedirect(url);
            // dùng rd để ẩn login.html
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
