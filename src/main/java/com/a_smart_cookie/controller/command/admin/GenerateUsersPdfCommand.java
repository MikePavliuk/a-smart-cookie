package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.PdfException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.service.UserService;
import com.a_smart_cookie.util.CookieHandler;
import com.a_smart_cookie.util.PdfHandler;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Provides with generating and auto-downloading users statistics in pdf-format.
 *
 */
public class GenerateUsersPdfCommand extends Command {

	private static final long serialVersionUID = 5083550064066420448L;

	private static final Logger LOG = Logger.getLogger(GenerateUsersPdfCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		Language language = Language.safeFromString(CookieHandler.readCookieValue(request, "lang").orElse(Language.UKRAINIAN.getAbbr()));
		UserService userService = ServiceFactory.getInstance().getUserService();

		response.setContentType("application/pdf;charset=UTF-8");
		response.addHeader("content-disposition", "attachment; filename=Users statistics.pdf");

		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ByteArrayOutputStream resultPdf = PdfHandler.generateUsersPdf(userService.getAllUsersWithStatistics() , language);
			LOG.debug("Command finished");
			resultPdf.writeTo(out);
		} catch (PdfException | IOException e) {
			LOG.error("Can't generate and download pdf");
			throw new ServiceException("Can't generate users pdf statistics", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					LOG.error("Can't flush and close the resource");
				}
			}
		}

		LOG.debug("Command finished without generated pdf");
		return new HttpPath(WebPath.Command.ADMIN_USERS, HttpHandlerType.SEND_REDIRECT);
	}

}
