<%--
  Created by IntelliJ IDEA.
  User: evges
  Date: 03.09.2021
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="locale" />

<html lang="<fmt:message key="html.lang" />">

<head>
    <c:import url="/jsp/parts/head.jsp" />
    <title>
        <fmt:message key="label.title" />
    </title>
</head>

<body>
<c:import url="/jsp/parts/navbar.jsp" />

<div class="container py-3">
    <div class="table-responsive">
        <div class="table-wrapper">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th><fmt:message key="user.name"/></th>
                    <th><fmt:message key="user.data.time"/></th>
                    <th><fmt:message key="user.role"/></th>
                    <th><fmt:message key="user.status"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userDtoList}" var="userDto" varStatus="counter">
                    <tr>
                        <td>${counter.index+1}</td>
                        <td><a href="${pageContext.request.contextPath}/poker?command=profile-page&id=${userDto.getId()}">
                            <img src="${pageContext.request.contextPath}/images/photo/${userDto.getPhoto()}" class="avatar rounded-circle mr-4" height="50px" width="50px" alt="Avatar">${userDto.getFirstName()} ${userDto.getLastName()}
                        </a></td>
                        <td>${userDto.getCreateTime()}</td>
                        <td>${userDto.getUserRole()}</td>
                        <td>
                            <span class="status text-success mr-2 h3">•</span>
                            <span>${userDto.getUserStatus()}</span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <c:if test="${userDtoList.size() != 0}">
                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                        <c:forEach var = "i" begin = "1" end = "${maxPage}">
                        <li class="page-item <c:if test="${currentPage == i}">active</c:if>">
                            <a class="page-link" href="${pageContext.request.contextPath}/poker?command=users-page&p=<c:out value = "${i}"/>&s=${amountOfPage}">
                                <c:out value = "${i}"/>
                            </a>
                        </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>

        </div>
    </div>
</div>

</body>
</html>