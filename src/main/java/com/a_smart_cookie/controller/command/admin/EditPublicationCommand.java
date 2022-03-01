package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dto.admin.PublicationDto;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
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
 * Provides with saving edit publication changes.
 *
 */
public class EditPublicationCommand extends Command {

	private static final long serialVersionUID = -8654492442706757097L;

	private static final Logger LOG = Logger.getLogger(EditPublicationCommand.class);

	@Override
	public HttpPath execute(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("Command starts");

		String newPricePerMonth = request.getParameter("price_per_month");
		LOG.trace("newPricePerMonth --> " + newPricePerMonth);

		String publicationId = request.getParameter("publicationId");
		LOG.trace("publicationId --> " + publicationId);

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
				Integer.parseInt(publicationId),
				Genre.safeFromString(newGenre),
				new BigDecimal(newPricePerMonth),
				titles,
				descriptions
		);

		PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();
		HttpSession session = request.getSession();

		HttpPath notValidHttpPath = publicationService.performValidationMechanism(session, publicationDto);
		if (notValidHttpPath != null) return notValidHttpPath;

		LOG.trace("Publication is valid");

		publicationService.editPublicationWithInfo(publicationDto);

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.FORWARD);
	}

}
