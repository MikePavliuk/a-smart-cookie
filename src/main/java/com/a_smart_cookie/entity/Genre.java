package com.a_smart_cookie.entity;

public class Genre extends Entity {
    private static final long serialVersionUID = 1736008828428984307L;
	private final Integer id;
    private final String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
