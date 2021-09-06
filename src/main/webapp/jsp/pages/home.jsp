<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 29.08.2021
  Time: 22:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
<%--    <title><fmt:message key="title.main"/></title>--%>
    <c:import url="/jsp/parts/head.jsp"/>
	<title><fmt:message key="label.title"/></title>
</head>
<body>
<c:import url="/jsp/parts/navbar.jsp"/>

<div class="container">
    <span>
        <h2>Texas Poker</h2>
        ${pageContext.request.contextPath}
        ${sessionScope.lang}
        <p>rules:</p>
        <br/>
		<p>
			<fmt:message key="poker.rules.text"/>
		</p>
    </span>
    <h2>Poker Hands</h2>
    <img src="/images/rules/pokerHands.png">
</div>


<%--<h3>Welcome</h3>--%>
<%--<hr/>--%>
<%--${user}, hello!--%>
<%--<hr/>--%>
<%--<a href="controller?command=logout">Logout</a>--%>


</body>
</html>
