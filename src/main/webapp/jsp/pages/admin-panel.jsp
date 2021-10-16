<%--
  Created by IntelliJ IDEA.
  User: evges
  Date: 11.10.2021
  Time: 14:42
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
</head>

<body>
<c:import url="/jsp/parts/navbar.jsp"/>

<div class="container py-3">
    <div class="table-responsive">
        <div class="table-wrapper">

            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th><fmt:message key="admin.user.id"/></th>
                    <th><fmt:message key="admin.user.login"/></th>
                    <th><fmt:message key="user.data.time"/></th>
                    <th><fmt:message key="user.role"/></th>
                    <th><fmt:message key="user.status"/></th>
                    <th><fmt:message key="admin.user.balance"/></th>
                </tr>
                </thead>
                <tbody class="table-of-users">
                <c:forEach items="${userDtoList}" var="userDto" varStatus="counter">
                    <tr>
                        <td id="id-user">${userDto.getId()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/poker?command=profile-page&id=${userDto.getId()}">
                                <img src="${pageContext.request.contextPath}/images/photo/${userDto.getPhoto()}"
                                     class="avatar rounded-circle mr-4" height="50px" width="50px"
                                     alt="Avatar">${userDto.getLogin()}
                            </a>
                        </td>
                        <td>${userDto.getCreateTime()}</td>
                        <td>${userDto.getUserRole()}</td>
                        <td id="status-part">
                            <span id="status-dot" class="status
<c:choose>
<c:when test="${userDto.getUserStatus() == 'ACTIVE'}">text-success</c:when><c:otherwise>text-danger</c:otherwise>
</c:choose> mr-2 h2">•</span>
                            <span class="status-user">${userDto.getUserStatus()}</span>
                            <button type="button" class="btn border btn-action-ban
<c:choose>
<c:when test="${userDto.getUserStatus() == 'ACTIVE'}">btn-outline-danger ml-4</c:when>
<c:otherwise>btn-outline-success ml-2</c:otherwise>
</c:choose>">
                                <c:choose>
                                    <c:when test="${userDto.getUserStatus() == 'ACTIVE'}">BAN</c:when>
                                    <c:otherwise>UNBAN</c:otherwise>
                                </c:choose>
                            </button>
                        </td>
                        <td>
                            <div class="input-group">
                                <button type="button" class="btn btn-light border text-warning font-weight-bold btn-minus">-</button>
                                <input type="number" id="input-money" maxlength="7" minlength="1" class="border border-primary rounded" value="${userDto.getBalance()}" data-decimals="2" min="0" max="10000"
                                       step="0.1">
                                <button type="button" class="btn border btn-light text-warning font-weight-bold btn-plus">+</button>
                                <button type="button" class="btn btn-primary ml-2 btn-action-save-balance"><fmt:message key="admin.save"/></button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <c:if test="${userDtoList.size() != 0}">
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
                                       href="${pageContext.request.contextPath}/poker?command=admin-panel-page&p=<c:out value = "${i}"/>&s=${amountOfPage}">
                                        <c:out value="${i}"/>
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>

        </div>
    </div>
</div>

<script src="/js/admin-panel.js"></script>
</body>
</html>
