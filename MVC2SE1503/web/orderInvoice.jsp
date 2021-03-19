<%-- 
    Document   : orderInvoice
    Created on : 6 Mar 2021, 14:53:03
    Author     : bchao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
    </head>
    <body>
        <h1>Thank you for your order, please check your invoice: </h1>
        <h3>Your order ID: ${requestScope.CUST_ORDERID}</h3>
        <h4>
            Your name: ${requestScope.CUST_CUSTNAME} <br/>
            Your address: ${requestScope.CUST_CUSTADDRESS}
        </h4>
        <table border="1">
            <thead>
                <tr>
                    <th>No. </th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="invoice" value="${requestScope.CUST_ORDERINVOICE}"/>
                <c:forEach var="dto" items="${invoice}" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td>${dto.productID}</td>
                        <td>${dto.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br/>
        <a href="viewBookstore">Click here to return to shopping.</a>
    </body>
</html>
