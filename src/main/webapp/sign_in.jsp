<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<html>

<fmt:message key="sign_in_jsp.title" var="signInTitle"/>
<c:set var="title" value="${signInTitle}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="content">
	<div class="container">
		<p>Login</p>
	</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>