<%-- 
    Document   : searchOrder
    Created on : 14-Mar-2021, 18:23:04
    Author     : bchao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <h2 style="color:rgb(20,50,180)">
            Welcome, ${sessionScope.USER_FULLNAME}
            <form action="logout">
                <input type="submit" value="Logout" name="btAction" />
            </form>
        </h2>
        <a href="viewBookstore">Click here to view bookstore.</a> <br/>
        <a href="searchPage">Click here to view search page.</a> <br/> <br/>
        <form action="searchOrder">
            Search Value <input type="text" name="txtSearchOrderValue" 
                                value="${param.txtSearchOrderValue}" /> <br/>
            <input type="submit" value="Search Order" name="btAction" /> <!--Lay tu DispatchServlet -->
        </form> <br/>

        <c:set var="searchOrderValue" value="${param.txtSearchOrderValue}"/>
        <c:if test="${not empty searchOrderValue}">
            <%--List<>--%>
            <c:set var="order_result" value="${requestScope.SEARCH_ORDER}"/>
            <%--List<>--%>
            <c:set var="order_details_result" value="${requestScope.SEARCH_ORDER_DETAILS}"/>
            <c:if test="${not empty order_result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No. </th>
                            <th>Order ID</th>
                            <th>Name</th>
                            <th>Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order_dto" items="${order_result}" varStatus="counter">
                            <tr>
                                <td>
                                    ${counter.count}
                                    .</td>
                                <td>
                                    ${order_dto.orderID}
                                </td>
                                <td>
                                    ${order_dto.custName}
                                </td>
                                <td>
                                    ${order_dto.custAddress}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if><%----%>
            <c:if test="${empty order_result}">
                No record matched.
            </c:if>
            <c:if test="${not empty order_details_result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No. </th>
                            <th>Order ID</th>
                            <th>Product ID</th>
                            <th>Quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order_details_dto" items="${order_details_result}" varStatus="counter">
                            <tr>
                                <td>
                                    ${counter.count}
                                    .</td>
                                <td>
                                    ${order_details_dto.orderID}
                                </td>
                                <td>
                                    ${order_details_dto.productID}
                                </td>
                                <td>
                                    ${order_details_dto.quantity}
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if><%----%>
            <c:if test="${empty order_details_result}">
                No record matched.
            </c:if>
        </c:if><%----%>
    </body>
</html>
