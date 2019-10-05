<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:internal pageTitle="Create User">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/users/list">Back to List</a>
        </li>
    </ul>

    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="create"/>
    </jsp:include>

</t:internal>
