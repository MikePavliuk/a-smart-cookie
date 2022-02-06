package com.a_smart_cookie.controller.command;

import com.a_smart_cookie.dao.DaoFactory;
import com.a_smart_cookie.dao.EntityTransaction;
import com.a_smart_cookie.dao.mysql.MysqlGenreDao;
import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.DaoException;
import com.a_smart_cookie.exception.ServiceException;
import com.a_smart_cookie.service.GenreService;
import com.a_smart_cookie.service.impl.GenreServiceImpl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public final class CatalogCommand extends Command {

    @Override
    public void process() throws ServletException, IOException {
        try {
            GenreService genreService = new GenreServiceImpl(DaoFactory.getInstance().getGenreDao(), new EntityTransaction());
            List<Genre> allGenresByLanguage = genreService.findAllGenresByLanguage(Language.UKRAINIAN);
            System.out.println(allGenresByLanguage);
            request.setAttribute("message", allGenresByLanguage.toString());
            forward("catalog");
        } catch (ServiceException | DaoException e) {
            e.printStackTrace();
        }
    }

}
