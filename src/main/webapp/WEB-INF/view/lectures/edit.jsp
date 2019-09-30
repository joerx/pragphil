<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"/>

    <title>Edit Lecture</title>
</head>
<body>
    <jsp:include page="../fragments/_navbar.jsp" />
    <jsp:include page="../fragments/_flash.jsp" />

    <h1>Edit Lecture</h1>
    <p>
        <a href="${pageContext.request.contextPath}/lectures/list">Back to List</a>
    </p>
    <jsp:include page="_form.jsp">
        <jsp:param name="action" value="update"/>
    </jsp:include>
</body>
</html>
