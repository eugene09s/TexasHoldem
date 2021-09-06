<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 30.08.2021
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="b-example-divider"></div>
<header class="p-3 bg-dark text-white">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <sc:access role="ADMIN">
                <ul class="nav col col-lg-auto me-lg-auto mr-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="${pageContext.request.contextPath}/jsp/pages/home.jsp" class="nav-link px-2 text-secondary"><fmt:message
                            key="nav.home.page"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/pages/ranking.jsp" class="nav-link px-2 text-white"><fmt:message
                            key="nav.ranking.page"/></a></li>
                    <li><a href="${pageContext.request.contextPath}" class="nav-link px-2 text-white"><fmt:message
                            key="nav.play.page"/></a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}"
                           class="nav-link px-2 text-white">FAQs</a></li>
                    <li><a href="${pageContext.request.contextPath}" class="nav-link px-2 text-white">About</a>
                    </li>
                </ul>

                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search..."
                           aria-label="Search">
                </form>

                <div class="text-end">
                    <a type="button" href="${pageContext.request.contextPath}/poker?command=login-page" class="btn btn-outline-light me-2"><fmt:message
                            key="nav.login"/></a>
                    <a type="button" href="${pageContext.request.contextPath}/poker?command=sign-up-page" class="btn btn-warning"><fmt:message
                            key="nav.sign.up.lower"/></a>
                </div>
            </sc:access>
            <sc:access role="GUEST">
                <ul class="nav col col-lg-auto me-lg-auto mr-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="${pageContext.request.contextPath}/jsp/pages/home.jsp" class="nav-link px-2 text-secondary"><fmt:message
                            key="nav.home.page"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/poker?command=login-page" class="nav-link px-2 text-white"><fmt:message
                            key="nav.play.page"/></a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}"
                           class="nav-link px-2 text-white">FAQs</a></li>
                    <li><a href="${pageContext.request.contextPath}" class="nav-link px-2 text-white">About</a>
                    </li>
                </ul>

                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search..."
                           aria-label="Search">
                </form>

                <div class="text-end">
                    <a type="button" href="${pageContext.request.contextPath}/poker?command=login-page" class="btn btn-outline-light me-2"><fmt:message
                            key="nav.login"/></a>
                    <a type="button" href="${pageContext.request.contextPath}/poker?command=sign-up-page" class="btn btn-warning"><fmt:message
                            key="nav.sign.up.lower"/></a>
                </div>
            </sc:access>
            <sc:access role="USER">
                <ul class="nav col col-lg-auto me-lg-auto mr-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="${pageContext.request.contextPath}/jsp/pages/home.jsp" class="nav-link px-2 text-secondary"><fmt:message
                            key="nav.home.page"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/poker?command=login-page" class="nav-link px-2 text-white"><fmt:message
                            key="nav.play.page"/></a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}"
                           class="nav-link px-2 text-white">FAQs</a></li>
                    <li><a href="${pageContext.request.contextPath}" class="nav-link px-2 text-white">About</a>
                    </li>
                </ul>

                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search..."
                           aria-label="Search">
                </form>

                <%--                <div class="text-end">--%>
                <%--                    <a type="button" href="/poker?command=login-page" class="btn btn-outline-light me-2"><fmt:message key="nav.login"/></a>--%>
                <%--                    <a type="button" href="/poker?command=sign-up-page" class="btn btn-warning"><fmt:message key="nav.sign.up.lower"/></a>--%>
                <%--                </div>--%>
                <div class="dropdown text-end">
                    <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser1"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle">
                    </a>
                    <ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1" style="">
                            <%--                        <li><a class="dropdown-item" href="#">New project...</a></li>--%>
                        <li><a class="dropdown-item" href="#"><fmt:message key="nav.setting"/></a></li>
                        <li><a class="dropdown-item" href="#"><fmt:message key="nav.profile"/></a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="#"><fmt:message key="nav.signout"/></a></li>
                    </ul>
                </div>
            </sc:access>
        </div>
    </div>
</header>
