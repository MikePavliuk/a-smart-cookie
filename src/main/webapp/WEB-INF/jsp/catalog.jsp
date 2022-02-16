<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.a_smart_cookie.util.sorting.SortingParameter" %>
<%@ page import="com.a_smart_cookie.util.sorting.SortingDirection" %>
<%@ page import="com.a_smart_cookie.util.pagination.ItemsPerPage" %>
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
					<fmt:message key="button.search" />
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
							<fmt:message key="param.sorting.parameter" />
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

					<c:forEach var="publication" items="${requestScope.publications}">
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
										<fmt:message key="publication.price_per_month"/>: <b><c:out
											value="${publication.pricePerMonth}"/> $</b>
									</p>
									<a href="#" class="card-link">
										<fmt:message key="publication.subscribe"/>
									</a>
								</div>
							</div>
						</div>
					</c:forEach>

				</div>

				<div class="row justify-content-center">
					<nav aria-label="Pagination for publications">
						<ul class="pagination">
							<c:if test="${requestScope.currentPage != 1}">
								<li class="page-item">
									<a class="page-link"
									   href="<p:replaceParam name='page' value='${requestScope.currentPage-1}' />">
										<fmt:message key="pagination.previous"/>
									</a>
								</li>
							</c:if>

							<c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
								<c:choose>
									<c:when test="${requestScope.currentPage eq i}">
										<li class="page-item active">
											<a class="page-link">
													${i} <span class="sr-only">(current)</span>
											</a>
										</li>
									</c:when>
									<c:otherwise>
										<li class="page-item">
											<a class="page-link"
											   href="<p:replaceParam name='page' value='${i}' />">
													${i}
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
								<li class="page-item">
									<a class="page-link"
									   href="<p:replaceParam name='page' value='${requestScope.currentPage+1}' />">
										<fmt:message key="pagination.next"/>
									</a>
								</li>
							</c:if>
						</ul>
					</nav>

				</div>
			</c:when>
			<c:otherwise>
				<div class="row justify-content-center mt-5">
					<h4><fmt:message key="info.empty_result"/></h4>
				</div>
			</c:otherwise>
		</c:choose>

	</div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/catalog.js"></script>

</body>
