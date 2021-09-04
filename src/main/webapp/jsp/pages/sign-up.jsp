<%--
  Created by IntelliJ IDEA.
  User: evges
  Date: 03.09.2021
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <c:import url="/jsp/parts/head.jsp"/>
    <title><fmt:message key="label.title"/></title>
</head>
<body>

<c:import url="/jsp/parts/navbar.jsp"/>

<div class="container py-3">
    <div class="row flex-column">
        <form name="loginForm" method="POST" action="controller" class="flex-box col-md-6">
            <h1><fmt:message key="signup.reg"/></h1>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.login"/></span>
                <input type="text" class="form-control" name="login" id="loginRegForm" minlength="8" maxlength="32" required>
                <span class="login-error d-none text-danger"><fmt:message key="signup.error.login.isalready"/></span>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.pass"/></span>
                <input type="password" name="password" minlength="8" maxlength="32" class="form-control password" required>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.repeat.pass"/></span>
                <input type="password" class="form-control password" minlength="8" maxlength="32" required>
                <span class="password-error d-none text-danger"><fmt:message key="signup.repeat.pass.error"/></span>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.first.name"/></span>
                <input type="text" name="firstName" minlength="4" maxlength="32" class="form-control" required>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.last.name"/></span>
                <input type="text" name="lastName" minlength="4" maxlength="32" class="form-control" required>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.email"/></span>
                <input type="email" name="email" minlength="5" maxlength="64" class="form-control" required>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="signup.pass"/></span>
                <input type="phone" name="phoneNumber" minlength="7" maxlength="18" class="form-control">
            </div>
            <br/>
            ${errorLoginPassMessage}
            ${wrongAction}
            ${nullPage}
            <button type="submit" class="btn btn-primary"><fmt:message key="signup.btn.submit"/></button>
        </form>
    </div>
</div>
<script src="/js/sign-up.js"></script>
</body>
</html>
