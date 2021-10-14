<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <form method="POST" name="loginForm"  action="${pageContext.request.contextPath}/poker?command=login" class="flex-box col-md-6">
            <h1><fmt:message key="login.log.in"/></h1>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="login.login"/></span>
                <input type="text" class="form-control" minlength="8" maxlength="32" name="login" value="${login}" required>
                <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
            </div>
            <div class="mb-3">
                <span class="form-label"><fmt:message key="login.password"/></span>
                <input type="password" id="currentPass" name="password" class="form-control" minlength="8" maxlength="32" required>
                <input type="checkbox" onclick="showPass()"> <fmt:message key="settings.current.password.show"/>
                <script>
                    const currentPass = document.getElementById('currentPass');
                    function showPass() {
                        if (currentPass.type === "password") {
                            currentPass.type = "text";
                        } else {
                            currentPass.type = "password";
                        }
                    }
                </script>
            </div>
            <h3 class="text-danger error-message">
                <fmt:message key="login.error.${errorMessage}"/>
            </h3>
            <br/>
            <button type="submit" name="Log in" class="btn btn-primary"><fmt:message key="login.submit"/></button>
        </form>
    </div>
</div>
</body>
</html>
