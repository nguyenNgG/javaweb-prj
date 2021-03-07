<%-- 
    Document   : orderInvoice
    Created on : 6 Mar 2021, 14:53:03
    Author     : bchao
--%>

<%@page import="nguyenng.cart.CartObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
    </head>
    <body>
        <h1>Thank you for your order, please check your invoice: </h1>
        <%
            String url = "DispatchServlet"
                    + "?btAction=View+Bookstore";
        %>
        [TO-DO INVOICE]
        <a href=<%= url%>>Click here to return to shopping.</a>
        
    </body>
</html>
