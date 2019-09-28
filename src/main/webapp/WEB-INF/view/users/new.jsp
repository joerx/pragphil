<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"/>

    <title>Create User</title>
</head>
<body>
    <jsp:include page="../fragments/_flash.jsp" />

    <h1>Create User</h1>
    <p>
        <a href="${pageContext.request.contextPath}/users/list">Back to List</a>
    </p>
    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="create"/>
    </jsp:include>
</body>
</html>
