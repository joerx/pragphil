<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="lecture" type="io.yodo.pragphil.entity.Lecture"--%>

<form:form action="${pageContext.request.contextPath}/lectures/${param.action}" method="post" modelAttribute="lecture">

    <form:hidden path="id"/>

    <div class="form-group">
        <spring:bind path="name">
            <label for="name">Name</label>
            <input type="text" name="name" id="name" value="${lecture.name}" class="form-control"/>
        </spring:bind>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>

</form:form>
