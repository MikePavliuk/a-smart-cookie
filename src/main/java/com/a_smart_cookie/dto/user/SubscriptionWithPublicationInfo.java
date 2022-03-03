package com.a_smart_cookie.dto.user;

import com.a_smart_cookie.entity.Publication;

import java.time.LocalDate;

/**
 * Data transfer object that used as holder of subscriptions with full publication localed info.
 *
 */
public class SubscriptionWithPublicationInfo {
	private final Publication publication;
	private final LocalDate startDate;
	private final int periodInMonths;

	public SubscriptionWithPublicationInfo(Publication publication, LocalDate startDate, int periodInMonths) {
		this.publication = publication;
		this.startDate = startDate;
		this.periodInMonths = periodInMonths;
	}

	public Publication getPublication() {
		return publication;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public int getPeriodInMonths() {
		return periodInMonths;
	}

	@Override
	public String toString() {
		return "SubscriptionWithPublicationInfo{" +
				"publication=" + publication +
				", startDate=" + startDate +
				", periodInMonths=" + periodInMonths +
				'}';
	}

}
