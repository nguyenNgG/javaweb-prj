<%-- 
    Document   : createNewAccount
    Created on : 13-Mar-2021, 09:16:28
    Author     : bchao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Account</title>
    </head>
    <body>
        <h1>Create a new Account</h1>
        <form action="createAccount" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERROR}"/>
            Username <input type="text" name="txtUsername" value="${param.txtUsername}" /> (6-30 chars.) <br/> 
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                ${errors.usernameLengthErr} <br/>
                </font>
            </c:if>
            Password <input type="password" name="txtPassword" value="" /> (6-20 chars.) <br/> 
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                ${errors.passwordLengthErr} <br/>
                </font>
            </c:if>
            Confirm <input type="password" name="txtConfirm" value="" /> <br/>
            <c:if test="${not empty errors.confirmNotMatchErr}">
                <font color="red">
                ${errors.confirmNotMatchErr} <br/>
                </font>
            </c:if>
            Full Name <input type="text" name="txtFullname" value="${param.txtFullname}" /> (2-50 chars.) <br/>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                ${errors.fullnameLengthErr} <br/>
                </font>
            </c:if>
            <input type="submit" value="Create New Account" name="btAction" />
            <input type="reset" value="Reset" />
        </form>
        <c:if test="${not empty errors.usernameIsExistedErr}">
            <font color="red">
            ${errors.usernameIsExistedErr} <br/>
            </font>
        </c:if> <br/>
        <a href="default">Click here to exit creating account.</a>
    </body>
</html>
