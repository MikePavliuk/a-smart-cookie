<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<html>

<fmt:message key="error.illegal_arguments.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
	<div class="container text-center">
		<h2 class="mt-2"><fmt:message key="error.illegal_arguments.header" /></h2>
		<h4 class="mt-3"><fmt:message key="error.illegal_arguments.message" /></h4>
		<%@ include file="/WEB-INF/jspf/redirect-to-home.jspf" %>
	</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>