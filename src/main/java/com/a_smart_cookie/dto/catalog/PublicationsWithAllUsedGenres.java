package com.a_smart_cookie.dto.catalog;

import com.a_smart_cookie.entity.Publication;

import java.util.List;

/**
 * Data transfer object that used as holder of requested list of publications and all used genres in app.
 *
 */
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
