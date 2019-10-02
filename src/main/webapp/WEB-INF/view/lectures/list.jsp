<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"/>

    <title>Lectures</title>
</head>
<body>
    <jsp:include page="../fragments/_navbar.jsp" />
    <jsp:include page="../fragments/_flash.jsp" />

    <h1>Lectures</h1>

    <p>
        <a href="${pageContext.request.contextPath}/lectures/new">New Lecture</a>
    </p>
    <table>
        <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Lecturer</th>
                <th>&nbsp;</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${lectures}" var="lecture">
            <tr>
                <td>${lecture.id}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/lectures/view/${lecture.id}">
                        ${lecture.name}
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/users/view/${lecture.lecturer.id}">
                        ${lecture.lecturer.username}
                    </a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/lectures/edit/${lecture.id}">edit</a>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
