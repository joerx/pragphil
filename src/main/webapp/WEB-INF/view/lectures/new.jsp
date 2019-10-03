<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="Create Lecture">

    <main id="main">
        <h1>Create Lecture</h1>

        <p><a href="${pageContext.request.contextPath}/lectures/list">Back to List</a></p>

        <jsp:include page="_form.jsp">
            <jsp:param name="action" value="create"/>
        </jsp:include>
    </main>

</t:internal>
