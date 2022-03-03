<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<html>

<fmt:message key="user_page_jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
	<div class="container">
		<h1 class="mt-3 text-center"><fmt:message key="user_page_jsp.header"/></h1>
		<c:choose>
			<c:when test="${requestScope.statistics.activeSubscriptions.size() gt 0}">
				<p><b><fmt:message key="user_page_jsp.active_subscriptions_number"/>:</b> ${requestScope.statistics.activeSubscriptions.size()}</p>
				<p><b><fmt:message key="user_page_jsp.inactive_subscriptions_number"/>:</b> ${requestScope.statistics.numberOfInactiveSubscriptions}</p>
				<p>
					<b><fmt:message key="user_page_jsp.payment_total_amount"/>:</b>
						${requestScope.statistics.totalSpentMoney}$
				</p>
				<h5 class="mt-3 text-center"><fmt:message key="user_page_jsp.subscriptions_table"/></h5>
				<table class="table">
					<thead class="thead-light">
					<tr>
						<th scope="col">#</th>
						<th scope="col"><fmt:message key="user_page_jsp.table.publication_title"/></th>
						<th scope="col"><fmt:message key="user_page_jsp.table.genre"/></th>
						<th scope="col"><fmt:message key="user_page_jsp.table.price_per_month"/></th>
						<th scope="col"><fmt:message key="user_page_jsp.table.start_date"/></th>
						<th scope="col"><fmt:message key="user_page_jsp.table.end_date"/></th>
					</tr>
					</thead>
					<tbody>
					<c:forEach var="subscription" items="${requestScope.statistics.activeSubscriptions}" varStatus="loop">
						<tr>
							<th scope="row">${loop.count}</th>
							<td>${subscription.publication.title}</td>
							<td>${subscription.publication.genre.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}</td>
							<td>${subscription.publication.pricePerMonth}$</td>
							<td>${subscription.startDate}</td>
							<td>${subscription.startDate.plusMonths(subscription.periodInMonths)}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<h4 class="text-center"><fmt:message key="info.no_subscriptions"/></h4>
				<h5 class="text-center">
					<a href="${pageContext.request.contextPath}/controller?command=catalog">
						<fmt:message key="catalog_jsp.title"/>
					</a>
				</h5>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>