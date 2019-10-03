<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Lecture ${lecture.name}">

    <main id="main">

        <h1>Lecture ${lecture.name}</h1>

        <p>
            <a href="${pageContext.request.contextPath}/lectures/list">Back to List</a>|
            <a href="${pageContext.request.contextPath}/lectures/edit/${lecture.id}">Edit</a>|
            <a href="${pageContext.request.contextPath}/lectures/delete/${lecture.id}"
                onclick="if (!confirm('Are you sure you want to do this?')) return false">
                Delete
            </a>
        </p>

        <table>
            <tr>
                <td>Name</td>
                <td>${lecture.name}</td>
            </tr>
            <tr>
                <td>Id</td>
                <td>${lecture.id}</td>
            </tr>
            <tr>
                <td>Lecturer</td>
                <td>${(empty lecture.lecturer) ? "<em>unassigned</em>" : lecture.lecturer.username}</td>
            </tr>
        </table>
    </main>

</t:internal>
