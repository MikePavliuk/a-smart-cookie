package com.a_smart_cookie.entity;

import com.a_smart_cookie.util.StringHandler;
import com.a_smart_cookie.util.translator.Translatable;
import com.a_smart_cookie.util.translator.TranslatorContext;
import com.a_smart_cookie.util.translator.strategies.GenreTranslatorStrategies;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Publication entity.
 *
 */
public final class Publication extends Entity {
    private static final long serialVersionUID = 5722602323493897338L;
	private final Integer id;
    private final Genre genre;
    private final String title;
    private final String description;
    private final BigDecimal pricePerMonth;

	private Publication(PublicationBuilder publicationBuilder) {
		this.id = publicationBuilder.id;
		this.genre = publicationBuilder.genre;
		this.title = publicationBuilder.title;
		this.description = publicationBuilder.description;
		this.pricePerMonth = publicationBuilder.pricePerMonth;
	}

	public Integer getId() {
		return id;
	}

	public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

	@Override
	public String toString() {
		return "Publication{" +
				"id=" + id +
				", genre=" + genre +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", pricePerMonth=" + pricePerMonth +
				'}';
	}

	/**
	 * Implements Builder pattern and provide with ability to construct immutable Publication object.
	 *
	 */
	public static class PublicationBuilder {
		private Integer id;
		private Genre genre;
		private String title;
		private String description;
		private BigDecimal pricePerMonth;

		public Publication build() {
			return new Publication(this);
		}

		public PublicationBuilder() {
		}

		public PublicationBuilder(Integer id, Genre genre, String title, String description, BigDecimal pricePerMonth) {
			this.id = id;
			this.genre = genre;
			this.title = title;
			this.description = description;
			this.pricePerMonth = pricePerMonth;
		}

		public PublicationBuilder withId(Integer id) {
			this.id = id;
			return this;
		}

		public PublicationBuilder withGenre(Genre genre) {
			this.genre = genre;
			return this;
		}

		public PublicationBuilder withTitle(String title) {
			this.title = title;
			return this;
		}

		public PublicationBuilder withDescription(String description) {
			this.description = description;
			return this;
		}

		public PublicationBuilder withPricePerMonth(BigDecimal pricePerMonth) {
			this.pricePerMonth = pricePerMonth;
			return this;
		}

		@Override
		public String toString() {
			return "PublicationBuilder{" +
					"id=" + id +
					", genre=" + genre +
					", title='" + title + '\'' +
					", description='" + description + '\'' +
					", pricePerMonth=" + pricePerMonth +
					'}';
		}
	}

	/**
	 * Language entity with ability to be translated.
	 *
	 */
	public enum Genre implements Translatable {
		FICTION,
		NOVEL,
		COOKBOOK,
		DETECTIVE,
		HISTORICAL,
		HORROR,
		SCIENCE;

		public static Genre safeFromString(String genreName) {
			return Arrays.stream(Genre.values())
					.filter(genre -> genre.name().equalsIgnoreCase(genreName))
					.findFirst()
					.orElse(FICTION);
		}

		public static Genre fromString(String genreName) {
			return Arrays.stream(Genre.values())
					.filter(genre -> genre.name().equalsIgnoreCase(genreName))
					.findFirst()
					.orElse(null);
		}

		@Override
		public String getTranslatedValue(Language language) {
			return StringHandler.capitaliseFirstLetter(
					TranslatorContext.translateInto(GenreTranslatorStrategies.getTranslatorByLanguage(language), this)
			);
		}
	}

}
