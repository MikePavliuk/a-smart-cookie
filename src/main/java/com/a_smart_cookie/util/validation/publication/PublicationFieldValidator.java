package com.a_smart_cookie.util.validation.publication;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.util.validation.Validator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Holder of validators that returns validator by its defined name.
 *
 */
public final class PublicationFieldValidator {

	private static final Map<String, Validator<String, Boolean>> validatorMap;

	static {
		validatorMap = new HashMap<>();

		validatorMap.put(EntityColumn.PublicationInfo.TITLE.getName(), (title) -> {
			if (title != null) {
				return title.matches(PublicationValidationPattern.TITLE.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.PublicationInfo.DESCRIPTION.getName(), (description) -> {
			if (description != null) {
				return description.matches(PublicationValidationPattern.DESCRIPTION.getPattern());
			}
			return false;
		});

		validatorMap.put(EntityColumn.Publication.PRICE_PER_MONTH.getName(), (priceStr) -> {
			try {
				BigDecimal price = new BigDecimal(priceStr);

				return price.compareTo(new BigDecimal(PublicationValidationPattern.MIN_PRICE_PER_MONTH.getPattern())) >= 0;
			} catch (NumberFormatException e) {
				return false;
			}
		});
	}

	/**
	 * Gets validator by field name.
	 *
	 * @param fieldName Name of field for validating.
	 * @return Requested validator.
	 */
	public static Validator<String, Boolean> getValidatorByFieldName(String fieldName) {
		return validatorMap.get(fieldName);
	}

}