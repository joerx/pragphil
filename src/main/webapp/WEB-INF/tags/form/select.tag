<%@ tag description="Bootstrap style form group" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="label" required="true" %>
<%@ attribute name="items" required="true" type="java.util.List"%>
<%@ attribute name="itemLabel" required="true" %>
<%@ attribute name="itemValue" required="true" %>
<%@ attribute name="emptyOption" required="false" %>
<%@ attribute name="emptyValue" required="false" %>
<%@ attribute name="emptyLabel" required="false" %>

<spring:bind path="${path}">
    <form:label path="${path}">${label}</form:label>
    <form:select path="${path}" cssClass="form-control">
        <c:if test="${!empty emptyOption}">
            <form:option value="0">${empty emptyLabel ? "&nbsp;" : emptyLabel }</form:option>
        </c:if>
        <form:options items="${items}" itemLabel="${itemLabel}" itemValue="${itemValue}"/>
    </form:select>
    <form:errors path="${path}" cssClass="invalid-feedback"/>
</spring:bind>
