<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="auth" tagdir="/WEB-INF/tags/auth" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:layout pageTitle="Login">
    <main id="main" class="container">

        <form:form action="${pageContext.request.contextPath}/authenticate" method="POST" cssClass="login-form">

            <h2>Please Log In</h2>

            <auth:messages />

            <div class="form-group">
                <label for="username" class="sr-only">Username</label>
                <input type="text" id="username" name="username" placeholder="Username" class="form-control" required autofocus/>
            </div>
            <div class="form-group">
                <label for="password" class="sr-only">Password</label>
                <input type="password" id="password" name="password" placeholder="Password" class="form-control" required/>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="remember-me" name="remember-me"  checked />
                <label class="form-check-label" for="remember-me">Remember me</label>
            </div>
            <button class="btn btn-lg btn-primary btn-block">Sign In</button>
        </form:form>

    </main>
</t:layout>
