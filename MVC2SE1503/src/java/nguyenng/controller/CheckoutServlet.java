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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nguyenng.cart.CartObj;
import nguyenng.order.OrderDAO;
import nguyenng.order_details.Order_DetailsDAO;
import nguyenng.product.ProductDAO;

/**
 *
 * @author bchao
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

//    private final String VIEW_BOOKSTORE_CONTROLLER = "DispatchServlet"
//            + "?btAction=View+Bookstore";
    private final String INVOICE_PAGE = "orderInvoice.jsp";

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
        String url = INVOICE_PAGE;
        try {
            //1. Cust goes to cart place
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. Cust takes their cart
                CartObj cart = (CartObj) session.getAttribute("CART");
                if (cart != null) {
                    //3. Cust get items in cart
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //new DAO
                        ProductDAO pdDAO = new ProductDAO();
                        OrderDAO odDAO = new OrderDAO();
                        Order_DetailsDAO od_dDAO = new Order_DetailsDAO();

                        //create orderID
                        String orderID = "";
                        Random rand = new Random();
                        do {
                            int randInt = rand.nextInt(2000);
                            orderID = "ORDER" + randInt;
                        } while (odDAO.isOrderIDTaken(orderID));
                        //end while if available order ID is found

                        //order table
                        //orderID
                        String custName = request.getParameter("txtCustName");
                        String custAddress = request.getParameter("txtAddress");

                        //call method
                        boolean addOrder_result = odDAO.addOrder(orderID, custName, custAddress);

                        //4. Traverse each item to save to DB
                        for (String title : items.keySet()) {

                            //order_details table
                            //orderID (from order table)
                            String productID = pdDAO.getProductIDFromName(title);
                            int quantity = items.get(title);

                            boolean addOrder_Details_result = od_dDAO.addOrderDetails(orderID, productID, quantity);

                            if (addOrder_Details_result && addOrder_result) {
                                //5. Container destroy attribute
                                session.removeAttribute("CART");
                            }//end if add order & order details successfully
                        }//end traverse items
                    } //end if items existed
                } //end if cart existed
            } //end if session existed
        } catch (SQLException ex) {
            //to do
        } catch (NamingException ex) {
            //to do
        } finally {
            //6. Cust view invoice and return to go shopping
            response.sendRedirect(url);
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
