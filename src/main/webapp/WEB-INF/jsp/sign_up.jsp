<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>
<%@ page import="com.a_smart_cookie.util.validation.user.UserValidationPattern" %>

<html>

<fmt:message key="sign_up_jsp.title" var="signUpTitle"/>
<c:set var="title" value="${signUpTitle}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="content">
	<div class="container">
		<h1 class="text-center"><fmt:message key="sign_up_jsp.title"/></h1>
		<div class="row justify-content-center">
			<form action="${pageContext.request.contextPath}/controller?command=registration" method="post"
				  class="col-6">
				<div class="form-group">
					<label for="firstName"><fmt:message key="sign_up_jsp.first_name"/> <span class="text-danger">*</span></label>
					<input
							type="text"
							class="form-control"
							name="firstName"
							id="firstName"
							required
							pattern="${UserValidationPattern.NAME.pattern}"
							title="<fmt:message key="validation.user.first_name" />"
					<c:if test="${sessionScope.oldFirstName != null}">
							value="${sessionScope.oldFirstName}"
					</c:if>
					>
					<c:if test="${sessionScope.isValidName != null && !sessionScope.isValidName}">
					<span class="error text-danger">
						<fmt:message key="validation.user.first_name"/>
					</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="lastName"><fmt:message key="sign_up_jsp.last_name"/> <span class="text-danger">*</span></label>
					<input
							type="text"
							class="form-control"
							name="lastName"
							id="lastName"
							required
							pattern="${UserValidationPattern.SURNAME.pattern}"
							title="<fmt:message key="validation.user.last_name" />"
					<c:if test="${sessionScope.oldLastName != null}">
							value="${sessionScope.oldLastName}"
					</c:if>
					>
					<c:if test="${sessionScope.isValidSurname != null && !sessionScope.isValidSurname}">
					<span class="error text-danger">
						<fmt:message key="validation.user.last_name"/>
					</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="email"><fmt:message key="credentials.email"/> <span class="text-danger">*</span></label>
					<input
							type="email"
							class="form-control"
							name="email"
							id="email"
							required
							pattern="${UserValidationPattern.EMAIL.pattern}"
							title="<fmt:message key="validation.user.email" />"
					<c:if test="${sessionScope.oldSignUpEmail != null}">
							value="${sessionScope.oldSignUpEmail}"
					</c:if>
					>
					<c:if test="${sessionScope.isValidEmail != null && !sessionScope.isValidEmail}">
					<span class="error text-danger">
						<fmt:message key="validation.user.email"/>
					</span>
					</c:if>
					<c:if test="${sessionScope.emailAlreadyExists != null && sessionScope.emailAlreadyExists}">
					<span class="error text-danger">
						<fmt:message key="validation.user.emailAlreadyExists"/>
					</span>
					</c:if>
				</div>
				<div class="form-group">
					<label for="password"><fmt:message key="credentials.password"/> <span class="text-danger">*</span></label>
					<input
							type="password"
							class="form-control"
							name="password"
							id="password"
							required
							pattern="${UserValidationPattern.PASSWORD.pattern}"
							title="<fmt:message key="validation.user.password" />"
					>
					<c:if test="${sessionScope.isValidPassword != null && !sessionScope.isValidPassword}">
					<span class="error text-danger">
						<fmt:message key="validation.user.password"/>
					</span>
					</c:if>
				</div>
				<div class="col text-center">
					<button type="submit" class="btn btn-primary">
						<fmt:message key="sign_up_jsp.button.sign_up"/>
					</button>
				</div>
			</form>
		</div>
		<div class="row justify-content-center text-center mt-3">
			<div class="col-6">
				<p>
					<fmt:message key="sign_up_jsp.have_acc_question" />
					<a href="${pageContext.request.contextPath}/controller?command=sign-in">
						<fmt:message key="sign_up_jsp.login" />
					</a>
				</p>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>