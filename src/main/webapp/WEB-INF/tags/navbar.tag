<%@ tag description="Navigation Bar" pageEncoding="UTF-8" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">PragPhil</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarContent">
        <ul class="navbar-nav mr-auto">
            <t:navlink path="/lectures/list">
                Lectures
            </t:navlink>
            <t:navlink path="/users/list">
                Users
            </t:navlink>
        </ul>
        <ul class="navbar-nav justify-content-end">
            <t:navlink path="/users/view/1">
                <span class="oi oi-person" title="person" aria-hidden="true"></span>
                John Doe
            </t:navlink>
        </ul>
    </div>

</nav>
