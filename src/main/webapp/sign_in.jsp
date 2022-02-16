<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>
<%@ page import="com.a_smart_cookie.util.validation.ValidationPattern" %>

<r:redirectIfInSession sessionAttribute="user" redirectPath="controller?command=catalog"/>

<html>

<fmt:message key="sign_in_jsp.title" var="signInTitle"/>
<c:set var="title" value="${signInTitle}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="content">
	<div class="container">
		<div class="row justify-content-center">
			<form action="${pageContext.request.contextPath}/controller?command=login" method="post"
				  class="col-6">
				<div class="form-group">
					<label for="email"><fmt:message key="credentials.email"/></label>
					<input
							type="email"
							class="form-control"
							name="email"
							id="email"
							required
							pattern="${ValidationPattern.EMAIL.pattern}"
							title="<fmt:message key="validation.email" />"
					<c:if test="${sessionScope.oldEmail != null}">
							value="${sessionScope.oldEmail}"
					</c:if>
					>
					<c:if test="${sessionScope.isValidEmail != null && !sessionScope.isValidEmail}">
					<span class="error text-danger">
						<fmt:message key="validation.email"/>
					</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="password"><fmt:message key="credentials.password"/></label>
					<input
							type="password"
							class="form-control"
							name="password"
							id="password"
							required
							pattern="${ValidationPattern.PASSWORD.pattern}"
							title="<fmt:message key="validation.password" />"
					>
					<c:if test="${sessionScope.isValidPassword != null && !sessionScope.isValidPassword}">
					<span class="error text-danger">
						<fmt:message key="validation.password"/>
					</span>
					</c:if>
				</div>
				<c:if test="${sessionScope.badCredentials != null && sessionScope.badCredentials}">
					<span class="error text-danger">
						<fmt:message key="sign_in_jsp.bad_credentials"/>
					</span>
				</c:if>
				<c:if test="${sessionScope.isBlocked != null && sessionScope.isBlocked}">
					<span class="error text-danger">
						<fmt:message key="sign_in_jsp.blocked_user"/>
					</span>
				</c:if>
				<div class="col text-center">
					<button type="submit" class="btn btn-primary mt-2">
						<fmt:message key="sign_in_jsp.button_sign_in"/>
					</button>
				</div>
			</form>
		</div>
		<div class="row justify-content-center text-center mt-3">
			<div class="col-6">
				<p>
					<fmt:message key="sign_in_jsp.dont_have_acc_question" />
					<a href="${pageContext.request.contextPath}/sign_up.jsp">
						<fmt:message key="sign_in_jsp.register" />
					</a>
				</p>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>