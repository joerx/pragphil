<%@ tag description="Internal page template" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="pageTitle" required="false" %>

<t:layout pageTitle="${pageTitle}">
    <jsp:body>
        <t:navbar/>

        <main id="main" class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <t:flash/>

                    <h1>${pageTitle}</h1>
                    <jsp:doBody/>
                </div>
            </div>

        </main>

    </jsp:body>
</t:layout>
