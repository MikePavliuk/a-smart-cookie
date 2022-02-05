package com.a_smart_cookie.entity;

import java.sql.Date;
import java.time.LocalDate;

public final class Subscription {
    private final Publication publication;
    private final LocalDate startDate;

    public Subscription(Publication publication, Date startDate) {
        this.publication = publication;
        this.startDate = startDate.toLocalDate();
    }

    public Publication getPublication() {
        return publication;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "publication=" + publication +
                ", startDate=" + startDate +
                '}';
    }
}
