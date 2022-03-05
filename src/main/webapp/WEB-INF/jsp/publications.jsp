<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<html>

<fmt:message key="publications_jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
	<div class="container">
		<h1 class="mt-3 text-center"><fmt:message key="publications_jsp.header"/></h1>
		<c:choose>
			<c:when test="${requestScope.publications.size() gt 0}">
				<h5 class="mt-3 text-center"><fmt:message key="publications_jsp.table_header"/></h5>
				<div class="d-flex justify-content-end mt-2 mb-2">
					<a type="button"
					   class="btn btn-success"
					   href="${pageContext.request.contextPath}/controller?command=publication_create_view">
						<fmt:message key="button.create"/>
					</a>
				</div>
				<table class="table">
					<thead class="thead-light">
					<tr>
						<th scope="col">#</th>
						<th scope="col"><fmt:message key="publications_jsp.table.title"/></th>
						<th scope="col"><fmt:message key="publications_jsp.table.description"/></th>
						<th scope="col"><fmt:message key="publications_jsp.table.genre"/></th>
						<th scope="col"><fmt:message key="publications_jsp.table.price_per_month"/></th>
						<th scope="col"><fmt:message key="publications_jsp.table.action_edit"/></th>
						<th scope="col"><fmt:message key="publications_jsp.table.action_delete"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="publication" items="${requestScope.publications}" varStatus="loop">
						<tr>
							<th scope="row">${(requestScope.itemsPerPage * (requestScope.currentPage-1)) + loop.count}</th>
							<td>${publication.title}</td>
							<td>${publication.description}</td>
							<td>${publication.genre.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}</td>
							<td>${publication.pricePerMonth}$</td>
							<td>
								<a class="btn btn-warning"
								   href="${pageContext.request.contextPath}/controller?command=publication_edit_view&item=${publication.id}">
									<fmt:message key="publications_jsp.table.button.edit"/>
								</a>
							</td>
							<td>
								<form action="${pageContext.request.contextPath}/controller?command=publication_delete"
									  method="post">
									<input type="hidden" name="publicationId" value="${publication.id}">
									<button class="btn btn-danger" type="submit">
										<fmt:message key="publications_jsp.table.button.delete"/>
									</button>
								</form>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>

				<%@ include file="/WEB-INF/jspf/pagination.jspf" %>

			</c:when>
			<c:otherwise>
				<h4 class="text-center">
					<fmt:message key="info.no_publications"/></h4>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<c:choose>
	<c:when test="${not empty sessionScope.illegalParams}">
		${sar:remove(pageContext.session, "illegalParams")}
		<div class="alert alert-danger alert-dismissable text-center">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<fmt:message key="alert.illegal_params"/>
		</div>
	</c:when>

	<c:when test="${not empty sessionScope.serviceError}">
		${sar:remove(pageContext.session, "serviceError")}
		<div class="alert alert-danger alert-dismissable text-center">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<fmt:message key="alert.service_exception"/>
		</div>
	</c:when>
</c:choose>




<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>