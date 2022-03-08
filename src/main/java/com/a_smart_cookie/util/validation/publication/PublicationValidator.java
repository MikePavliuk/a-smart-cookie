package com.a_smart_cookie.util.validation.publication;

import com.a_smart_cookie.dao.EntityColumn;
import com.a_smart_cookie.dto.admin.PublicationDto;
import com.a_smart_cookie.entity.Language;

import java.util.HashMap;
import java.util.Map;

/**
 * Util method that returns validation results publication entity.
 */
public final class PublicationValidator {

	/**
	 * Gets validation result by Publication.
	 *
	 * @param publicationDto Publication dto
	 * @return Map with string key - name of field and boolean value - is valid answer.
	 */
	public static Map<String, Boolean> getValidationResults(PublicationDto publicationDto) {
		Map<String, Boolean> validationResult = new HashMap<>();

		validationResult.put(EntityColumn.Publication.PRICE_PER_MONTH.getName(),
				PublicationFieldValidator.getValidatorByFieldName(EntityColumn.Publication.PRICE_PER_MONTH.getName())
						.isValid(publicationDto.getPricePerMonth().toString())
		);

		for (Language language : Language.values()) {
			validationResult.put(EntityColumn.PublicationInfo.TITLE.getName() + "_" + language.getAbbr(),
					PublicationFieldValidator
							.getValidatorByFieldName(EntityColumn.PublicationInfo.TITLE.getName())
							.isValid(publicationDto.getTitles().get(language))
			);

			validationResult.put(EntityColumn.PublicationInfo.DESCRIPTION.getName() + "_" + language.getAbbr(),
					PublicationFieldValidator
							.getValidatorByFieldName(EntityColumn.PublicationInfo.DESCRIPTION.getName())
							.isValid(publicationDto.getDescriptions().get(language))
			);

		}

		return validationResult;
	}

	private PublicationValidator() {
	}

}
