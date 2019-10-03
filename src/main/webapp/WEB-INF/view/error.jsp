<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="ex" scope="request" type="java.lang.Exception"/>

<t:layout pageTitle="error">

    <h1>Error</h1>

    <p>
        <c:choose>
            <c:when test="${ex.message != null && ex.message != \"\"}">
                ${ex.message}
            </c:when>
            <c:otherwise>
                We don't really know what happened (${ex.toString()})
            </c:otherwise>
        </c:choose>

    </p>
    <p><em>"This too shall pass!"</em></p>

<!--
<c:forEach items="${ex.stackTrace}" var="ste">${ste}
</c:forEach>
-->

</t:layout>
