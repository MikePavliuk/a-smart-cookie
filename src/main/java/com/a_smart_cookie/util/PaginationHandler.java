package com.a_smart_cookie.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Util class fo handling common operations that used while paginating on commands.
 *
 */
public final class PaginationHandler {

	private static final Logger LOG = Logger.getLogger(PaginationHandler.class);

	/**
	 * Sets request attributes for pagination.
	 *
	 * @param request Request to set parameters for.
	 * @param numberOfPages Total number of pages.
	 * @param currentPage Current active page number.
	 * @param itemsPerPage Items per page.
	 */
	public static void setPaginationAttributes(HttpServletRequest request, int numberOfPages, int currentPage, int itemsPerPage) {
		request.setAttribute("numberOfPages", numberOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("itemsPerPage", itemsPerPage);
	}

	/**
	 * Gets requested number of pages.
	 *
	 * @param itemsPerPage Items per page.
	 * @param totalNumberOfItems Total number of items.
	 * @return Requested count of number of pages.
	 */
	public static int getRequestedNumberOfPages(int itemsPerPage, int totalNumberOfItems) {
		int numberOfPages = totalNumberOfItems / itemsPerPage;
		if (totalNumberOfItems % itemsPerPage > 0) {
			++numberOfPages;
		}
		LOG.trace("Total number of requested pages -->" + numberOfPages);
		return numberOfPages;
	}

	/**
	 * Gets the current (requested) page number.
	 *
	 * @param request  Request to get parameter from.
	 * @param numberOfPages Total number of pages.
	 * @return Requested page number.
	 */
	public static int getRequestedPageNumber(HttpServletRequest request, int numberOfPages) {
		int currentPage = 1;
		String pageNumberParameter = request.getParameter("page");
		if (pageNumberParameter != null && (Integer.parseInt(pageNumberParameter) >= currentPage) && (Integer.parseInt(pageNumberParameter) <= numberOfPages)) {
			currentPage = Integer.parseInt(pageNumberParameter);
		}
		LOG.trace("Requested page number -->" + currentPage);
		return currentPage;
	}

	private PaginationHandler() {

	}
}
