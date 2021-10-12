<%--
  Created by IntelliJ IDEA.
  User: evges
  Date: 11.10.2021
  Time: 23:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang" />">

<head>
    <c:import url="/jsp/parts/head.jsp"/>
    <title>
        <fmt:message key="label.title"/>
    </title>
    <link rel="stylesheet" href="/css/statistic-games.css">
</head>

<body>
<c:import url="/jsp/parts/navbar.jsp"/>

<div class="container py-3">

    <c:forEach items="${statisticResultGameList}" var="statisticResultGame" varStatus="counter">
        <div class="col-lg-12">
            <div class="card card-margin">
                <div class="card-header no-border">
                    <h5 class="card-title">
                        <span># ${statisticResultGame.game.gameId}</span>
                        <div><fmt:message key="game.title"/>: <span class="h6">${statisticResultGame.game.title}</span>
                        </div>
                        <div><fmt:message key="game.date"/>: <span class="h6">${statisticResultGame.game.date}</span>
                        </div>
                        <div><fmt:message key="game.bank"/>: <span class="h6">${statisticResultGame.game.bank}$</span>
                        </div>
                    </h5>
                </div>
                <div class="card-body pt-0 ">
                    <span><fmt:message key="game.cards"/>: ${statisticResultGame.game.fiveCards}</span>

                    <c:forEach items="${statisticResultGame.getGamePlayers()}" var="gamePlayer" varStatus="i">
                        <div class="gambler">
                                <span>
                                    ${i.index})
                                    <a href="${pageContext.request.contextPath}/poker?command=profile-page&id=${gamePlayer.userId}"><fmt:message
                                            key="game.gambler"/></a>
                                </span>
                            <span>${gamePlayer.lastAction}</span>
                            <span>${gamePlayer.twoCards}</span>
                            <span>${gamePlayer.combinationsCards}</span>
                        </div>
                    </c:forEach>

                    <c:forEach items="${statisticResultGame.getGameWinners()}" var="gameWinners" varStatus="j">
                        <div class="win-gambler border-secondary">
                            <img src="https://img.icons8.com/office/40/000000/win.png"/>
                            <span class="ml-2">
                                    <a href="${pageContext.request.contextPath}/poker?command=profile-page&id=${gameWinners.userId}"><fmt:message
                                            key="game.winner"/></a>
                                </span>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </div>
    </c:forEach>

    <c:if test="${statisticResultGameList.size() != 0}">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:forEach var="i" begin="1" end="${maxPage}">
                    <c:if test="${i == currentPage+4}">
                        <li class="page-item">
                            <a class="page-link">...</a>
                        </li>
                    </c:if>
                    <c:if test="${(((currentPage-1) == i) || ((i < currentPage+3) && (i > currentPage-3))) || (i > maxPage-2) || (i == 1)}">
                        <li class="page-item <c:if test="${currentPage == i}">active</c:if>">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/poker?command=statistic-games-page&p=<c:out value = "${i}"/>&s=${amountOfPage}">
                                <c:out value="${i}"/>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </nav>
    </c:if>

</div>

</body>
</html>
