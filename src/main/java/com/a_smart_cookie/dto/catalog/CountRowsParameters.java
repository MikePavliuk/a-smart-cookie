package com.a_smart_cookie.dto.catalog;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;

/**
 * Data transfer object that used as holder of filter params to get count of requested rows by that params.
 *
 */
public class CountRowsParameters {
	private final Language language;
	private final Genre genre;
	private final String searchedTitle;

	public CountRowsParameters(Language language, Genre genre, String searchedTitle) {
		this.language = language;
		this.genre = genre;
		this.searchedTitle = searchedTitle;
	}

	public Language getLanguage() {
		return language;
	}

	public Genre getGenre() {
		return genre;
	}

	public String getSearchedTitle() {
		return searchedTitle;
	}

	@Override
	public String toString() {
		return "CountRowsParameters{" +
				"language=" + language +
				", genre=" + genre +
				", searchedTitle='" + searchedTitle + '\'' +
				'}';
	}
}
