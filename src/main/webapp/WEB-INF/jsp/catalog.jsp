<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.a_smart_cookie.dto.catalog.SortingParameter" %>
<%@ page import="com.a_smart_cookie.dto.catalog.SortingDirection" %>
<%@ page import="com.a_smart_cookie.dto.ItemsPerPage" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>

<html>

<fmt:message key="catalog_jsp.title" var="catalogTitle"/>
<c:set var="title" value="${catalogTitle}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
	<div class="container">

		<div class="row ">
			<form
					class="form-inline justify-content-center col-12"
					action="${pageContext.request.contextPath}/controller"
			>
				<input type="hidden" name="command" value="catalog">

				<c:if test="${requestScope.specificGenre != null}">
					<input type="hidden" name="specificGenre"
						   value="${requestScope.specificGenre.name().toLowerCase()}">
				</c:if>
				<c:if test="${requestScope.itemsPerPage != null}">
					<input type="hidden" name="limit" value="${requestScope.itemsPerPage}">
				</c:if>
				<c:if test="${requestScope.sort != null}">
					<input type="hidden" name="sort" value="${requestScope.sort}">
				</c:if>
				<c:if test="${requestScope.direction != null}">
					<input type="hidden" name="direction" value="${requestScope.direction}">
				</c:if>

				<input id="search-text" class="form-control col-8" type="search" name="search"
					   placeholder="<fmt:message key="catalog_jsp.search.placeholder"/>"
					   aria-label="Search" value="${requestScope.search}">
				<button id="search-button" class="btn btn-outline-success col-2 ml-3" type="submit">
					<fmt:message key="button.search"/>
				</button>
			</form>
		</div>

		<c:if test="${requestScope.search != null || requestScope.specificGenre != null}">
			<div class="d-flex justify-content-center">
				<a class="btn btn-success col-2 ml-3"
				   href="${pageContext.request.contextPath}/controller?command=catalog">
					<fmt:message key="button.reset_all"/>
				</a>
			</div>
		</c:if>

		<c:choose>
			<c:when test="${requestScope.publications.size() gt 0}">
				<div class="row justify-content-around publication-filter-params">
					<div class="dropdown publication-filter-param">
						<button
								class="btn btn-primary dropdown-toggle"
								type="button"
								data-toggle="dropdown"
								aria-haspopup="true"
								aria-expanded="true">
							<fmt:message key="param.genre"/>
						</button>
						<div class="dropdown-menu">
							<c:forEach var="genre" items="${requestScope.genres}">
								<a class="btn btn-primary dropdown-item
									<c:if test="${requestScope.specificGenre eq genre}">
									  active
									</c:if>"
								   href="<p:replaceParam name='specificGenre' value='${genre.name().toLowerCase()}' />"
								   role="button">
									<c:out value="${genre.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}"/>
								</a>
							</c:forEach>
						</div>
					</div>
					<div class="dropdown publication-filter-param">
						<button
								class="btn btn-primary dropdown-toggle"
								type="button"
								data-toggle="dropdown"
								aria-haspopup="true"
								aria-expanded="true">
							<fmt:message key="param.sorting.direction"/>
						</button>
						<div class="dropdown-menu">
							<c:forEach var="sortingDirection" items="${SortingDirection.values()}">
								<a class="btn btn-primary dropdown-item
							<c:if test="${requestScope.direction.toUpperCase() eq sortingDirection.name()}">
							  active
						   	</c:if>"

								   href="<p:replaceParam name='direction' value='${sortingDirection.name()}' />"
								   role="button">
									<c:out value="${sortingDirection.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}"/>
								</a>
							</c:forEach>
						</div>
					</div>
					<div class="dropdown publication-filter-param">
						<button
								class="btn btn-primary dropdown-toggle"
								type="button"
								data-toggle="dropdown"
								aria-haspopup="true"
								aria-expanded="true">
							<fmt:message key="param.sorting.parameter"/>
						</button>
						<div class="dropdown-menu">
							<c:forEach var="sortingParam" items="${SortingParameter.values()}">
								<a class="btn btn-primary dropdown-item
							<c:if test="${requestScope.sort eq sortingParam.getValue()}">
							  active
						   	</c:if>"

								   href="<p:replaceParam name='sort' value='${sortingParam.getValue()}' />"
								   role="button">
									<c:out value="${sortingParam.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}"/>
								</a>
							</c:forEach>
						</div>
					</div>
					<div class="dropdown publication-filter-param">
						<button
								class="btn btn-primary dropdown-toggle"
								type="button"
								data-toggle="dropdown"
								aria-haspopup="true"
								aria-expanded="true">
							<fmt:message key="param.pagination.items_per_page"/>
						</button>
						<div class="dropdown-menu">
							<c:forEach var="perPageOption" items="${ItemsPerPage.values()}">
								<a class="btn btn-primary dropdown-item
									<c:if test="${requestScope.itemsPerPage eq perPageOption.getLimit()}">
									  active
									</c:if>"

								   href="<p:replaceParam name='limit' value='${perPageOption.getLimit()}' />"
								   role="button">
									<c:out value="${perPageOption.getLimit()}"/>
								</a>
							</c:forEach>
						</div>
					</div>
				</div>

				<div class="row justify-content-around">

					<c:forEach var="publication" items="${requestScope.publications}" varStatus="loop">
						<div class="col-6" style="display: flex">
							<div class="card publication-item">
								<div class="card-body">
									<h5 class="card-title">
										<c:out value="${publication.title}"/>
									</h5>
									<h6 class="card-subtitle mb-2 text-muted">
										<c:out value="${publication.genre.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}"/>
									</h6>
									<p class="card-text">
										<c:out value="${publication.description}"/>
									</p>
									<p class="card-text">
										<fmt:message key="publication.price_per_month"/>:
										<b><c:out value="${publication.pricePerMonth}"/> $</b>
									</p>
									<c:choose>
										<c:when test="${empty sessionScope.user}">
											<a class="btn btn-danger"
											   href="${pageContext.request.contextPath}/controller?command=sign-in"
											   role="button">
												<fmt:message key="publication.subscribe"/>
											</a>
										</c:when>

										<c:when test="${sessionScope.user != null && cfn:contains(sessionScope.user.subscriptions, publication.id)}">
											<a class="btn btn-secondary" role="button">
												<fmt:message key="publication.subscription_present"/>
											</a>
										</c:when>

