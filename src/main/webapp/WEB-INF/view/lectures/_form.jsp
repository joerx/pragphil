<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="lecture" type="io.yodo.pragphil.entity.Lecture"--%>

<form:form
        action="${pageContext.request.contextPath}/lectures/${param.action}"
        method="post"
        modelAttribute="lecture"
        cssClass="page-form">

<%--    <form:errors delimiter="<br/>" cssClass="error" path="*" />--%>

    <form:hidden path="id"/>

    <div class="form-group">
        <spring:bind path="name">
            <label for="name">Name</label>
            <input type="text" name="name" id="name" value="${lecture.name}" class="form-control"/>
            <c:if test="${status.error}">
            <span class="invalid-feedback">${status.errorMessage}</span>
            </c:if>
        </spring:bind>
    </div>
    <div class="form-group">
        <form:label path="lecturer">Lecturer</form:label>
        <form:select path="lecturer" cssClass="form-control">
            <form:option value="0">&nbsp;</form:option>
            <form:options items="${lecturers}" itemLabel="username" itemValue="id"/>
        </form:select>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>

</form:form>
