<%-- 
    Document   : bookstore
    Created on : 5 Mar 2021, 21:54:00
    Author     : bchao
--%>

<%--
<%@page import="nguyenng.product.ProductDTO"%>
<%@page import="java.util.List"%>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bookstore</title>
    </head>
    <body>
        <h1>Welcome, please choose products.</h1>
        <a href="default">Click here to exit shopping.</a> <br/>
        <br/>
        <form action="viewCart">
            <input type="submit" value="View Cart" />
        </form>
        <br/>
        <%--List<ProductDTO>--%>
        <c:set var="result" value="${requestScope.BOOKSTORE_VIEW}"/>
        <c:if test="${not empty result}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No. </th>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Cart</th>
                    </tr>
                </thead>
                <tbody>
                    <%--ProductDTO--%>
                    <c:forEach var="dto" items="${result}" varStatus="counter">
                    <form action="addBook">
                        <tr>
                            <td>
                                ${counter.count}
                                .</td>
                            <td>
                                ${dto.productID}
                            </td>
                            <td>
                                ${dto.productName}
                                <input 
                                    type="hidden" 
                                    name="txtProductName" 
                                    value="${dto.productName}" />
                            </td>
                            <td>
                                <input type="submit" 
                                       value="Add to Cart" name="btAction" />
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>

    </c:if>
    <c:if test="${empty result}">
        <h2>No products available.</h2>
    </c:if>
    <%--
    <%
        List<ProductDTO> result
                = (List<ProductDTO>) request.getAttribute("BOOKSTORE_VIEW");
        if (result != null) {
    %> 
    <table border="1">
        <thead>
            <tr>
                <th>No. </th>
                <th>Product ID</th>
                <th>Product Name</th>
                <th>Cart</th>
            </tr>
        </thead>
        <tbody>
            <%  //                  
                int count = 0;
                for (ProductDTO dto : result) {
            %>
        <form action="DispatchServlet" method="POST">
            <tr>
                <td>
                    <%= ++count%>
                </td>
                <td>
                    <%= dto.getProductID()%>
                </td>
                <td>
                    <%= dto.getProductName()%>
                    <input type="hidden" name="txtPName" 
                           value="<%= dto.getProductName()%>" />
                </td>
                <td>
                    <input type="submit" 
                           value="Add to Cart" name="btAction" />
                </td>
            </tr>
        </form>
        <%
            }//end for
        %>
    </tbody>
</table>

    <%
    }//end if result existed
    else {
    %> 
    <h2>No products available.</h2>
    <%
        }
    %>--%>
</body>
</html>
