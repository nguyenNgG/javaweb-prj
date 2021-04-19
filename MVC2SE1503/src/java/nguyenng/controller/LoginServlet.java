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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String INVALID_PAGE = "invalid";
    private final String SEARCH_PAGE = "searchPage";
//    private final String SEARCH_PAGE = "search.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /*
        1. check login
        1. fail -> invalid page
        2. successful, create session
        3. save username, fullname, role
        4. create cookie
        5. search page
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //Khong dung shorthand
        PrintWriter out = response.getWriter();
        Map<String, String> listUrl = (Map<String, String>) request
                .getServletContext()
                .getAttribute("URL_MAPPING");
        
        String url = listUrl.get(INVALID_PAGE); //default page

        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        // no check Login value
        try {
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
                //get role
                boolean userRole = dao.getRoleOfUser(username);
                session.setAttribute("USER_FULLNAME", fullname);
                session.setAttribute("USER_ROLE", userRole);
                Cookie cookie = new Cookie(username, password);
                cookie.setMaxAge(60*3);
                response.addCookie(cookie);
//                cookie de remember password, session de quan ly 
//                quy trinh van hanh network (thao tac)
            } //end if login successful
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException ex) {
            log("LoginServlet _ SQLException: ", ex.getCause());
            response.sendError(561);
        } catch (NamingException ex) {
            log("LoginServlet _ NamingException: ", ex.getCause());
            response.sendError(561);
        } finally {
//            response.sendRedirect(url); 
            
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
