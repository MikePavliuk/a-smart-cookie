package com.a_smart_cookie.dto.catalog;

import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.entity.Publication;
import com.a_smart_cookie.util.sorting.SortingDirection;
import com.a_smart_cookie.util.sorting.SortingParameter;

public class FilterParameters {
	private final Integer itemsPerPage;
	private final Integer paginationOffset;
	private final Language language;
	private final SortingDirection sortingDirection;
	private final SortingParameter sortingParameter;
	private final Publication.Genre specificGenre;
	private final String searchedTitle;

	public FilterParameters(Integer itemsPerPage,
							Integer paginationOffset,
							Language language,
							SortingDirection sortingDirection,
							SortingParameter sortingParameter,
							Publication.Genre specificGenre,
							String searchedTitle) {
		this.itemsPerPage = itemsPerPage;
		this.paginationOffset = paginationOffset;
		this.language = language;
		this.sortingDirection = sortingDirection;
		this.sortingParameter = sortingParameter;
		this.specificGenre = specificGenre;
		this.searchedTitle = searchedTitle;
	}

	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public Integer getPaginationOffset() {
		return paginationOffset;
	}

	public Language getLanguage() {
		return language;
	}

	public SortingDirection getSortingDirection() {
		return sortingDirection;
	}

	public SortingParameter getSortingParameter() {
		return sortingParameter;
	}

	public Publication.Genre getSpecificGenre() {
		return specificGenre;
	}

	public String getSearchedTitle() {
		return searchedTitle;
	}

	@Override
	public String toString() {
		return "FilterParameters{" +
				"itemsPerPage=" + itemsPerPage +
				", numberOfRequestedPage=" + paginationOffset +
				", language=" + language +
				", sortingDirection=" + sortingDirection +
				", sortingParameter=" + sortingParameter +
				", specificGenre=" + specificGenre +
				", searchedTitle='" + searchedTitle + '\'' +
				'}';
	}
}
