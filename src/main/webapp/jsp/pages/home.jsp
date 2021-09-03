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
<c:set var="locale" value="${not empty sessionScope.locale ? sessionScope.locale : 'ru_RU'}"/>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="content"/>

<html>
<head>
<%--    <title><fmt:message key="title.main"/></title>--%>
    <c:import url="parts/head.jsp"/>
</head>
<body>
<c:import url="parts/navbar.jsp"/>

<div class="container">
    <span>
        <h2>Texas Poker</h2>
        <p>rules:</p>
        <br/>
		<p>Итак, как играть в техасский холдем? В холдеме игроки пытаются собрать лучшую комбинацию из пяти карт в соответствии с покерными комбинациями.
			В холдеме каждому игроку раздаются две карты лицевой стороной вниз, затем в течение последующих раундов раздаются еще пять карт.
			Эти открытые карты называются «общими картами», потому что каждый игрок использует их, чтобы собрать руку. Пять общих карт раздаются в три этапа.
			Первые три общие карты называются «флоп». Затем раздаётся только одна карта, называемая «терн». Наконец, раздаётся еще одна карта, пятая и последняя общая карта - «ривер».
			Игроки собирают комбинации, используя лучшие доступные пять карт из семи общих карт (две открытые карты и пять общих карт).
			Это можно сделать, используя как открытые карты в комбинации с тремя общими картами, так и с одной картой в комбинации с четырьмя общими картами или используя все пять общих карт.
		</p>
    </span>
</div>


<h3>Welcome</h3>
<hr/>
${user}, hello!
<hr/>
<a href="controller?command=logout">Logout</a>


</body>
</html>
