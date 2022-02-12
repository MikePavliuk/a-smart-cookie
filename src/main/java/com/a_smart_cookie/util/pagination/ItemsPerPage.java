package com.a_smart_cookie.util.pagination;

import java.util.Arrays;

public enum ItemsPerPage {
	ONE(1),
	TWO(2),
	TEN(10),
	TWENTY(20);

	private final Integer limit;

	ItemsPerPage(Integer limit) {
		this.limit = limit;
	}

	public static ItemsPerPage fromString(String limitString) throws IllegalArgumentException {
		if (limitString == null || !limitString.matches("[0-9]+")) {
			return ONE;
		}

		return Arrays.stream(ItemsPerPage.values())
				.filter(limit -> limit.getLimit().equals(Integer.parseInt(limitString)))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}

	public Integer getLimit() {
		return limit;
	}
}
