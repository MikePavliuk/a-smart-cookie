<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Catalog" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>

<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="container">

        <div class="row justify-content-around">

            <c:forEach var="publication" items="${requestScope.publications}">
                <div class="col-6">
                    <div class="card publication-item">
                        <div class="card-body">
                            <h5 class="card-title">
                                <c:out value="${publication.title}"/>
                            </h5>
                            <h6 class="card-subtitle mb-2 text-muted">
                                <c:out value="${publication.genre.getTranslatedValue(requestScope.language)}"/>
                            </h6>
                            <p class="card-text">
                                <c:out value="${publication.description}"/>
                            </p>
                            <p class="card-text">
                                Price per month: <b><c:out value="${publication.pricePerMonth}"/> $</b>
                            </p>
                            <a href="#" class="card-link">
                                Subscribe
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
                               href="${pageContext.request.contextPath}/controller?command=catalog&limit=${requestScope.itemsPerPage}&page=${requestScope.currentPage-1}">
                                Previous
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
                                       href="${pageContext.request.contextPath}/controller?command=catalog&limit=${requestScope.itemsPerPage}&page=${i}">
                                            ${i}
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/controller?command=catalog&limit=${requestScope.itemsPerPage}&page=${requestScope.currentPage+1}">
                                Next
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>

        </div>

    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>

</body>
