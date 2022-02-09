package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.WebPath;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.PublicationService;
import com.a_smart_cookie.service.impl.PublicationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CatalogCommand extends Command {

	private static final long serialVersionUID = -266627794194940139L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			PublicationService publicationService = new PublicationServiceImpl();
			List<Publication> publications = publicationService.findAllPublicationsByLanguage(Language.fromString(request.getParameter("lang")));
			System.out.println(publications);
			request.setAttribute("message", publications.toString());
			return WebPath.Page.CATALOG.getValue();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return WebPath.Page.ERROR.getValue();
	}

}
