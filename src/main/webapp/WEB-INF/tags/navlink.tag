<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Navigation Bar Link" pageEncoding="UTF-8" %>

<%@ attribute name="path" required="true" %>

<c:set var="isActive" value="${requestScope['javax.servlet.forward.request_uri'].toString().endsWith(path)}"/>

<li class="nav-item ${isActive ? "active" : ""}">
    <a class="nav-link" href="${pageContext.request.contextPath}${path}">
        <jsp:doBody/>
    </a>
</li>
