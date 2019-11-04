<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="student" scope="request" type="io.yodo.pragphil.core.domain.entity.User"/>
<jsp:useBean id="lectures" scope="request" type="io.yodo.pragphil.core.domain.paging.Page"/>
<jsp:useBean id="attendedLectures" scope="request" type="io.yodo.pragphil.core.domain.paging.Page"/>

<t:internal pageTitle="Student ${student.username}">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/users/${student.username}">
                <span class="oi oi-person"></span> User Profile
            </a>
        </li>
    </ul>

    <div class="row">
        <div class="col-md-6">
            <h2>Attended Lectures</h2>
            <table class="table table-sm" style="max-width: 650px;">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Lecturer</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${attendedLectures.contents}" var="lecture">
                    <tr>
                        <td>${lecture.name}</td>
                        <td>${lecture.lecturer.username}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/students/delist/${student.id}?l=${lecture.id}">delist</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${attendedLectures.needsPaging()}">
                <t:pager pager="${attendedLectures}"
                         path="${pageContext.request.contextPath}/students/${student.username}"
                         pageParam="ap"
                         size="sm" />
            </c:if>
        </div>

        <div class="col-md-6">
            <h2>Eligible Lectures</h2>
            <table class="table table-sm" style="max-width: 650px;">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Lecturer</th>
                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${lectures.contents}" var="lecture">
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
            <c:if test="${attendedLectures.needsPaging()}">
                <t:pager pager="${lectures}"
                         path="${pageContext.request.contextPath}/students/${student.username}"
                         pageParam="ep"
                         size="sm" />
            </c:if>
        </div>
    </div>

</t:internal>
