<%@ tag description="Navigation Bar" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<%--@elvariable id="userDetails" type="io.yodo.pragphil.security.UserDetailsImpl"--%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">PragPhil</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarContent">
        <ul class="navbar-nav mr-auto">
            <c:if test="${!empty userDetails}">
                <t:navlink path="/lectures/list">Lectures</t:navlink>
                <t:navlink path="/users/list">Users</t:navlink>
            </c:if>
        </ul>

        <ul class="navbar-nav justify-content-end">
            <c:choose>
                <c:when test="${!empty userDetails}">
                    <t:navlink path="/users/view/${userDetails.user.id}">
                        <span class="oi oi-person" title="person" aria-hidden="true"></span>
                        ${userDetails.username}
                    </t:navlink>
                    <li class="nav-item">
                        <a href="#" class="nav-link" onclick="document.getElementById('logout-form').submit();">
                            Logout
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <t:navlink path="/login">Login</t:navlink>
                </c:otherwise>
            </c:choose>
        </ul>

    </div>

    <spring:form cssStyle="display: none" id="logout-form" method="post" action="${pageContext.request.contextPath}/logout">
    </spring:form>

</nav>
