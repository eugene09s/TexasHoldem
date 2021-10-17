<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 29.08.2021
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <c:import url="/jsp/parts/head.jsp"/>
    <title>Error Page</title>
</head>
<body>
<c:import url="/jsp/parts/navbar.jsp"/>

<div class="container py-3 h4">
    <c:if test="${not empty pageContext.errorData.requestURI}">
        Request from ${pageContext.errorData.requestURI} is failed
        <br/>
    </c:if>
    <c:if test="${not empty pageContext.errorData.servletName}">
        Servlet name or type: ${pageContext.errorData.servletName}
        <br/>
    </c:if>
    <c:if test="${pageContext.errorData.statusCode != 0}">
        <span class="text-warning">Status code: </span>${pageContext.errorData.statusCode}
        <br/>
    </c:if>
    <c:if test="${not empty pageContext.errorData.throwable}">
        <span class="text-danger">Exception: </span>${pageContext.errorData.throwable}
        <br/>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <span class="text-danger">Error: </span>${errorMessage}
    </c:if>
    <h2><a href="/jsp/pages/home.jsp"></a></h2>
</div>
</body>
</html>
