<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Lectures">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/lectures/new">
                <span class="oi oi-plus"></span>
                New Lecture
            </a>
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
                <th>Lecturer</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${lectures}" var="lecture">
            <tr>
                <td>
                    <a href="${pageContext.request.contextPath}/lectures/edit/${lecture.id}">
                        <span class="oi oi-pencil"></span>
                    </a>
                </td>
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
            </tr>
            </c:forEach>
        </tbody>
    </table>

</t:internal>
