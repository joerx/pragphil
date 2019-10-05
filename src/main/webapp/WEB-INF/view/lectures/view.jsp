<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Lecture \"${lecture.name}\"">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/lectures/list">Back to List</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/lectures/edit/${lecture.id}">
                <span class="oi oi-pencil"></span> Edit
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/lectures/delete/${lecture.id}"
                onclick="if (!confirm('Are you sure you want to do this?')) return false">
                <span class="oi oi-x"></span> Delete
            </a>
        </li>
    </ul>

    <dl>
        <dt>Name</dt>
        <dd>${lecture.name}</dd>
        <dt>Lecturer</dt>
        <dd>${(empty lecture.lecturer) ? "<em>unassigned</em>" : lecture.lecturer.username}</dd>
    </dl>

</t:internal>
