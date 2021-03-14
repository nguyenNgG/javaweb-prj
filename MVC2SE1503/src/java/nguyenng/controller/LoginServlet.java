/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //Khong dung shorthand
        PrintWriter out = response.getWriter();

        String url = INVALID_PAGE; //default page

        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");

        // no check Login value
        try {
            RegistrationDAO dao = new RegistrationDAO();
            boolean result = dao.checkLogin(username, password);
            if (result) {
                url = SEARCH_PAGE; //when result is true, set url to SEARCH
                HttpSession session = request.getSession(true);
                //can luu tru thi can vung nho cap nhat
                session.setAttribute("USER_USERNAME", username);
                //truyen fullname vao, su dung dao
                String fullname = dao.getFullName(username);
                if (fullname == null) {
                    fullname = username + " (username)";
                }
                session.setAttribute("USER_FULLNAME", fullname);
//                Cookie cookie = new Cookie(username, password);
//                cookie.setMaxAge(60);
//                response.addCookie(cookie);
//                cookie de remember password, session de quan ly 
//                quy trinh van hanh network (thao tac)
            } //end if Login is click
        } catch (SQLException ex) {
            log("LoginServlet _ SQLException: " + ex.getCause());
        } catch (NamingException ex) {
            log("LoginServlet _ NamingException: " + ex.getCause());
        } finally {
//            response.sendRedirect(url); //no security
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
