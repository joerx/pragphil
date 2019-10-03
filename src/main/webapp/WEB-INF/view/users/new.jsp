<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:internal pageTitle="Create User">
    <main id="main">
        <h1>Create User</h1>
        <p>
            <a href="${pageContext.request.contextPath}/users/list">Back to List</a>
        </p>
        <jsp:include page="_form.jsp">
            <jsp:param name="action" value="create"/>
        </jsp:include>
    </main>
</t:internal>
