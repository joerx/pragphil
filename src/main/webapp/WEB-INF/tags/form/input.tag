<%@ tag description="Bootstrap style form group" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="type" required="false" %>
<%@ attribute name="label" required="true" %>

<c:if test="${empty type}">
    <c:set var="type" value="text"/>
</c:if>

<spring:bind path="${path}">
    <label for="${path}">${label}</label>
    <input type="${type}"
           name="${path}"
           id="${path}"
           value="${value}"
           class="form-control ${status.error ? "is-invalid" : ""}"/>
    <form:errors path="${path}" cssClass="invalid-feedback"/>
</spring:bind>
