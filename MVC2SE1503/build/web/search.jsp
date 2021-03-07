<%-- 
    Document   : search
    Created on : Feb 25, 2021, 9:05:21 AM
    Author     : bchao
--%>

<%@page import="nguyenng.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <c:set var="cookies" value="${cookie}"/>
        <c:if test="${not empty cookies}">
            <c:set var="username" value=""/>
            <c:forEach var="currentCookie" items="${cookies}" >
                <c:set var="temp" value="${currentCookie.value.name}" />
                <c:if test="${temp != 'JSESSIONID'}">
                    <c:set var="username" value="${temp}"/>
                </c:if>
            </c:forEach>
            <c:if test="${username == ''}">
                <jsp:forward page="StartupServlet" />
            </c:if>
        </c:if>
        Welcome, ${username} <br/>
        <a href="createNewAccount.jsp">Click here to go to registration page.</a>
        <h1>Search Page</h1>
        <form action="DispatchServlet">
            Search Value <input type="text" name="txtSearchValue" 
                                value="${param.txtSearchValue}" /> <br/>
            <input type="submit" value="Search" name="btAction" /> <!--Lay tu DispatchServlet -->
        </form> <br/>

        <c:set var="searchValue" value="${param.txtSearchValue}"/>
        <c:if test="${not empty searchValue}">
            <!--searchValue!=null-->
            <c:set var="result" value="${requestScope.SEARCH_RESULT}" />
            <!--List<RegistrationDTO>-->
            <c:if test="${not empty result}"> 
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full Name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}" varStatus="counter">
                        <form action="DispatchServlet" method="POST">
                            <tr>
                                <td>
                                    ${counter.count}
                                    .
                                </td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername"
                                           value="${dto.username}" />
                                </td>
                                <td>
                                    <input type="text" name="txtPassword"
                                           value="${dto.password}" />
                                </td>
                                <td>
                                    ${dto.fullname}
                                </td>
                                <td>
                                    ${dto.role}
                                </td>
                                <td>
                                    <c:set var="urlRewriting" value="DispatchServlet?btAction=del&pk=${dto.username}&lastSearch=${searchValue}"/>
                                    <a href="${urlRewriting}">Delete</a>
                                </td>
                                <td>
                                    <input type="hidden" name="lastSearchValue"
                                           value="${searchValue}" /> 
                                    <!-- ^ Control an de nhac server-->
                                    <input type="submit" value="Update" name="btAction" />
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty result}">
            <h2>No record matched.</h2>
        </c:if>
    </c:if>
    <%-- <%
        String searchValue = request.getParameter("txtSearchValue");
        if (searchValue != null) {
            List<RegistrationDTO> result
                    = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
            if (result != null) {
    %>
    <table border="1">
        <thead>
            <tr>
                <th>No.</th>
                <th>Username</th>
                <th>Password</th>
                <th>Full Name</th>
                <th>Role</th>
                <th>Delete</th>
                <th>Update</th>
            </tr>
        </thead>
        <tbody>
            <%
                int count = 0;
                for (RegistrationDTO dto : result) {
                    String urlRewriting = "DispatchServlet"
                            + "?btAction=del"
                            + "&pk=" + dto.getUsername()
                            + "&lastSearch=" + searchValue;
            %> 
        <form action="DispatchServlet" method="POST">

                <tr>
                    <td>
                        <%= ++count%>
                        .</td>
                    <td>
                        <%= dto.getUsername()%>
                        <input type="hidden" name="txtUsername"
                               value="<%= dto.getUsername()%>" />
                    </td>
                    <td>
                        <input type="text" name="txtPassword"
                               value="<%= dto.getPassword()%>" />
                    </td>
                    <td>
                        <%= dto.getFullname()%>
                    </td>
                    <td>
                        <input type="checkbox" name="chkAdmin" value="ON" 
                               <%
                                   if (dto.isRole()) {
                               %> 
                               checked
                               <%
                                   }//end if check role
                               %>
                               />
                    </td>
                    <td>
                        <a href="<%= urlRewriting%>">Delete</a>
                    </td>
                    <td>
                        <input type="hidden" name="lastSearchValue"
                               value="<%= searchValue%>" /> 
                        <!-- ^ Control an de nhac server-->
                        <input type="submit" value="Update" name="btAction" />
                    </td>
                </tr>

            </form>
            <%
                }
            %>
        </tbody>
    </table>

    <%        }//end if result existed
    else {
    %> 
    <h2>No record is matched.</h2>
    <%
            }
        } //end if searchValue has a value
    %> --%>
</body>
</html>
