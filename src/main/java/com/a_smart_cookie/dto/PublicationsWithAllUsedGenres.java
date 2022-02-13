package com.a_smart_cookie.dto;

import com.a_smart_cookie.entity.Publication;

import java.util.List;

public class PublicationsWithAllUsedGenres {
	private List<Publication.Genre> genres;
	private List<Publication> publications;

	public PublicationsWithAllUsedGenres() {
	}

	public PublicationsWithAllUsedGenres(List<Publication.Genre> genres, List<Publication> publications) {
		this.genres = genres;
		this.publications = publications;
	}

	public List<Publication.Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Publication.Genre> genres) {
		this.genres = genres;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}
}
