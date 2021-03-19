/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
import nguyenng.order_details.Order_DetailsDTO;
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
        /*
            Order Table
            ID = orderID
            NAME = custName
            ADDRESS = custAddress
        
            --
            Order_Details Table
            ID = orderID
            Quantity = quantity
            PID = productID
        
            --
            Product Table
            PID = productID
        
         */

        //0. Cust information
        boolean bErr = false;
        String custName = request.getParameter("txtCustName");
        String custAddress = request.getParameter("txtAddress");

        OrderInsertErr errors = new OrderInsertErr();

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
                //Initialize productDAO and order_detailsDAO
                ProductDAO productDAO = new ProductDAO();
                Order_DetailsDAO order_detailsDAO = new Order_DetailsDAO();
                //call orderDAO, get orderID
                OrderDAO orderDAO = new OrderDAO();
                String orderID = "ORDER";
                int count = 0;
                do {
                    ++count;
                    orderID = "ORDER" + count;
                } while (orderDAO.isOrderIDTaken(orderID));
                //end while if available order ID is found

                // Product list (ProductID, Quantity)
                Map<String, Integer> productList = null;
                // 1. Customer goes to cart place
                HttpSession session = request.getSession();
                if (session != null) {
                    // 2. Customer takes cart (CartObj)
                    CartObj cart = (CartObj) session.getAttribute("CART");
                    if (cart != null) {
                        // 3. Customer takes items in cart
                        Map<String, Integer> items = cart.getItems();
                        if (items != null) {
                            // 4. Traverse each item in cart
                            for (String title : items.keySet()) {
                                int quantity = items.get(title);
                                String productID = productDAO
                                        .getProductIDFromName(title);
                                if (productList == null) {
                                    productList = new HashMap<>();
                                }
                                productList.put(productID, quantity);
                            } // end traversing items in cart
                            // insert order into table
                            boolean order_add_result = orderDAO
                                    .addOrder(orderID, custName, custAddress);
                            if (order_add_result) {
                                // insert order details
                                boolean order_details_add_result
                                        = order_detailsDAO
                                                .addOrderDetails(orderID, productList);
                                if (order_details_add_result) {
                                    //5. Container destroy attribute 
                                    session.removeAttribute("CART");
                                    
                                    //5a. Get purchased products by searching with orderID
                                    order_detailsDAO.searchOrder_Details(orderID);
                                    List<Order_DetailsDTO> orderList = order_detailsDAO.getOrder_Details_List();
                                    //5b. Get bookstore list
                                    //5c. Set attribute for invoice information
                                    request.setAttribute("CUST_ORDERID", orderID);
                                    request.setAttribute("CUST_CUSTNAME", custName);
                                    request.setAttribute("CUST_CUSTADDRESS", custAddress);
                                    request.setAttribute("CUST_ORDERINVOICE", orderList);
                                    //6. Cust view invoice and return to go shopping
                                    url = listUrl.get(INVOICE_PAGE);
                                } // end if added order details successfully
                                if (!order_details_add_result) {
                                    // removing order
                                    orderDAO.deleteOrder(orderID);
                                    response.sendError(561);
                                    return;
                                } // end if add order details failed
                            } // end if added order successfully
                        } // end if items existed in cart
                    } // end if cart existed
                } // end if session existed
            } // end if no customer input error found
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException ex) {
            log("CheckoutServlet _ SQLException: ", ex.getCause());
            response.sendError(561);
        } catch (NamingException ex) {
            log("CheckoutServlet _ NamingException: ", ex.getCause());
            response.sendError(561);
        } finally {
            out.close();
        }

