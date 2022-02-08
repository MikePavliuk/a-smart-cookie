package com.a_smart_cookie.entity;

public class Genre extends Entity<Integer> {
    private static final long serialVersionUID = 1736008828428984307L;
    private final String name;

    public Genre(Integer id, String name) {
        super.setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                '}';
    }
}
