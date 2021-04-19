<%-- 
    Document   : orderReceipt
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
        <h1>Thank you for your order, please check your receipt: </h1>
        <table border="1">
            <thead>
                <tr>
                    <th>
                        Your order ID: ${requestScope.CUSTOMER_ORDERID}
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        Your name: ${requestScope.CUSTOMER_CUSTNAME} <br/>
                        Your address: ${requestScope.CUSTOMER_CUSTADDRESS}
                    </td>
                </tr>
            </tbody>
        </table>
        <br/>
        <table border="1">
            <thead>
                <tr>
                    <th>No. </th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="receipt" value="${requestScope.CUSTOMER_RECEIPT}"/>
                <c:set var="productNameList" value="${requestScope.CUSTOMER_PRODUCTNAMES}"/>
                <c:forEach var="dto" items="${receipt}" varStatus="counter">
                    <tr>
                        <td>${counter.count}</td>
                        <td>${productNameList[dto.productID]}</td>
                        <td>${dto.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <br/>
        <a href="viewBookstore">Click here to return to shopping.</a>
    </body>
</html>
