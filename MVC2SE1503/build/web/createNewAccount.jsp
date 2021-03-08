<%-- 
    Document   : createNewAccount
    Created on : 6 Mar 2021, 22:27:00
    Author     : bchao
--%>

<%@page import="nguyenng.registration.RegistrationInsertError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <div>Create a New Account</div>
        <form action="DispatchServlet" method="POST">
            <c:set var="errors" value="${requestScope.INSERT_ERRS}"/>
            Username* <input type="text" name="txtUsername"
                             value="${param.txtUsername}" /> (6-12 chars.) <br/>
            <c:if test="${not empty errors}">
                <c:if test="${not empty errors.usernameLengthErr}">
                    <font color="red">
                    ${errors.usernameLengthErr}
                    </font> <br/>
                </c:if>
            </c:if>
            Password* <input type="text" name="txtPassword"
                             value="${param.txtPassword}" /> (6-30 chars.) <br/>
            Confirm* <input type="text" name="txtConfirm"
                            value="${param.txtConfirm}" /> <br/>
            Full Name* <input type="text" name="txtFullName"
                              value="${param.txtFullName}" /> (2-50 chars.) <br/>
            <input type="submit" value="Sign Up" name="btAction" />
            <input type="reset" value="Reset" /> <br/>
            <a href="DispatchServlet">Click here to return to login page.</a>
        </form>
        <%--
        <form action="DispatchServlet" method="POST">
            Username* <input type="text" name="txtUsername"
                             value="<%= request.getParameter("txtUsername")%>" /> (6-12 chars.)
            <br/>

            <%
                RegistrationInsertError errors
                        = (RegistrationInsertError) request.getAttribute("INSERT_ERRS");
                
                if (errors != null) {
                    if (errors.getUsernameLengthErr() != null) {
            %>  
            <font color="red">
            <%= errors.getUsernameLengthErr()%> 
            </font><br/>
            <%
                    }//end if usernamelenerr existed 
                }//end if errors existed
                if (errors != null) {
                    if (errors.getUsernameIsExistedErr() != null) {
            %>  
            <font color="red">
            <%= errors.getUsernameIsExistedErr()%> 
            </font><br/>
            <%
                    }//end if pwdlenerr existed
                }//end if errors existed
            %>
            Password* <input type="password" name="txtPassword" value="" /> (6-30 chars.)
            <br/>
            <%    
                if (errors != null) {
                    if (errors.getPasswordLengthErr() != null) {
            %>  
            <font color="red">
            <%= errors.getPasswordLengthErr()%> 
            </font><br/>
            <%
                    }//end if pwdlenerr existed
                }//end if errors existed
            %>
            Confirm* <input type="password" name="txtConfirm" value="" /> 
            <br/>
            <%    
                if (errors != null) {
                    if (errors.getConfirmNotMatchErr() != null) {
            %>  
            <font color="red">
            <%= errors.getConfirmNotMatchErr()%> 
            </font><br/>
            <%
                    }//end if pwdnomatcherr existed
                }//end if errors existed
                %>
            Full Name* <input type="text" name="txtFullName"
                              value="<%= request.getParameter("txtFullName")%>" /> (2-50 chars.)
            <br/>
            <%    
                if (errors != null) {
                    if (errors.getFullNameLengthErr() != null) {
            %>  
            <font color="red">
            <%= errors.getFullNameLengthErr()%> 
            </font><br/>
            <%
                    }//end if fullnamelenerr existed
                }//end if errors existed
                %>
            <input type="submit" value="Sign Up" name="btAction" />
            <input type="reset" value="Reset" />
        </form>
        --%>
    </body>
</html>