//        //new DAO
//        ProductDAO pdDAO = new ProductDAO();
//        OrderDAO odDAO = new OrderDAO();
//        Order_DetailsDAO od_dDAO = new Order_DetailsDAO();
//
//        //create orderID
//        //orderID format is ORDERx
//        //x is number from 0 to maximum in table
//        //if taken, generate another one and try checking again
//        //if there are no more available IDs to assign
//        //log to server and break
//        String orderID = "ORDER";
//        int count = 0;
//        try {
//            do {
//                ++count;
//                orderID = "ORDER" + count;
//            } while (odDAO.isOrderIDTaken(orderID));
//            //end while if available order ID is found
//        } catch (SQLException ex) {
//            log("CheckoutServlet _ SQLException: "
//                    + ex.getMessage() + "\n", ex.getCause());
//        } catch (NamingException ex) {
//            log("CheckoutServlet _ NamingException: ", ex.getCause());
//        }
//
//        try {
//
//            if (custName.trim().length() < 2 || custName.trim().length() > 50) {
//                bErr = true;
//                errors.setNameLengthErr("Name must have a length of 2-50 characters");
//            }
//            if (custAddress.trim().length() < 6
//                    || custAddress.trim().length() > 30) {
//                bErr = true;
//                errors.setAddressLengthErr("Address must have a length of 6-30 characters");
//            }
//            if (bErr) {
//                request.setAttribute("CHECKOUT_ERROR", errors);
//            } else {
//                //1. Cust goes to cart place
//                HttpSession session = request.getSession(false);
//                if (session != null) {
//                    //2. Cust takes their cart
//                    CartObj cart = (CartObj) session.getAttribute("CART");
//                    if (cart != null) {
//                        //3. Cust get items in cart
//                        Map<String, Integer> items = cart.getItems();
//                        if (items != null) {
//
//                            //order table:
//                            //orderID
//                            //custName
//                            //custAddress
//                            //
//                            //
//                            //call method
//                            boolean addOrder_result = odDAO.addOrder(orderID, custName, custAddress);
//                            if (addOrder_result) {
//                                //4. Traverse each item to save to DB
//                                for (String title : items.keySet()) {
//
//                                    //order_details table:
//                                    //orderID (from order table)
//                                    String productID = pdDAO.getProductIDFromName(title);
//                                    int quantity = items.get(title);
//                                    //
//                                    //
//                                    boolean addOrder_Details_result = od_dDAO.
//                                            addOrderDetails(orderID, productID, quantity);
//                                    if (addOrder_Details_result) {
//                                        //5. Container destroy attribute
//                                        session.removeAttribute("CART");
//                                        //6. Cust view invoice and return to go shopping
//                                        url = listUrl.get(INVOICE_PAGE);
//                                    }//end if add order & order details successfully
//                                }//end traverse items
//                            } //end if add order (without details) successfully
//                        } //end if items existed
//                    } //end if cart existed
//                } //end if session existed
//            }//end if order information error existed
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
//        } catch (SQLException ex) {
//            String errMsg = ex.getMessage();
//            log("CheckoutServlet _ SQLException: ", ex.getCause());
//            if (errMsg.contains("Order_Details")) {
//                try {
//                    od_dDAO.deleteOrderDetails(orderID);
//                } catch (SQLException ex1) {
//                    log("CheckoutServlet _ SQLException: ", ex1.getCause());
//                    response.sendError(461);
//                } catch (NamingException ex1) {
//                    log("CheckoutServlet _ NamingException: ", ex1.getCause());
//                    response.sendError(461);
//                }
//            }
//            if (errMsg.contains("Order")) {
//                try {
//                    odDAO.deleteOrder(orderID);
//                } catch (SQLException ex1) {
//                    log("CheckoutServlet _ SQLException: ", ex1.getCause());
//                    response.sendError(461);
//                } catch (NamingException ex1) {
//                    log("CheckoutServlet _ NamingException: ", ex1.getCause());
//                    response.sendError(461);
//                }
//            }
//            response.sendError(461);
//        } catch (NamingException ex) {
//            log("CheckoutServlet _ NamingException: ", ex.getCause());
//            response.sendError(461);
//        } finally {
//
////            response.sendRedirect(url);
//            
//            out.close();
//        }
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
