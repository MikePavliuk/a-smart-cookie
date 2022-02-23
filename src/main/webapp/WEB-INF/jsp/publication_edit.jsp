<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/locale.jspf" %>
<%@ page import="com.a_smart_cookie.entity.Genre" %>
<%@ page import="com.a_smart_cookie.util.validation.publication.PublicationValidationPattern" %>


<html>

<fmt:message key="publication_edit_jsp.title" var="title"/>
<c:set var="title" value="${title}" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content admin">
	<div class="container publications">
		<h1 class="mt-3 text-center mb-3"><fmt:message key="publication_edit_jsp.header"/></h1>

		<form action="${pageContext.request.contextPath}/controller?command=save_edit_publication_changes"
			  method="post">
			<input type="hidden" name="publicationId" value="${requestScope.id}">
			<div class="form-group row">
				<label for="genre" class="col-sm-2 label-center">
					<fmt:message key="publication.genre"/>
				</label>
				<select class="form-control col-sm-3" id="genre" name="genre" required>
					<c:forEach var="genre" items="${Genre.values()}">
						<option value="${genre.name()}"
								<c:choose>
									<c:when test="${sessionScope.oldGenre != null}">
										selected
									</c:when>
									<c:otherwise>
										<c:if test="${requestScope.genre eq genre}">selected</c:if>
									</c:otherwise>
								</c:choose>
						>
								${genre.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}
						</option>
					</c:forEach>
				</select>
			</div>

			<div class="form-group row">
				<label for="price_per_month" class="col-sm-2 label-center">
					<fmt:message key="publication.price_per_month"/>
					<span class="text-danger">*</span></label>

				<div class="input-group-append col-sm-2 p-0">
					<input type="number"
						   class="form-control"
						   id="price_per_month"
						   min="${PublicationValidationPattern.MIN_PRICE_PER_MONTH.pattern}"
						   name="price_per_month"
						   step="any"
						   title="<fmt:message key="validation.publication.price_per_month" />"
						   required
					<c:choose>
					<c:when test="${sessionScope.oldPricePerMonth eq null}">
						   value="${requestScope.pricePerMonth}"
					</c:when>
					<c:otherwise>
						   value="${sessionScope.oldPricePerMonth}"
					</c:otherwise>
					</c:choose>
					>
					<span class="input-group-text">$</span>
				</div>
				<c:if test="${sessionScope.isValidPricePerMonth != null && !sessionScope.isValidPricePerMonth}">
					<span class="error text-danger">
						<fmt:message key="validation.publication.price_per_month"/>
					</span>
				</c:if>

			</div>

			<div class="accordion" id="accordionTranslations">
				<c:forEach var="publication" items="${requestScope.publicationMap}">

					<div class="card">
						<div class="card-header" id="${publication.key.name()}">
							<h2 class="mb-0">
								<button class="btn btn-link btn-block text-left" type="button" data-toggle="collapse"
										data-target="#${publication.key.abbr}" aria-expanded="true"
										aria-controls="${publication.key.abbr}">
									<fmt:message
											key="language"/>: ${publication.key.getTranslatedValue(Language.safeFromString(cookie['lang'].value))}
								</button>
							</h2>
						</div>

						<div id="${publication.key.abbr}" class="collapse show"
							 aria-labelledby="${publication.key.name()}" data-parent="#accordionTranslations">
							<div class="card-body">

								<div class="form-group">
									<label for="title_${publication.key.abbr}">
										<fmt:message key="publication.title"/>
										<span class="text-danger">*</span></label>
									<input type="text"
										   class="form-control"
										   name="title_${publication.key.abbr}"
										   id="title_${publication.key.abbr}"
										   required
										   pattern="${PublicationValidationPattern.TITLE.pattern}"
										   title="<fmt:message key="validation.publication.title" />"

									<c:choose>
										<c:when test="${publication.key == Language.UKRAINIAN && sessionScope.oldTitle_uk !=null && !sessionScope.oldTitle_uk}">
											   value="${sessionScope.oldTitle_uk}"
										</c:when>
										<c:when test="${publication.key == Language.ENGLISH && sessionScope.oldTitle_en !=null && !sessionScope.oldTitle_en}">
											   value="${sessionScope.oldTitle_en}"
										</c:when>
										<c:otherwise>
											   value="${publication.value.title}"
										</c:otherwise>
									</c:choose>
									>
									<c:choose>
										<c:when test="${publication.key == Language.UKRAINIAN}">
											<c:if test="${sessionScope.isValidTitle_uk != null && sessionScope.isValidTitle_uk !=null && !sessionScope.isValidTitle_uk}">
												<span class="error text-danger">
													<fmt:message key="validation.publication.title"/>
												</span>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${sessionScope.isValidTitle_en != null && sessionScope.isValidTitle_en !=null && !sessionScope.isValidTitle_en}">
												<span class="error text-danger">
													<fmt:message key="validation.publication.title"/>
												</span>
											</c:if>
										</c:otherwise>
									</c:choose>
								</div>

								<div class="form-group">
									<label for="description_${publication.key.abbr}">
										<fmt:message key="publication.description"/>
										<span class="text-danger">*</span></label>
									<input type="text"
										   class="form-control"
										   name="description_${publication.key.abbr}"
										   id="description_${publication.key.abbr}"
										   required
										   pattern="${PublicationValidationPattern.DESCRIPTION.pattern}"
										   title="<fmt:message key="validation.publication.description" />"

									<c:choose>
									<c:when test="${publication.key == Language.UKRAINIAN && sessionScope.oldDescription_uk !=null && !sessionScope.oldDescription_uk}">
										   value="${sessionScope.oldDescription_uk}"
									</c:when>
									<c:when test="${publication.key == Language.ENGLISH && sessionScope.oldDescription_en !=null && !sessionScope.oldDescription_en}">
										   value="${sessionScope.oldDescription_en}"
									</c:when>
									<c:otherwise>
										   value="${publication.value.description}"
									</c:otherwise>
									</c:choose>
									>
									<c:choose>
										<c:when test="${publication.key == Language.UKRAINIAN}">
											<c:if test="${sessionScope.isValidDescription_uk != null && sessionScope.isValidDescription_uk !=null && !sessionScope.isValidDescription_uk}">
												<span class="error text-danger">
													<fmt:message key="validation.publication.description"/>
												</span>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${sessionScope.isValidDescription_en != null && sessionScope.isValidDescription_en !=null && !sessionScope.isValidDescription_en}">
												<span class="error text-danger">
													<fmt:message key="validation.publication.description"/>
												</span>
											</c:if>
										</c:otherwise>
									</c:choose>
								</div>

							</div>
						</div>
					</div>

				</c:forEach>
			</div>

			<div class="col text-center mt-3">
				<button type="submit" class="btn btn-primary">
					<fmt:message key="button.save"/>
				</button>
			</div>

		</form>

	</div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>