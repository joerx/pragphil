<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lecture ${lecture.name}</title>
</head>
<body>
    <h1>Lecture ${lecture.name}</h1>
    <p>
        <a href="${pageContext.request.contextPath}/lectures/list">back to list</a>
    </p>
    <table>
        <tr>
            <td>Name</td>
            <td>${lecture.name}</td>
        </tr>
        <tr>
            <td>Id</td>
            <td>${lecture.id}</td>
        </tr>
        <tr>
            <td>Lecturer</td>
            <td>TBD</td>
        </tr>
    </table>
</body>
</html>
