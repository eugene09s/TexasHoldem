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

<div class="container py-3">
    Request from ${pageContext.errorData.requestURI} is failed
    <br/>
    Servlet name or
    type: ${pageContext.errorData.servletName}
    <br/>
    Status code: ${pageContext.errorData.statusCode}
    <br/>
    Exception: ${pageContext.errorData.throwable}
    Error: ${errorMessage}
    <h2><a href="/jsp/pages/home.jsp"></a></h2>
</div>
</body>
</html>
