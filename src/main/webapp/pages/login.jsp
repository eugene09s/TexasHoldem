<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html lang="en">
<head>
    <title>Login</title>

    <c:import url="../pages/parts/header.jsp"/>
</head>
<body>
<%--<form name="loginForm" method="POST" action="controller">--%>

<%--    <input type="hidden" name="command" value="login"/>--%>
<%--    Login:<br/>--%>
<%--    <input type="text" name="login" value=""/>--%>
<%--    <br/>Password:<br/>--%>
<%--    <input type="password" name="password" value=""/>--%>
<%--    <br/>--%>
<%--    ${errorLoginPassMessage}--%>
<%--    <br/>--%>
<%--    ${wrongAction}--%>
<%--    <br/>--%>
<%--    ${nullPage}--%>
<%--    <br/>--%>
<%--    <input type="submit" value="Log in"/>--%>
<%--</form>--%>
<c:import url="../pages/parts/navbar.jsp"/>

<form  name="loginForm" method="POST" action="controller" class="flex-box">
    <input type="hidden" name="command" value="login"/>
    <div class="mb-3">
        <label for="exampleInputEmail1" class="form-label">Email address</label>
        <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" name="command" value="login">
        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
    </div>
    <div class="mb-3">
        <label for="exampleInputPassword1" class="form-label">Password</label>
        <input type="password"  name="password" class="form-control" id="exampleInputPassword1">
    </div>
    <br/>
    ${errorLoginPassMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <button type="submit" name="Log in" class="btn btn-primary" >Submit</button>
</form>

</body>
</html>
