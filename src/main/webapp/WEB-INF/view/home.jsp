<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:layout>
<main id="main" class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <h1>The Pragmatic Philosopher</h1>
            <p>Awesome stuff ahead. Stay tuned!</p>
            <ul>
                <li><a href="${pageContext.request.contextPath}/lectures/list">Lectures</a></li>
                <li><a href="${pageContext.request.contextPath}/users/list">Users</a></li>
            </ul>
        </div>
    </div>
</main>
</t:layout>
