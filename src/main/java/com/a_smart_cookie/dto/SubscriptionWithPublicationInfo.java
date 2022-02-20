package com.a_smart_cookie.dto;

import com.a_smart_cookie.entity.Publication;

import java.time.LocalDate;

/**
 * Data transfer object that used as holder of subscriptions with full publication localed info.
 *
 */
public class SubscriptionWithPublicationInfo {
	private final Publication publication;
	private final LocalDate localDate;

	public SubscriptionWithPublicationInfo(Publication publication, LocalDate localDate) {
		this.publication = publication;
		this.localDate = localDate;
	}

	public Publication getPublication() {
		return publication;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}

	@Override
	public String toString() {
		return "SubscriptionWithPublicationInfo{" +
				"publication=" + publication +
				", localDate=" + localDate +
				'}';
	}
}
