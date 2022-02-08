package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.controller.WebPath;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.GenreService;
import com.a_smart_cookie.service.impl.GenreServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CatalogCommand extends Command {

	private static final long serialVersionUID = -266627794194940139L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			GenreService genreService = new GenreServiceImpl();
			List<Genre> allGenresByLanguage = genreService.findAllGenresByLanguage(Language.fromString(request.getParameter("lang")));
			System.out.println(allGenresByLanguage);
			request.setAttribute("message", allGenresByLanguage.toString());
			return WebPath.Page.CATALOG.getValue();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return WebPath.Page.ERROR.getValue();
	}

}
