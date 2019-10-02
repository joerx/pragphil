<%--@elvariable id="ex" type="java.lang.Exception"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <jsp:include page="fragments/_navbar.jsp" />

    <h1>Error</h1>

    <p>
        <c:if test="${ex.message == null || ex.message == \"\"} ">
            We don't really know what happened
        </c:if>
        <c:if test="${ex.message != null && ex.message != \"\"}">
            ${ex.message}
        </c:if>
    </p>
    <p><em>"This too shall pass!"</em></p>

<!--
<c:forEach items="${ex.stackTrace}" var="ste">${ste}
</c:forEach>
-->

</body>
</html>
