package com.a_smart_cookie.dto.admin;

import com.a_smart_cookie.entity.Genre;
import com.a_smart_cookie.entity.Language;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Data transfer object that used as holder of some user information for managing by publication.
 *
 */
public class PublicationDto {
	private final Integer id;
	private final Genre genre;
	private final BigDecimal pricePerMonth;
	private final Map<Language, String> titles;
	private final Map<Language, String> descriptions;

	public PublicationDto(Integer id, Genre genre, BigDecimal pricePerMonth) {
		this.id = id;
		this.genre = genre;
		this.pricePerMonth = pricePerMonth;
		titles = new HashMap<>();
		descriptions = new HashMap<>();
	}

	public PublicationDto(Integer id, Genre genre, BigDecimal pricePerMonth, Map<Language, String> titles, Map<Language, String> descriptions) {
		this.id = id;
		this.genre = genre;
		this.pricePerMonth = pricePerMonth;
		this.titles = titles;
		this.descriptions = descriptions;
	}

	public Integer getId() {
		return id;
	}

	public Genre getGenre() {
		return genre;
	}

	public BigDecimal getPricePerMonth() {
		return pricePerMonth;
	}

	public Map<Language, String> getTitles() {
		return titles;
	}

	public Map<Language, String> getDescriptions() {
		return descriptions;
	}

	@Override
	public String toString() {
		return "PublicationDto{" +
				"id=" + id +
				", genre=" + genre +
				", pricePerMonth=" + pricePerMonth +
				", titles=" + titles +
				", descriptions=" + descriptions +
				'}';
	}
}
