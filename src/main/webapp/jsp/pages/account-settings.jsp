<%--
  Created by IntelliJ IDEA.
  User: evges
  Date: 10.10.2021
  Time: 11:12
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
    <link rel="stylesheet" href="/css/account-settings.css">
</head>

<body>
<c:import url="/jsp/parts/navbar.jsp" />
<div class="container py-3 light-style flex-grow-1 container-p-y">

    <h4 class="font-weight-bold py-3 mb-4">
        <fmt:message key="settings.account.settings" />
    </h4>

    <div class="card overflow-hidden">
        <div class="row no-gutters row-bordered row-border-light">

            <div class="col-md-3 pt-0" id="choiceSection">
                <div class="list-group list-group-flush account-settings-links">
                    <a class="list-group-item list-group-item-action active" onclick="changeBtnSubmitGeneral()" data-toggle="list" href="#account-general">
                        <fmt:message key="settings.general" />
                    </a>
                    <a class="list-group-item list-group-item-action" onclick="changeBtnSubmitPas()" data-toggle="list" href="#account-change-password">
                        <fmt:message key="settings.change.password" />
                    </a>
                    <a class="list-group-item list-group-item-action" data-toggle="list" id="sectionNotifications" href="#account-notifications">Notifications</a>
                </div>
            </div>

            <div class="col-md-9">
                <div class="tab-content">
                    <div class="tab-pane fade active show" id="account-general">

                        <div class="card-body media align-items-center">
                            <img src="${pageContext.request.contextPath}/images/photo/${sessionScope.photo}" class="rounded-circle" width="100px" alt="" class="d-block ui-w-80">
                            <div class="media-body ml-4">
                                <input id="ajaxfile" type="file" class="d-none" accept=".png, .jpg, .jpeg .gif" size="10" onchange="uploadFile()" />
                                <button type="submit" class="btnUpload btn btn-primary">
                                    <fmt:message key="profile.change.photo" />
                                </button>
                                <script src="/js/upload-photo.js"></script>
                                <div class="text-light small mt-1">Allowed JPG, GIF or PNG.</div>
                            </div>
                        </div>
                        <hr class="border-light m-0">
                        <div class="card-body">
                            <form id="changeGeneralForm" name="changeGeneralDataUserForm" method="POST" action="${pageContext.request.contextPath}/poker?command=change-general-info" class="">
                                <div class="form-group">
                                    <label class="form-label">
                                        <fmt:message key="settings.first.name" />
                                    </label>
                                    <input type="text" name="firstName" minlength="4" maxlength="32" class="form-control" value="${user.getFirstName()}" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">
                                        <fmt:message key="settings.last.name" />
                                    </label>
                                    <input type="text" name="lastName" minlength="4" maxlength="32" class="form-control" value="${user.getLastName()}" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">
                                        <fmt:message key="profile.phone" />
                                    </label>
                                    <input type="phone" pattern="[+]{1}[0-9]{12, 16}" name="phoneNumber" minlength="12" maxlength="16" class="form-control" value="${user.getPhoneNumber()}" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label ">
                                        <fmt:message key="profile.about.yourself" />
                                    </label>
                                    <div class="text-light small float-right mt-1"><fmt:message key="settings.desc.bio"/></div>
                                    <textarea class="form-control" minlength="10" maxlength="510" name="bio" rows="5" required>${profilePlayer.getAboutYourself()}</textarea>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade" id="account-change-password">
                        <div class="card-body pb-2">
                            <form id="changePasForm" name="changePasswordUserForm" method="POST" action="${pageContext.request.contextPath}/poker?command=change-pas">
                                <div class="form-group">
                                    <label class="form-label">
                                        <fmt:message key="settings.current.password" />
                                    </label>
                                    <input type="password" id="currentPass" name="currPas" minlength="8" maxlength="32" class="form-control" required>
                                    <input type="checkbox" onclick="showPass()"> <fmt:message key="settings.current.password.show"/>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">
                                        <fmt:message key="settings.new.password" />
                                    </label>
                                    <input type="password" name="newPas" minlength="8" maxlength="32" class="form-control password" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">
                                        <fmt:message key="settings.repeat.new.password" />
                                    </label>
                                    <input type="password" class="form-control password" minlength="8" maxlength="32" required>
                                    <span class="password-error d-none text-danger">
                                            <fmt:message key="signup.repeat.pass.error" />
                                        </span>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane fade  bg-secondary" id="account-notifications">
                        <h2 class="display-5">in developing</h2>
                        <div class="card-body pb-2">
                            <h6 class="mb-4">Activity</h6>
                            <div class="form-group">
                                <label class="switcher">
                                    <input type="checkbox" class="switcher-input" checked="">
                                    <span class="switcher-indicator">
                                            <span class="switcher-yes"></span>
                                            <span class="switcher-no"></span>
                                        </span>
                                    <span class="switcher-label">Email me when someone comments on my article</span>
                                </label>
                            </div>
                            <div class="form-group">
                                <label class="switcher">
                                    <input type="checkbox" class="switcher-input" checked="">
                                    <span class="switcher-indicator">
                                            <span class="switcher-yes"></span>
                                            <span class="switcher-no"></span>
                                        </span>
                                    <span class="switcher-label">Email me when someone answers on my forum
                                            thread</span>
                                </label>
                            </div>
                            <div class="form-group">
                                <label class="switcher">
                                    <input type="checkbox" class="switcher-input">
                                    <span class="switcher-indicator">
                                            <span class="switcher-yes"></span>
                                            <span class="switcher-no"></span>
                                        </span>
                                    <span class="switcher-label">Email me when someone follows me</span>
                                </label>
                            </div>
                        </div>
                        <hr class="border-light m-0">
                        <div class="card-body pb-2">

                            <h6 class="mb-4">Application</h6>

                            <div class="form-group">
                                <label class="switcher">
                                    <input type="checkbox" class="switcher-input" checked="">
                                    <span class="switcher-indicator">
                                            <span class="switcher-yes"></span>
                                            <span class="switcher-no"></span>
                                        </span>
                                    <span class="switcher-label">News and announcements</span>
                                </label>
                            </div>
                            <div class="form-group">
                                <label class="switcher">
                                    <input type="checkbox" class="switcher-input">
                                    <span class="switcher-indicator">
                                            <span class="switcher-yes"></span>
                                            <span class="switcher-no"></span>
                                        </span>
                                    <span class="switcher-label">Weekly product updates</span>
                                </label>
                            </div>
                            <div class="form-group">
                                <label class="switcher">
                                    <input type="checkbox" class="switcher-input" checked="">
                                    <span class="switcher-indicator">
                                            <span class="switcher-yes"></span>
                                            <span class="switcher-no"></span>
                                        </span>
                                    <span class="switcher-label">Weekly blog digest</span>
                                </label>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <h3 class="text-danger error-message text-centr">
        <fmt:message key="settings.error.${errorMessage}" />
    </h3>

    <div class="text-right mt-3">
        <button type="submit" form="changeGeneralForm" class="btn btn-primary" id="btnSubmit">
            <fmt:message key="settings.save.changes" />
        </button>
        <a class="btn btn-default border border-secondary" href="${pageContext.request.contextPath}/poker?command=profile-page&id=${sessionScope.get("userId")}">
            <fmt:message key="settings.cancel" />
        </a>
    </div>

</div>
<script src="/js/account-settings.js"></script>
<script src="/js/checker-repeat-pas.js"></script>
</body>

</html>