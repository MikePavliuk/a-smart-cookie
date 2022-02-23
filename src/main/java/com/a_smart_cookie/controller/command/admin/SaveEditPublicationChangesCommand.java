package com.a_smart_cookie.controller.command.admin;

import com.a_smart_cookie.controller.command.Command;
import com.a_smart_cookie.controller.route.HttpHandlerType;
import com.a_smart_cookie.controller.route.HttpPath;
import com.a_smart_cookie.controller.route.WebPath;
import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dto.admin.PublicationDto;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.ServiceFactory;
import com.a_smart_cookie.util.validation.publication.PublicationValidator;
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
public class SaveEditPublicationChangesCommand extends Command {

	private static final long serialVersionUID = -8654492442706757097L;

	private static final Logger LOG = Logger.getLogger(SaveEditPublicationChangesCommand.class);

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

		HttpSession session = request.getSession();

		HttpPath notValidHttpPath = performValidationMechanism(session, publicationDto);
		if (notValidHttpPath != null) return notValidHttpPath;

		LOG.trace("Publication is valid");

		PublicationService publicationService = ServiceFactory.getInstance().getPublicationService();
		publicationService.editPublicationWithInfo(publicationDto);

		LOG.debug("Command finished");
		return new HttpPath(WebPath.Command.ADMIN_PUBLICATIONS_MANAGEMENT, HttpHandlerType.FORWARD);
	}

	private HttpPath performValidationMechanism(HttpSession session, PublicationDto publicationDto) {
		Map<String, Boolean> validationResult = PublicationValidator.getValidationResults(publicationDto);

		if (validationResult.containsValue(false)) {
			session.setAttribute("isValidPricePerMonth", validationResult.get(EntityColumn.Publication.PRICE_PER_MONTH.getName()));

			for (Language language: Language.values()) {

				session.setAttribute("isValidTitle_" + language.getAbbr(),
						validationResult.get(EntityColumn.PublicationInfo.TITLE.getName() + "_" + language.getAbbr()));

				session.setAttribute("isValidDescription_" + language.getAbbr(),
						validationResult.get(EntityColumn.PublicationInfo.DESCRIPTION.getName() + "_" + language.getAbbr()));
			}

			addOldFieldValuesToSession(session, publicationDto);

			LOG.debug("Command finished with not valid user");
			return new HttpPath(WebPath.Command.ADMIN_PUBLICATION_EDIT.getValue() + "&item=" + publicationDto.getId(), HttpHandlerType.SEND_REDIRECT);
		}

		return null;
	}

	private void addOldFieldValuesToSession(HttpSession session, PublicationDto publicationDto) {
		session.setAttribute("oldPricePerMonth", publicationDto.getPricePerMonth());
		session.setAttribute("oldGenre", publicationDto.getGenre());

		for (Language language: Language.values()) {
			session.setAttribute("oldTitle_" + language.getAbbr(), publicationDto.getTitles().get(language));
			session.setAttribute("oldDescription_" + language.getAbbr(), publicationDto.getDescriptions().get(language));
		}
	}

}
