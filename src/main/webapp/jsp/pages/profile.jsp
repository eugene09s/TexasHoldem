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
    <h3 class="text-danger error-message">
        <fmt:message key="settings.success.${successMessage}"/>
    </h3>
    <div class="row gutters-sm">
        <div class="col-md-4 mb-3">
            <div class="card">
                <div class="card-body">
                    <div class="d-flex flex-column align-items-center text-center">
                        <img src="${pageContext.request.contextPath}/images/photo/${profilePlayer.photo}" alt="notPhoto"
                             class="rounded-circle" width="200">
                        <div class="mt-3">
                            <h4>${user.getLogin()}</h4>
                            <p class="mb-1 h5 <c:choose>
<c:when test="${user.userStatus == 'ACTIVE'}">text-success</c:when>
<c:otherwise>text-danger</c:otherwise>
</c:choose>">${user.userStatus}</p>
                            <p class="text-muted font-size-sm h5">${user.userRole}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card mt-3">
                <ul class="list-group list-group-flush">
                    <c:if test="${requestScope.user.userId == sessionScope.userId}">
                        <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                            <h6 class="mb-0">
                                <img src="https://img.icons8.com/office/32/000000/money--v2.png"/> <fmt:message key="profile.your.balance"/>
                            </h6>
                            <span class="text-secondary">${user.getBalance()} $</span>
                        </li>
                    </c:if>
                    <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                        <h6 class="mb-0">
                            <img src="https://img.icons8.com/external-vitaliy-gorbachev-flat-vitaly-gorbachev/32/000000/external-award-online-learning-vitaliy-gorbachev-flat-vitaly-gorbachev.png"/>
                            <fmt:message key="profile.award"/>
                        </h6>
                        <span class="text-secondary">${profilePlayer.getAward()}</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                        <h6 class="mb-0">
                            <img src="https://img.icons8.com/color/32/000000/prize-money.png"/> <fmt:message key="profile.best.prize"/>
                        </h6>
                        <span class="text-secondary">${profilePlayer.getBestPrize()} $</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                        <h6 class="mb-0">
                            <img src="https://img.icons8.com/ios-filled/32/000000/money--v1.png"/> <fmt:message key="profile.lost.money"/>
                        </h6>
                        <span class="text-secondary">${profilePlayer.getLostMoney()} $</span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center flex-wrap">
                        <h6 class="mb-0">
                            <img src="https://img.icons8.com/material-rounded/32/000000/money-bag.png"/> <fmt:message key="profile.win.money"/>
                        </h6>
                        <span class="text-secondary">${profilePlayer.getWinMoney()} $</span>
                    </li>
                </ul>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card mb-3">
                <div class="card-body">
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">
                                <fmt:message key="profile.full.name"/>
                            </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">${user.getFirstName()} ${user.getLastName()}</div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">
                                <fmt:message key="profile.email"/>
                            </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">${user.getEmail()}</div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">
                                <fmt:message key="profile.phone"/>
                            </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">${user.getPhoneNumber()}</div>
                    </div>
                    <hr>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">
                                <fmt:message key="profile.about.yourself"/>
                            </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">${profilePlayer.getAboutYourself()}</div>
                    </div>
                    <hr>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">
                                <fmt:message key="profile.registered"/>
                            </h6>
                        </div>
                        <div class="col-sm-9 text-secondary">${user.createTime}</div>
                    </div>
                    <hr>
                </div>
            </div>

        </div>
    </div>
</body>

</html>