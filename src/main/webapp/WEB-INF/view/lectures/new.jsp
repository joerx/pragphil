<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Lecture</title>
</head>
<body>
    <h1>Create Lecture</h1>
    <p>
        <a href="${pageContext.request.contextPath}/lectures/list">Back to List</a>
    </p>
    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="create"/>
    </jsp:include>
</body>
</html>
