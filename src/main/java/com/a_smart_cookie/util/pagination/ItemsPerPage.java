package com.a_smart_cookie.util.pagination;

import java.util.Arrays;

public enum ItemsPerPage {
	ONE(1),
	TWO(2),
	FIVE(5),
	TEN(10),
	TWENTY(20);

	private final Integer limit;

	ItemsPerPage(Integer limit) {
		this.limit = limit;
	}

	public static ItemsPerPage safeFromString(String limitString) {
		if (limitString == null || !limitString.matches("[0-9]+")) {
			return TWO;
		}

		return Arrays.stream(ItemsPerPage.values())
				.filter(limit -> limit.getLimit().equals(Integer.parseInt(limitString)))
				.findFirst()
				.orElse(TWO);
	}

	public Integer getLimit() {
		return limit;
	}
}
