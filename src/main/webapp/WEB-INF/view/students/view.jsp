<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Student ${student.username}">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/users/view/${student.id}">
                <span class="oi oi-person"></span> User Profile
            </a>
        </li>
    </ul>

    <h2>Enrolled Lectures</h2>
    <ul>
        <c:forEach items="${attendedLectures}" var="lecture">
        <li>${lecture.name}
            <a href="${pageContext.request.contextPath}/students/delist/${student.id}?l=${lecture.id}">delist</a>
        </li>
        </c:forEach>
    </ul>

    <h2>Available Lectures</h2>
    <table class="table table-sm" style="max-width: 650px;">
        <thead>
            <tr>
                <th>Name</th>
                <th>Lecturer</th>
                <th>&nbsp;</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${lectures}" var="lecture">
            <tr>
                <td>${lecture.name}</td>
                <td>${lecture.lecturer.username}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/students/enroll/${student.id}?l=${lecture.id}">enroll</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</t:internal>
