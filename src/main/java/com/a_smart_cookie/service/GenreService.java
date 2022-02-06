package com.a_smart_cookie.service;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.exception.ServiceException;

import java.util.List;

public interface GenreService {

    List<Genre> findAllGenresByLanguage(Language language) throws ServiceException;

}
