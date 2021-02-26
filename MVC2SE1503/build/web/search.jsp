<%-- 
    Document   : search
    Created on : Feb 25, 2021, 9:05:21 AM
    Author     : bchao
--%>

<%@page import="nguyenng.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Search Page</h1>
        <form action="DispatchServlet">
            Search Value <input type="text" name="txtSearchValue" value="<%= request.getParameter("txtSearchValue")%>" /> <br/>
            <input type="submit" value="Search" name="btAction" /> <!--Lay tu DispatchServlet -->
        </form> <br/>
        <%
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
                <tr>
                    <td>
                        <%= ++count%>
                        .</td>
                    <td>
                        <%= dto.getUsername()%>
                    </td>
                    <td>
                        <%= dto.getPassword()%>
                    </td>
                    <td>
                        <%= dto.getFullname()%>
                    </td>
                    <td>
                        <%= dto.isRole()%>
                    </td>
                    <td>
                        <a href="<%= urlRewriting %>">Delete</a>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <%        } else {
        %> 
        <h2>No record is matched.</h2>
        <%
                }
            } //end if searchValue has a value
%>
    </body>
</html>
