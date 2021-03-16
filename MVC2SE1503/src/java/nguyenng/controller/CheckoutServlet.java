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
import javax.servlet.http.HttpSession;
import nguyenng.cart.CartObj;
import nguyenng.order.OrderDAO;
import nguyenng.order.OrderInsertErr;
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
    private final String INVOICE_PAGE = "invoicePage";
    private final String VIEW_CART_PAGE = "viewCart";

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

        String url = listUrl.get(VIEW_CART_PAGE);
        //0. Cust information
        boolean bErr = false;
        String custName = request.getParameter("txtCustName");
        String custAddress = request.getParameter("txtAddress");
        OrderInsertErr errors = new OrderInsertErr();

        //new DAO
        ProductDAO pdDAO = new ProductDAO();
        OrderDAO odDAO = new OrderDAO();
        Order_DetailsDAO od_dDAO = new Order_DetailsDAO();

        //create orderID
        //orderID format is ORDERx
        //x is number from 0 to maximum in table
        //if taken, generate another one and try checking again
        //if there are no more available IDs to assign
        //log to server and break
        String orderID = "ORDER";
        int count = 0;
        try {
            do {
                ++count;
                orderID = "ORDER" + count;
            } while (odDAO.isOrderIDTaken(orderID));
            //end while if available order ID is found
        } catch (SQLException ex) {
            log("CheckoutServlet _ SQLException: "
                    + ex.getMessage() + "\n", ex.getCause());
        } catch (NamingException ex) {
            log("CheckoutServlet _ NamingException: ", ex.getCause());
        }

        try {

            if (custName.trim().length() < 2 || custName.trim().length() > 50) {
                bErr = true;
                errors.setNameLengthErr("Name must have a length of 2-50 characters");
            }
            if (custAddress.trim().length() < 6
                    || custAddress.trim().length() > 30) {
                bErr = true;
                errors.setAddressLengthErr("Address must have a length of 6-30 characters");
            }
            if (bErr) {
                request.setAttribute("CHECKOUT_ERROR", errors);
            } else {
                //1. Cust goes to cart place
                HttpSession session = request.getSession(false);
                if (session != null) {
                    //2. Cust takes their cart
                    CartObj cart = (CartObj) session.getAttribute("CART");
                    if (cart != null) {
                        //3. Cust get items in cart
                        Map<String, Integer> items = cart.getItems();
                        if (items != null) {

                            //order table:
                            //orderID
                            //custName
                            //custAddress
                            //
                            //
                            //call method
                            boolean addOrder_result = odDAO.addOrder(orderID, custName, custAddress);
                            if (addOrder_result) {
                                //4. Traverse each item to save to DB
                                for (String title : items.keySet()) {

                                    //order_details table:
                                    //orderID (from order table)
                                    String productID = pdDAO.getProductIDFromName(title);
                                    int quantity = items.get(title);
                                    //
                                    //
                                    boolean addOrder_Details_result = od_dDAO.
                                            addOrderDetails(orderID, productID, quantity);
                                    if (addOrder_Details_result) {
                                        //5. Container destroy attribute
                                        session.removeAttribute("CART");
                                        //6. Cust view invoice and return to go shopping
                                        url = listUrl.get(INVOICE_PAGE);
                                    }//end if add order & order details successfully
                                }//end traverse items
                            } //end if add order (without details) successfully
                        } //end if items existed
                    } //end if cart existed
                } //end if session existed
            }//end if order information error existed
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            log("CheckoutServlet _ SQLException: ", ex.getCause());
            if (errMsg.contains("Order_Details")) {
                try {
                    od_dDAO.deleteOrderDetails(orderID);
                } catch (SQLException ex1) {
                    log("CheckoutServlet _ SQLException: ", ex1.getCause());
                    response.sendError(461);
                } catch (NamingException ex1) {
                    log("CheckoutServlet _ NamingException: ", ex1.getCause());
                    response.sendError(461);
                }
            }
            if (errMsg.contains("Order")) {
                try {
                    odDAO.deleteOrder(orderID);
                } catch (SQLException ex1) {
                    log("CheckoutServlet _ SQLException: ", ex1.getCause());
                    response.sendError(461);
                } catch (NamingException ex1) {
                    log("CheckoutServlet _ NamingException: ", ex1.getCause());
                    response.sendError(461);
                }
            }
            response.sendError(461);
        } catch (NamingException ex) {
            log("CheckoutServlet _ NamingException: ", ex.getCause());
            response.sendError(461);
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
