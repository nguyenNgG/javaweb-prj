/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nguyenng.order.OrderDAO;
import nguyenng.order.OrderDTO;
import nguyenng.order_details.Order_DetailsDAO;
import nguyenng.order_details.Order_DetailsDTO;

/**
 *
 * @author bchao
 */
@WebServlet(name = "SearchOrderServlet", urlPatterns = {"/SearchOrderServlet"})
public class SearchOrderServlet extends HttpServlet {
    
    private final String SEARCH_RESULT_PAGE = "searchOrder";
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
        
        String url = SEARCH_RESULT_PAGE;
        
        try {
            OrderDAO odDAO = new OrderDAO();
            Order_DetailsDAO od_dDAO = new Order_DetailsDAO();
            
            odDAO.viewOrderList();
            od_dDAO.viewOrder_Details_List();
            
            List<OrderDTO> orderList = odDAO.getOrderList();
            List<Order_DetailsDTO> order_Details_List = od_dDAO.getOrder_Details_List();
            
            request.setAttribute("SEARCH_ORDER", orderList);
            request.setAttribute("SEARCH_ORDER_DETAILS", order_Details_List);
            
            url = SEARCH_RESULT_PAGE;
        } catch (SQLException ex) {
            log("SearchOrderServlet _ SQLException: " + ex.getCause());
        } catch (NamingException ex) {
            log("SearchOrderServlet _ NamingException: " + ex.getCause());
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