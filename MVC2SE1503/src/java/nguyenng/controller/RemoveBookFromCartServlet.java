/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nguyenng.cart.CartObj;

/**
 *
 * @author bchao
 */
@WebServlet(name = "RemoveBookFromCartServlet", urlPatterns = {"/RemoveBookFromCartServlet"})
public class RemoveBookFromCartServlet extends HttpServlet {

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
    /*
        1. cust goes to cart place
        2. cust takes cart
        3. cust takes items
        4. cust looks at checked items names to remove []
        5. cust remove items from cart with name
        6. save cart to server
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Map<String, String> listUrl = (Map<String, String>) request
                .getServletContext()
                .getAttribute("URL_MAPPING");

        String url = listUrl.get(VIEW_CART_PAGE);

        try {
            //1. Cust goes to cart place
            // Cart ma user thay la o client
            // Truong hop session expire thi k phai tao session moi
            HttpSession session = request.getSession(false);
            if (session != null) {
                //2. Cust takes cust's cart
                CartObj cart = (CartObj) session.getAttribute("CART");
                if (cart != null) {
                    //3. Cust gets items from cart
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //4. Cust lay danh sach cac mon do can xoa
                        String[] selectedItems = request.
                                getParameterValues("chkItem");
                        if (selectedItems != null) {
                            for (String title : selectedItems) {
                                cart.removeItemFromCart(title);
                            }//end for removing items from cart
                            session.setAttribute("CART", cart);
                        }//end if items to remove existed
                    }//end if items existed
                }//end if cart existed
            }//end if session existed
            //6. Call View Cart function
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
