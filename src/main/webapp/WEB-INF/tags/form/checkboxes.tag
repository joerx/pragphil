<%@ tag description="Bootstrap style form checkbox group" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="path" required="true" %>
<%@ attribute name="itemValue" required="true" %>
<%@ attribute name="itemLabel" required="true" %>
<%@ attribute name="allItems" required="true" type="java.util.List" %>
<%@ attribute name="checkedItems" required="true" type="java.util.List" %>

<c:forEach items="${allItems}" var="item" varStatus="s">
    <div class="form-check">
        <input type="checkbox"
               id="${path}${s.count}"
               name="${path}"
               value="${item[itemValue]}"
               class="form-check-input"
            ${checkedItems.contains(item) ? "checked" : ""}>
        <label for="${path}${s.count}" class="form-check-label">${item[itemLabel]}</label>
    </div>
</c:forEach>
