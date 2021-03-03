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
        <title>Search</title>
    </head>
    <body>
        <%
            Cookie cookies[] = request.getCookies();

            if (cookies != null) {
                String username = "";
                for (Cookie cookie : cookies) {
                    String temp = cookie.getName();
                    if (!temp.equals("JSESSIONID")) {
                        username = temp;
                    }
                }
                if (username.equals("")) {
        %>  
        <jsp:forward page="StartupServlet" />
        <%
            }
        %>
        Welcome, <%= username%>
        <%
            }

        %>
        <h1>Search Page</h1>
        <form action="DispatchServlet">
            <%                //
                String lastSearch = request.getParameter("txtSearchValue");
                if (lastSearch == null) {
                    lastSearch = "";
                }
            %>
            Search Value <input type="text" name="txtSearchValue" 
                                value="<%= lastSearch%>" /> <br/>
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

    <%        } else {
    %> 
    <h2>No record is matched.</h2>
    <%
            }
        } //end if searchValue has a value
%>
</body>
</html>
