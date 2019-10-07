<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="Logout message" pageEncoding="UTF-8" %>

<section id="flash">
<c:if test="${param.logout != null}">

    <p class="alert alert-success">
        You have been logged out.
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </p>

</c:if>
<c:if test="${param.error != null}">

    <p class="alert alert-warning">
        Invalid credentials. Please try again!
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </p>

</c:if>
</section>