<%--										<c:when test="${not empty sessionScope.user and sessionScope.user.userDetail.balance < publication.pricePerMonth}">--%>
<%--											<a class="btn btn-warning"--%>
<%--											   data-toggle="modal"--%>
<%--											   data-target="#addFundsModal"--%>
<%--											   role="button">--%>
<%--												<fmt:message key="publication.subscribe"/>--%>
<%--											</a>--%>
<%--										</c:when>--%>

										<c:otherwise>
											<form action="${pageContext.request.contextPath}/controller?command=subscribe" method="post" id="subscriptionForm">
												<input type="hidden" name="item" value="${publication.id}">
												<div class="form-group row">
													<label for="period-${loop.index}" class="col-sm-4 label-center">
														<fmt:message key="publication.period"/>
													</label>
													<div class="col-sm-2">
														<input type="number"
															   class="form-control"
															   id="period-${loop.index}"
															   min="1"
															   max="12"
															   value="1"
															   name="period"
															   step="1"
															   required>
													</div>
												</div>
												<div>
													<button class="btn btn-success btn-submit"
															type="submit"
															onclick="subscribe(
																${publication.pricePerMonth.doubleValue()},
																${sessionScope.user.userDetail.balance.doubleValue()},
																${loop.index})">
														<fmt:message key="publication.subscribe"/>
													</button>
												</div>
											</form>
										</c:otherwise>
									</c:choose>

								</div>
							</div>
						</div>
					</c:forEach>

				</div>

				<%@ include file="/WEB-INF/jspf/pagination.jspf" %>
			</c:when>
			<c:otherwise>
				<div class="row justify-content-center mt-5">
					<h4><fmt:message key="info.empty_result"/></h4>
				</div>
			</c:otherwise>
		</c:choose>

	</div>
</div>

<c:choose>
	<c:when test="${not empty sessionScope.serviceError}">
		${sar:remove(pageContext.session, "serviceError")}
		<div class="alert alert-danger alert-dismissable text-center">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<fmt:message key="alert.service_exception"/>
		</div>
	</c:when>

	<c:when test="${not empty sessionScope.notUpdatedResult}">
		${sar:remove(pageContext.session, "notUpdatedResult")}
		<div class="alert alert-warn alert-dismissable text-center">
			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
			<fmt:message key="alert.not_updated_results"/>
		</div>
	</c:when>

</c:choose>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script type="text/javascript">
    function subscribe(price, balance, index) {
        let userBalance = parseFloat(balance);
		let totalPrice = parseInt($("#period-" + index).val()) * parseFloat(price);

        if (totalPrice > userBalance) {
            event.preventDefault();
            $("#addFundsModal").modal('show');
        }
    }
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/catalog.js"></script>

</body>
