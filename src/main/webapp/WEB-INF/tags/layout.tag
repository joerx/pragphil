<%@ tag description="Main page layout" pageEncoding="UTF-8" %>

<%@ attribute name="pageTitle" %>

<html>
<head>
    <title>${(empty pageTitle) ? "The Pragmatic Philosopher" : pageTitle}</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css"/>
</head>
<body>
    <jsp:doBody/>
</body>
</html>
