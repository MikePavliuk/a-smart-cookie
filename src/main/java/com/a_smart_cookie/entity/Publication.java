package com.a_smart_cookie.entity;

import com.a_smart_cookie.util.StringHandler;
import com.a_smart_cookie.util.translator.strategies.GenreTranslatorStrategies;
import com.a_smart_cookie.util.translator.Translatable;
import com.a_smart_cookie.util.translator.TranslatorContext;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Publication entity.
 *
 */
public final class Publication extends Entity {
    private static final long serialVersionUID = 5722602323493897338L;
	private Integer id;
    private final Genre genre;
    private final String title;
    private final String description;
    private final BigDecimal pricePerMonth;

	public Publication(Integer id, Genre genre, String title, String description, BigDecimal pricePerMonth) {
		this(genre, title, description, pricePerMonth);
		this.id = id;
	}

    public Publication(Genre genre, String title, String description, BigDecimal pricePerMonth) {
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.pricePerMonth = pricePerMonth;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
