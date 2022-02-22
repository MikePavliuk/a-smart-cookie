package com.a_smart_cookie.util.validation.publication;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.entity.Publication;

import java.util.HashMap;
import java.util.Map;

/**
 * Util method that returns validation results publication entity.
 *
 */
public final class PublicationValidator {

	/**
	 * Gets validation result by Publication.
	 *
	 * @param publication Publication entity
	 * @return Map with string key - name of field and boolean value - is valid answer.
	 */
	public static Map<String, Boolean> getValidationResults(Publication publication) {
		Map<String, Boolean> validationResult = new HashMap<>();

		validationResult.put(EntityColumn.PublicationInfo.TITLE.getName(),
				PublicationFieldValidator.getValidatorByFieldName(EntityColumn.PublicationInfo.TITLE.getName())
						.isValid(publication.getTitle())
				);

		validationResult.put(EntityColumn.PublicationInfo.DESCRIPTION.getName(),
				PublicationFieldValidator.getValidatorByFieldName(EntityColumn.PublicationInfo.DESCRIPTION.getName())
				.isValid(publication.getDescription())
		);

		validationResult.put(EntityColumn.Publication.PRICE_PER_MONTH.getName(),
				PublicationFieldValidator.getValidatorByFieldName(EntityColumn.Publication.PRICE_PER_MONTH.getName())
				.isValid(publication.getPricePerMonth().toString())
		);

		return validationResult;
	}

	private PublicationValidator() {
	}

}
