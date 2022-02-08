package com.a_smart_cookie.entity;

import java.math.BigDecimal;

public final class Publication extends Entity<Integer> {
    private static final long serialVersionUID = 5722602323493897338L;
    private final Genre genre;
    private final String title;
    private final String description;
    private final BigDecimal pricePerMonth;

    public Publication(Integer id, Genre genre, String title, String description, BigDecimal pricePerMonth) {
        super.setId(id);
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.pricePerMonth = pricePerMonth;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

}
