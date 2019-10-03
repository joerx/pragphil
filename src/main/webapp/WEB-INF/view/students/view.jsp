<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Student ${student.username}">

    <main id="main">

        <h1>Student ${student.username}</h1>
        <h2>Enrolled Lectures</h2>

        <ul>
            <c:forEach items="${attendedLectures}" var="lecture">
            <li>${lecture.name}
                <a href="${pageContext.request.contextPath}/students/delist/${student.id}?l=${lecture.id}">delist</a>
            </li>
            </c:forEach>
        </ul>

        <h2>Available Lectures</h2>
        <table>
            <thead>
                <tr>
                    <td>Name</td>
                    <td>Lecturer</td>
                    <td>&nbsp;</td>
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

    </main>

</t:internal>
