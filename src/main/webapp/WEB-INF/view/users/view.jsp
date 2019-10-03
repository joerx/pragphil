<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="User ${user.username}">
    <main id="main">

        <h1>User ${user.username}</h1>

        <p>
            <a href="${pageContext.request.contextPath}/users/list">Back to List</a>
            |
            <a href="${pageContext.request.contextPath}/users/edit/${user.id}">Edit</a>
            |
            <c:if test="${user.enabled}">
            <a href="${pageContext.request.contextPath}/users/disable/${user.id}">Disable</a>
            </c:if>
            <c:if test="${!user.enabled}">
            <a href="${pageContext.request.contextPath}/users/enable/${user.id}">Enable</a>
            </c:if>
            |
            <a href="${pageContext.request.contextPath}/users/delete/${user.id}"
               onclick="if (!confirm('Are you sure you want to do this?')) return false">
                Delete
            </a>
        </p>

        <h2>Properties</h2>

        <table>
            <tr>
                <td>Username</td>
                <td>${user.username}</td>
            </tr>
            <tr>
                <td>Id</td>
                <td>${user.id}</td>
            </tr>
            <tr>
                <td>Enabled</td>
                <td>
                    <c:if test="${user.enabled}">yes</c:if>
                </td>
            </tr>
            <tr>
                <td>Roles</td>
                <td>
                    <c:forEach items="${user.roles}" var="role">
                        ${role.name}<br/>
                    </c:forEach>
                </td>
            </tr>
        </table>

        <h2>Conducted Lectures</h2>
        <c:choose>
            <c:when test="${empty user.conductedLectures}">
            <em>No lectures</em>
            </c:when>
            <c:otherwise>
            <ul>
                <c:forEach items="${user.conductedLectures}" var="lecture">
                <li>${lecture.name}</li>
                </c:forEach>
            </ul>
            </c:otherwise>
        </c:choose>

        <c:if test="${user.student}">
            <h2>Attended Lectures</h2>
            <p>
                <a href="${pageContext.request.contextPath}/students/view/${user.id}">View Attended Lectures</a>
            </p>
        </c:if>

    </main>
</t:internal>
