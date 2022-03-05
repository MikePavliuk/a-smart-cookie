package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dto.admin.PublicationDto;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides with creating publication.
 *
 */
public class CreatePublicationCommand extends Command {

	private static final long serialVersionUID = -7596867861960586132L;

	private static final Logger LOG = Logger.getLogger(CreatePublicationCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String newPricePerMonth = request.getParameter("price_per_month");
		LOG.trace("newPricePerMonth --> " + newPricePerMonth);

		String newGenre = request.getParameter("genre");
		LOG.trace("newGenre --> " + newGenre);

		Map<Language, String> titles = new HashMap<>();
		Map<Language, String> descriptions = new HashMap<>();

		for(Language language : Language.values()) {
			titles.put(language, request.getParameter("title_"+language.getAbbr()));
			descriptions.put(language, request.getParameter("description_"+language.getAbbr()));
			LOG.trace("language --> " + language.getAbbr() + ", title --> " + titles.get(language) + ", description --> " + descriptions.get(language));
		}

		PublicationDto publicationDto = new PublicationDto(
				null,
				Genre.safeFromString(newGenre),
				new BigDecimal(newPricePerMonth),
				titles,
				descriptions
		);

		HttpSession session = request.getSession();

		try {
			PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();

			HttpPath notValidHttpPath = publicationService.performValidationMechanism(session, publicationDto);
			if (notValidHttpPath != null) return notValidHttpPath;

			LOG.trace("Publication is valid");
			publicationService.createPublicationWithInfo(publicationDto);

		} catch (ServiceException e) {
			session.setAttribute("serviceError", new Object());
			LOG.error("Exception has occurred on service layer", e);
			return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.SEND_REDIRECT);
		}

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.FORWARD);
	}

}
