<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"/>

    <title>Users</title>
</head>
<body>
    <jsp:include page="../fragments/_flash.jsp" />

    <h1>Users</h1>
    <p>
        <a href="${pageContext.request.contextPath}/users/new">New User</a>
    </p>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Enabled</th>
            <th>&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/view/${user.id}">${user.username}</a>
                </td>
                <td>
                    <c:if test="${user.enabled}">yes</c:if>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/edit/${user.id}">edit</a>
                    <c:if test="${user.enabled}">
                    <a href="${pageContext.request.contextPath}/users/disable/${user.id}">disable</a>
                    </c:if>
                    <c:if test="${!user.enabled}">
                    <a href="${pageContext.request.contextPath}/users/enable/${user.id}">enable</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
