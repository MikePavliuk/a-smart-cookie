package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dto.admin.UserForManagement;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.UserService;
import com.a_smart_cookie.util.PaginationHandler;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Provides with information about users in the system.
 *
 */
public class UsersManagementCommand extends Command {

	private static final long serialVersionUID = 8660826338889063524L;

	private static final Logger LOG = Logger.getLogger(UsersManagementCommand.class);

	private static final int ITEMS_PER_PAGE = 15;

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		List<UserForManagement> usersForManagement;
		int numberOfPages;
		int currentPage;

		try {

			UserService userService = ServiceFactory.getInstance().getUserService();
			int totalNumberOfSubscribers = userService.getTotalNumberOfSubscribers();
			numberOfPages = PaginationHandler.getRequestedNumberOfPages(ITEMS_PER_PAGE, totalNumberOfSubscribers);
			currentPage = PaginationHandler.getRequestedPageNumber(request, numberOfPages);
			usersForManagement = userService.getPaginatedUsersWithStatistics(currentPage, ITEMS_PER_PAGE);

		} catch (ServiceException e) {
			HttpSession session = request.getSession();
			session.setAttribute("serviceError", new Object());
			LOG.error("Exception has occurred on service layer", e);
			return new HttpPath(WebPath.Command.ADMIN_USERS, HttpHandlerType.SEND_REDIRECT);
		}

		LOG.trace("usersForManagement --> " + usersForManagement);

		request.setAttribute("usersForManagement", usersForManagement);

		PaginationHandler.setPaginationAttributes(request, numberOfPages, currentPage, ITEMS_PER_PAGE);

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Page.ADMIN_USERS, HttpHandlerType.FORWARD);
	}

}
