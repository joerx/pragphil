<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Users">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/users/new">
                <span class="oi oi-plus"></span> New User
            </a>
        </li>
        <li>
            Show:
            <a href="${pageContext.request.contextPath}/users/list">All</a>
            |
            <a href="${pageContext.request.contextPath}/users/list?role=ROLE_LECTURER">Lecturers</a>
            |
            <a href="${pageContext.request.contextPath}/users/list?role=ROLE_STUDENT">Students</a>
        </li>
    </ul>

    <table class="table">
        <colgroup>
            <col style="width: 20px"/>
            <col/>
            <col/>
        </colgroup>
        <thead>
        <tr>
            <th>&nbsp;</th>
            <th>Name</th>
            <th>Enabled</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/users/edit/${user.id}">
                        <span class="oi oi-pencil"></span>
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/view/${user.id}">${user.username}</a>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${user.enabled}">
                            yes
                        </c:when>
                        <c:otherwise>
                            <span class="thing-disabled">no</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${user.enabled}">
                            <a href="${pageContext.request.contextPath}/users/disable/${user.id}">
                                disable
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/users/enable/${user.id}">
                                enable
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</t:internal>
