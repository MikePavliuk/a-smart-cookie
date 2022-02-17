package com.a_smart_cookie.entity;

/**
 * Status entity.
 */
public enum Status {
	ACTIVE(1),
	BLOCKED(2);

	private final int id;

	Status(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
