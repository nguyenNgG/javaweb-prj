<%-- 
    Document   : viewCart
    Created on : Mar 4, 2021, 9:39:57 AM
    Author     : bchao
--%>

<%@page import="java.util.Map"%>
<%@page import="nguyenng.cart.CartObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
    </head>
    <body>
        <h1>Your cart has: </h1>
        <%
            //1. Cust goes to cust's cart place
            //jsp support san~ session, getSession o dang false
            //getSession get false de khong de` vung nho cua nguoi dung
            if (session != null) {
                //urlRewriting for when cust need to continue shopping
                String urlRewriting = "DispatchServlet"
                        + "?btAction=View+Bookstore";
                //2. Cust take cust's cart
                CartObj cart = (CartObj) session.getAttribute("CART");
                //cart is a pointer
                if (cart != null) {
                    //3. Cust gets items to check
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
        %> 
        <table border="1">
            <thead>
                <tr>
                    <th>No. </th>
                    <th>Title</th>
                    <th>Quantity</th>
                    <th>Action</th>
                </tr>
            </thead>
            <form action="DispatchServlet">

                <tbody>
                    <%
                        //keySet tra ve mang String
                        int count = 0;
                        for (String title : items.keySet()) {
                    %> 
                    <tr>
                        <td><%= ++count%></td>
                        <td><%= title%></td>
                        <td><%= items.get(title)%></td>
                        <td> 
                            <input type="checkbox" name="chkItem"
                                   value="<%= title%>" /> 
                        </td>
                    </tr>
                    <%
                        }//for
                    %>
                    <tr>
                        <td colspan="3">
                            <a href="<%= urlRewriting%>">Add more items to Cart</a>
                        </td>
                        <td>
                            <input type="submit" 
                                   value="Remove Selected Items" name="btAction" />
                        </td>
                    </tr>
                </tbody>

            </form>
        </table>

        <form action="DispatchServlet" method="POST">
            <br/>
            Name   <input type="text" name="txtCustName" value="" />
            <br/>
            Address <input type="text" name="txtAddress" value="" />
            <br/><br/>
            <input type="submit" value="Checkout" name="btAction" />
        </form>

        <%  //                      
                        return;
                    } //end if items existed
                } //end if cart existed
            } //end if session existed
        %>
        <h2>No item exist in cart.</h2>
        <a href="DispatchServlet?btAction=View+Bookstore">Add more items to Cart</a>
    </body>
</html>