<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="user" type="io.yodo.pragphil.entity.User"--%>

<form:form
        action="${pageContext.request.contextPath}/users/${param.action}"
        method="post"
        modelAttribute="user"
        cssClass="page-form">

    <form:hidden path="id"/>

    <div class="form-group">
        <spring:bind path="username">
            <label for="username">Username</label>
            <input type="text" name="username" id="username" value="${user.username}" class="form-control"/>
            <c:if test="${status.error}">
            <span class="invalid-feedback">${status.errorMessage}</span>
            </c:if>
        </spring:bind>
    </div>
    <div class="form-group">
        <spring:bind path="password">
            <label for="password">Password</label>
            <input type="text" name="password" id="password" value="${user.password}" class="form-control"/>
            <c:if test="${status.error}">
            <span class="invalid-feedback">${status.errorMessage}</span>
            </c:if>
        </spring:bind>
    </div>
    <div class="form-group">
        <%-- Build checkboxes manually so we comply with required structure for Bootstrap --%>
        <%--@elvariable id="allRoles" type="java.util.List"--%>
        <c:forEach items="${allRoles}" var="role" varStatus="s">
        <div class="checkbox">
            <label>
                <input type="checkbox"
                       id="roles${s.count}"
                       name="roles"
                       value="${role.id}"
                        ${user.roles.contains(role) ? "checked" : ""}>
                ${role.name}
            </label>
        </div>
        </c:forEach>
    </div>
    <div class="form-group">
        <div class="checkbox">
            <p class="label">Status</p>
            <form:label path="enabled">
                <form:checkbox path="enabled"/>
                Enabled
            </form:label>
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>

</form:form>
