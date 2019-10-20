<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="f" tagdir="/WEB-INF/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<%--@elvariable id="user" type="io.yodo.pragphil.core.entity.User"--%>

<form:form
        action="${pageContext.request.contextPath}/users/${param.action}"
        method="post"
        modelAttribute="user"
        cssClass="page-form">

    <form:hidden path="id"/>
    
    <f:global-errors name="user"/>

    <div class="form-group">
    <f:input path="username" value="${user.username}" label="Username"/>
    </div>

    <div class="form-group">
    <f:input path="password" value="${user.password}" label="Password" type="password"/>
    </div>

    <fieldset>
        <legend>Roles</legend>
        <div class="form-group">
            <%--@elvariable id="allRoles" type="java.util.List"--%>
            <f:checkboxes path="roles"
                          allItems="${allRoles}"
                          checkedItems="${user.roles}"
                          itemValue="id"
                          itemLabel="name"/>
        </div>
    </fieldset>

    <fieldset>
        <legend>Status</legend>
        <div class="form-group">
            <div class="form-check">
                <form:checkbox path="enabled" cssClass="form-check-input"/>
                <form:label path="enabled" cssClass="form-check-label">
                    Enabled
                </form:label>
            </div>
        </div>
    </fieldset>

    <button type="submit" class="btn btn-primary">Submit</button>

</form:form>
