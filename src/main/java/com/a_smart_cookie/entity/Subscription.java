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
    private final Integer periodInMonths;

    public Subscription(Integer publicationId, Date startDate, Integer periodInMonths) {
        this.publicationId = publicationId;
        this.startDate = startDate.toLocalDate();
        this.periodInMonths = periodInMonths;
    }

    public Integer getPublicationId() {
        return publicationId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Integer getPeriodInMonths() {
        return periodInMonths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (!publicationId.equals(that.publicationId)) return false;
        return startDate.equals(that.startDate);
    }

    @Override
    public int hashCode() {
        int result = publicationId.hashCode();
        result = 31 * result + startDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "publicationId=" + publicationId +
                ", startDate=" + startDate +
                ", periodInMonths=" + periodInMonths +
                '}';
    }
}
