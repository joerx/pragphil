<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create User</title>
</head>
<body>
    <h1>Create User</h1>
    <p>
        <a href="${pageContext.request.contextPath}/users/list">Back to List</a>
    </p>
    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="create"/>
    </jsp:include>
</body>
</html>
