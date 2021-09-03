<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html lang="en">
<head>
    <c:import url="/jsp/parts/head.jsp"/>
    <title>Login</title>
</head>
<body>

<c:import url="/jsp/parts/navbar.jsp"/>

<div class="container py-3">
    <div class="row flex-column">
        <form name="loginForm" method="POST" action="controller" class="flex-box col-md-6">
            <h1>Login</h1>
            <input type="hidden" name="command" value="login"/>
            <div class="mb-3">
                <span class="form-label">Login</span>
                <input type="text" class="form-control" minlength="8" maxlength="32" name="command">
                <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
            </div>
            <div class="mb-3">
                <span class="form-label">Password</span>
                <input type="password" name="password" class="form-control" minlength="8" maxlength="32" id="exampleInputPassword1">
            </div>
            <br/>
            ${errorLoginPassMessage}
            ${wrongAction}
            ${nullPage}
            <button type="submit" name="Log in" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>
</body>
</html>
