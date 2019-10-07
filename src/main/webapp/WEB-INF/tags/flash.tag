<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ tag description="Flash message area" pageEncoding="UTF-8" %>

<%--@elvariable id="flashInfo" type="java.lang.String"--%>
<%--@elvariable id="flashErr" type="java.lang.String"--%>

<section id="flash">
    <c:if test="${not empty flashInfo}">
        <p class="alert alert-info" role="alert">
            ${flashInfo}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </p>
    </c:if>

    <c:if test="${not empty flashErr}">
        <p class="alert alert-danger" role="alert">
            ${flashErr}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </p>
    </c:if>
</section>
