<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:internal pageTitle="User ${user.username}">

    <ul class="page-nav">
        <li>
            <a href="${pageContext.request.contextPath}/users/list">Back to List</a>
        </li>
        <li>
            <c:if test="${user.enabled}">
                <a href="${pageContext.request.contextPath}/users/disable/${user.id}">Disable</a>
            </c:if>
            <c:if test="${!user.enabled}">
                <a href="${pageContext.request.contextPath}/users/enable/${user.id}">Enable</a>
            </c:if>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/users/edit/${user.id}">
                <span class="oi oi-pencil"></span> Edit
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/users/delete/${user.id}"
               onclick="if (!confirm('Are you sure you want to do this?')) return false">
                <span class="oi oi-x"></span> Delete
            </a>
        </li>
    </ul>

    <div class="row">
        <div class="col-md-6">
            <h2>Properties</h2>
            <dl>
                <dt>Username</dt>
                <dd>${user.username}</dd>

                <dt>Enabled</dt>
                <dd>${user.enabled ? "yes" : "no"}</dd>

                <dt>API Token</dt>
                <dd><code>${user.apiToken}</code></dd>

                <dt>Roles</dt>
                <dd>
                    <ul class="list-unstyled">
                        <c:forEach items="${user.roles}" var="item">
                            <li>${item.name}</li>
                        </c:forEach>
                    </ul>
                </dd>
            </dl>
        </div>
        <div class="col-md-6">
            <h2>Lectures</h2>

            <dl>
                <dt>Conducted Lectures</dt>
                <dd>
                    <c:choose>
                        <c:when test="${empty lectures.contents}">
                            <em>No lectures</em>
                        </c:when>
                        <c:otherwise>
                            <ul class="list-unstyled">
                                <c:forEach items="${lectures.contents}" var="lecture">
                                    <li>${lecture.name}</li>
                                </c:forEach>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                    <nav>
                        <c:if test="${lectures.needsPaging()}">
                            <t:pager pager="${lectures}"
                                     path="${pageContext.request.contextPath}/users/${user.username}"
                                     pageParam="lp"
                                     size="sm"/>
                        </c:if>
                    </nav>
                </dd>

                <c:if test="${user.student}">
                    <dt>Attended Lectures</dt>
                    <dd>
                        <a href="${pageContext.request.contextPath}/students/${user.username}">View Attended Lectures</a>
                    </dd>
                </c:if>

            </dl>
        </div>
    </div>

</t:internal>
