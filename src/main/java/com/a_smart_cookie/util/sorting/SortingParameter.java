package com.a_smart_cookie.util.sorting;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.entity.Language;
import com.a_smart_cookie.util.StringHandler;
import com.a_smart_cookie.util.translator.Translatable;
import com.a_smart_cookie.util.translator.TranslatorContext;
import com.a_smart_cookie.util.translator.strategies.SortingParameterTranslatorStrategies;

import java.util.Arrays;

/**
 * Holder of possible sorting parameters on catalog page.
 *
 */
public enum SortingParameter implements Translatable {
	PRICE(EntityColumn.Publication.PRICE_PER_MONTH.getName()),
	TITLE(EntityColumn.PublicationInfo.TITLE.getName());

	private final String value;

	SortingParameter(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * Gets SortingParameter value by input string and returns TITLE by default, if something went wrong.
	 *
	 * @param parameterString String meant to be listed as value of enum.
	 * @return SortingParameter enum value by itself.
	 */
	public static SortingParameter safeFromString(String parameterString) {
		return Arrays.stream(SortingParameter.values())
				.filter(parameter -> parameter.value.equalsIgnoreCase(parameterString))
				.findFirst()
				.orElse(TITLE);
	}

	@Override
	public String getTranslatedValue(Language language) {
		return StringHandler.capitaliseFirstLetter(
				TranslatorContext.translateInto(SortingParameterTranslatorStrategies.getTranslatorByLanguage(language), this)
		);
	}
}
