<%@ tag description="Pager" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="pager" required="true" type="io.yodo.pragphil.core.domain.paging.Page" %>
<%@ attribute name="path" required="true" type="java.lang.String" %>
<%@ attribute name="pageParam" type="java.lang.String" %>
<%@ attribute name="size" type="java.lang.String" %>

<c:if test="${empty pageParam}">
    <c:set var="pageParam" value="p"/>
</c:if>
<c:if test="${!empty size}">
    <c:set var="sizeClass" value="pagination-${size}"/>
</c:if>

<c:set var="prefix" value="${path}?${pageParam}"/>

<ul class="pagination ${sizeClass}">
    <c:choose>
        <c:when test="${pager.hasPreviousPage()}">
            <li class="page-item">
                <a class="page-link" href="${prefix}=${pager.previousPage}">Previous</a>
            </li>
        </c:when>
        <c:otherwise>
            <li class="page-item disabled">
                <span class="page-link">Previous</span>
            </li>
        </c:otherwise>
    </c:choose>

    <c:forEach items="${pager.pageIterator()}" var="p">
        <li class="page-item ${p.current ? "active" : ""}">
            <a class="page-link" href="${prefix}=${p.number}">${p.number}</a>
        </li>
    </c:forEach>

    <c:choose>
        <c:when test="${pager.hasNextPage()}">
            <li class="page-item">
                <a class="page-link" href="${prefix}=${pager.nextPage}">Next</a>
            </li>
        </c:when>
        <c:otherwise>
            <li class="page-item disabled">
                <span class="page-link">Next</span>
            </li>
        </c:otherwise>
    </c:choose>
</ul>
