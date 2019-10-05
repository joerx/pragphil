<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Edit Lecture">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/lectures/list">Back to List</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/lectures/view/${lecture.id}">View Lecture</a>
        </li>
    </ul>

    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="update"/>
    </jsp:include>

</t:internal>
