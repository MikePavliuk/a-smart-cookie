package com.a_smart_cookie.dto.catalog;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;

public class CountRowsParameters {
	private final Language language;
	private final Publication.Genre genre;
	private final String searchedTitle;

	public CountRowsParameters(Language language, Publication.Genre genre, String searchedTitle) {
		this.language = language;
		this.genre = genre;
		this.searchedTitle = searchedTitle;
	}

	public Language getLanguage() {
		return language;
	}

	public Publication.Genre getGenre() {
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
