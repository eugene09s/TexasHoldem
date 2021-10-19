<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

<div class="container">
    <span>
        <h2>Texas Holdem Poker</h2>
        <p><a href="https://academypoker.ru/rules-poker.html">Rules</a></p>
        <br/>
		<p>
			<fmt:message key="poker.rules.text"/>
		</p>
    </span>
    <h2>Poker Hands</h2>
    <img class="w-100" src="/images/rules/pokerHands.png">
</div>

</body>
</html>
