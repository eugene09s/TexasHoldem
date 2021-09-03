<%--
  Created by IntelliJ IDEA.
  User: evges
  Date: 03.09.2021
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html>
<head>
    <title>SignUp</title>
    <c:import url="/jsp/parts/head.jsp"/>
</head>
<body>
<c:import url="/jsp/parts/navbar.jsp"/>

<div class="container py-3">
    <div class="row flex-column">
        <form name="loginForm" method="POST" action="controller" class="flex-box col-md-6">
            <h1>Register</h1>
            <input type="hidden" name="command" value="sign-up"/>
            <div class="mb-3">
                <span class="form-label">Login</span>
                <input type="text" class="form-control" id="login" name="command" minlength="8" maxlength="32" required>
            </div>
            <div class="mb-3">
                <span class="form-label">Password</span>
                <input type="password" name="password" minlength="8" maxlength="32" class="form-control password" required>
            </div>
            <div class="mb-3">
                <span class="form-label">Repeat password</span>
                <input type="password" class="form-control password" minlength="8" maxlength="32" required>
                <span class="password-error d-none text-danger">Passwords not match</span>
            </div>
            <div class="mb-3">
                <span class="form-label">First name</span>
                <input type="text" name="firstName" minlength="4" maxlength="32" class="form-control" required>
            </div>
            <div class="mb-3">
                <span class="form-label">Last name</span>
                <input type="text" name="lastName" minlength="4" maxlength="32" class="form-control" required>
            </div>
            <div class="mb-3">
                <span class="form-label">Email</span>
                <input type="email" name="lastName" minlength="5" maxlength="64" class="form-control" required>
            </div>
            <div class="mb-3">
                <span class="form-label">Phone number</span>
                <input type="phone" name="lastName" minlength="7" maxlength="16" class="form-control">
            </div>
            <br/>
            ${errorLoginPassMessage}
            ${wrongAction}
            ${nullPage}
            <button type="submit" class="btn btn-primary">Register</button>
        </form>
    </div>
</div>
<script src="/js/sign-up.js"></script>
</body>
</html>
