<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Edit User">
    <h1>Edit User</h1>
    <p>
        <a href="${pageContext.request.contextPath}/users/list">Back to List</a>
        |
        <a href="${pageContext.request.contextPath}/users/view/${user.id}">Show User</a>
    </p>
    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="update"/>
    </jsp:include>
</t:internal>
