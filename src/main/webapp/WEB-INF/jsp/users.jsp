<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>
<%@ page import="com.a_smart_cookie.entity.Status" %>

<html>

<fmt:message key="users_jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
	<div class="container">
		<h1 class="mt-3 text-center"><fmt:message key="users_jsp.header"/></h1>
		<c:choose>
			<c:when test="${requestScope.usersForManagement.size() gt 0}">
				<h5 class="mt-3 text-center"><fmt:message key="users_jsp.table_header"/></h5>
				<table class="table">
					<thead class="thead-light">
					<tr>
						<th scope="col">#</th>
						<th scope="col"><fmt:message key="users_jsp.table.first_name"/></th>
						<th scope="col"><fmt:message key="users_jsp.table.last_name"/></th>
						<th scope="col"><fmt:message key="users_jsp.table.email"/></th>
						<th scope="col"><fmt:message key="users_jsp.table.number_of_subscriptions"/></th>
						<th scope="col"><fmt:message key="users_jsp.table.status"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="user" items="${requestScope.usersForManagement}" varStatus="loop">
						<tr>
							<th scope="row">${loop.count}</th>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>${user.email}</td>
							<td>${user.numberOfSubscriptions}</td>
							<td>
								<form action="${pageContext.request.contextPath}/controller?command=change_user_status"
									  method="post">
									<input type="hidden" name="status" value="${user.status.name()}">
									<input type="hidden" name="userId" value="${user.id}">
									<c:choose>

										<c:when test="${user.status == Status.ACTIVE}">
											<button class="btn btn-danger" type="submit">
												<fmt:message key="users_jsp.table.button.block"/>
											</button>
										</c:when>

										<c:otherwise>
											<button class="btn btn-success" type="submit">
												<fmt:message key="users_jsp.table.button.unblock"/>
											</button>
										</c:otherwise>

									</c:choose>

								</form>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<h4 class="text-center">
					<fmt:message key="info.no_subscribers"/></h4>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>