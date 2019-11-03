<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" tagdir="/WEB-INF/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="lecture" type="io.yodo.pragphil.core.domain.entity.Lecture"--%>
<%--@elvariable id="userDetails" type="io.yodo.pragphil.core.security.DefaultUserDetails"--%>

<form:form
        action="${pageContext.request.contextPath}/lectures/${param.action}"
        method="post"
        modelAttribute="lecture"
        cssClass="page-form">

    <form:hidden path="id"/>

    <f:global-errors name="lecture"/>

    <div class="form-group">
        <f:input path="name" value="${lecture.name}" label="Name"/>
    </div>
    <div class="form-group">
        <%-- Show lecturer selection only if user is ADMIN --%>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <f:select path="lecturer"
                      label="Lecturer"
                      items="${lecturers}"
                      itemLabel="username"
                      itemValue="id"
                      emptyOption="true"
                      emptyValue="0"
                      emptyLabel="unassigned"/>
        </sec:authorize>

        <sec:authorize access="(not hasRole('ROLE_ADMIN')) and hasRole('ROLE_LECTURER')">
            <form:hidden path="lecturer" value="${userDetails.user.id}"/>
            <label for="static-lecturer">Lecturer</label>
            <input type="text"
                   value="${empty lecture.lecturer ? userDetails.user.username : lecture.lecturer.username}"
                   readonly
                   class="form-control-plaintext"
                   id="static-lecturer"/>
        </sec:authorize>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>

</form:form>
