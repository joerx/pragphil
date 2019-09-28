<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="io.yodo.pragphil.entity.User"--%>

<form:form action="${pageContext.request.contextPath}/users/${param.action}" method="post" modelAttribute="user">

    <form:hidden path="id"/>

    <div class="form-group">
        <spring:bind path="username">
            <label for="username">Username</label>
            <input type="text" name="username" id="username" value="${user.username}" class="form-control"/>
        </spring:bind>
    </div>
    <div class="form-group">
        <spring:bind path="password">
            <label for="password">Password</label>
            <input type="text" name="password" id="password" value="${user.password}" class="form-control"/>
        </spring:bind>
    </div>
    <div class="form-group">
        <form:label path="enabled">Enabled</form:label>
        <form:checkbox path="enabled" cssClass="form-control"/>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>

</form:form>
