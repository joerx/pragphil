<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"/>

    <title>User ${user.username}</title>
</head>
<body>
    <jsp:include page="../fragments/_navbar.jsp" />
    <jsp:include page="../fragments/_flash.jsp" />

    <h1>User ${user.username}</h1>
    <p>
        <a href="${pageContext.request.contextPath}/users/list">
            Back to List
        </a>|
        <a href="${pageContext.request.contextPath}/users/edit/${user.id}">
            Edit
        </a>|
        <c:if test="${user.enabled}">
        <a href="${pageContext.request.contextPath}/users/disable/${user.id}">
            Disable
        </a>
        </c:if>
        <c:if test="${!user.enabled}">
        <a href="${pageContext.request.contextPath}/users/enable/${user.id}">
            Enable
        </a>
        </c:if>|
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
                <c:if test="${user.enabled}">
                yes
                </c:if>
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
    <ul>
        <c:forEach items="${user.conductedLectures}" var="lecture">
        <li>${lecture.name}</li>
        </c:forEach>
    </ul>
</body>
</html>
