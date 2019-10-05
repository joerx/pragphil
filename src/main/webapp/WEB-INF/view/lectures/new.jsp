<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Create Lecture">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/lectures/list">Back to List</a>
        </li>
    </ul>

    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="create"/>
    </jsp:include>

</t:internal>
