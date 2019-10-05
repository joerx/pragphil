<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" tagdir="/WEB-INF/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="lecture" type="io.yodo.pragphil.entity.Lecture"--%>

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
        <f:select path="lecturer"
                  label="Lecturer"
                  items="${lecturers}"
                  itemLabel="username"
                  itemValue="id"
                  emptyOption="true"
                  emptyValue="0"
                  emptyLabel="unassigned"/>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>

</form:form>
