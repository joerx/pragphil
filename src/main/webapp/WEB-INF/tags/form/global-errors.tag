<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag description="Global form errors section" pageEncoding="UTF-8" %>

<%@ attribute name="name" required="true" %>

<spring:hasBindErrors name="${name}">
    <c:if test="${!empty errors.globalErrors}">
    <div class="alert alert-danger" role="alert">
        <ul class="list-unstyled">
            <c:forEach var="e" items="${errors.globalErrors}">
                <li><spring:message message="${e}"/></li>
            </c:forEach>
        </ul>
    </div>
    </c:if>
</spring:hasBindErrors>
