package com.a_smart_cookie.entity;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Subscription entity.
 *
 */
public class Subscription extends Entity {
    private static final long serialVersionUID = 8110419913226734658L;
    private final Integer publicationId;
    private final LocalDate startDate;

    public Subscription(Integer publicationId, Date startDate) {
        this.publicationId = publicationId;
        this.startDate = startDate.toLocalDate();
    }

    public Integer getPublicationId() {
        return publicationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "publicationId=" + publicationId +
                ", startDate=" + startDate +
                '}';
    }
}
