package com.a_smart_cookie.entity;

import java.math.BigDecimal;

public final class Publication {
    private Integer id;
    private final Genre genre;
    private final String title;
    private final String description;
    private final BigDecimal pricePerMonth;

    public Publication(int id, Genre genre, String title, String description, BigDecimal pricePerMonth) {
        this.id = id;
        this.genre = genre;
        this.title = title;
        this.description = description;
        this.pricePerMonth = pricePerMonth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
